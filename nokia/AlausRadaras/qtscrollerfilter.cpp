#include <qtscrollerfilter_p.h>
#include <qtscrollevent.h>
#include <qtscroller.h>
#include <QAbstractItemView>
#include <QScrollBar>
#include <QGestureEvent>
#include <QGesture>
#include <QGraphicsView>
#include <QGraphicsItem>
#include <QCoreApplication>

#ifdef Q_WS_WIN
#  include <qlibrary.h>
#  include <windows.h>
#endif

#ifdef Q_WS_MAEMO_5
#  include <QAbstractKineticScroller>
#endif

#include <QtDebug>

bool qt_sendSpontaneousEvent(QObject *receiver, QEvent *event)
{
    return QCoreApplication::sendSpontaneousEvent(receiver, event);
}


QtScrollerFilter *QtScrollerFilter::inst = 0;

QtScrollerFilter *QtScrollerFilter::instance()
{
    if (!inst)
        inst = new QtScrollerFilter();
    return inst;
}

QtScrollerFilter::QtScrollerFilter()
{ }

void QtScrollerFilter::add(QObject *target)
{
    target->installEventFilter(this);
    connect(QtScroller::scroller(target), SIGNAL(stateChanged(QtScroller::State)),
            this, SLOT(stateChanged(QtScroller::State)));
#ifdef Q_WS_MAEMO_5
    // disable the kinetic scroller on scrollArea
    if (QAbstractScrollArea *area = qobject_cast<QAbstractScrollArea *>(target->parent()))
        target = target->parent();
    if (QAbstractKineticScroller *m5s = target->property("kineticScroller").value<QAbstractKineticScroller *>()) {
        if (m5s)
            m5s->setEnabled(false);
    }
#endif
}

void QtScrollerFilter::remove(QObject *target)
{
    disconnect(QtScroller::scroller(target), SIGNAL(stateChanged(QtScroller::State)),
               this, SLOT(stateChanged(QtScroller::State)));
    target->removeEventFilter(this);
}

void QtScrollerFilter::stateChanged(QtScroller::State state)
{
    if (QtScroller *scroller = qobject_cast<QtScroller *>(sender())) {
        if (QObject *o = scroller->target()) {
            if (o->isWidgetType()) {
                QWidget *w = static_cast<QWidget *>(o);

                 if (w->parentWidget()) {
                    if (QAbstractScrollArea *area = qobject_cast<QAbstractScrollArea *>(w->parentWidget())) {
                        if (area->viewport() == w) {
                            if (QAbstractItemView *view = qobject_cast<QAbstractItemView *>(area))
                                stateChanged_QAbstractItemView(view, state);
                        }
                    }
                }
             }
        }
    }
}

bool QtScrollerFilter::eventFilter(QObject *o, QEvent *e)
{
    bool result = false;
    
    //qWarning() << o << e->type();

    if (o->isWidgetType()) {
        QWidget *w = static_cast<QWidget *>(o);
        
         if (w->parentWidget()) {
            if (QAbstractScrollArea *area = qobject_cast<QAbstractScrollArea *>(w->parentWidget())) {
                if (area->viewport() == w) {             
                    if (QAbstractItemView *view = qobject_cast<QAbstractItemView *>(area))
                        result |= eventFilter_QAbstractItemView(view, e);

                    result |= eventFilter_QAbstractScrollArea(area, e);
                }
            }
        }
    }
    return result;
}
    
bool QtScrollerFilter::eventFilter_QAbstractScrollArea(QAbstractScrollArea *area, QEvent *event)
{
    switch (event->type()) {
    case QtScrollPrepareEvent::ScrollPrepare:
    {
        QtScrollPrepareEvent *se = static_cast<QtScrollPrepareEvent *>(event);
        if (canStartScrollingAt_QAbstractScrollArea(area, se->startPos().toPoint())) {
            QScrollBar *hBar = area->horizontalScrollBar();
            QScrollBar *vBar = area->verticalScrollBar();

            se->setViewportSize(QSizeF(area->viewport()->size()));
            se->setContentPosRange(QRectF(0, 0, hBar->maximum(), vBar->maximum()));
            se->setContentPos(QPointF(hBar->value(), vBar->value()));
            se->accept();
            return true;
        }
        return false;
    }
    case QtScrollEvent::Scroll:
    {
        QtScrollEvent *se = static_cast<QtScrollEvent *>(event);

        QScrollBar *hBar = area->horizontalScrollBar();
        QScrollBar *vBar = area->verticalScrollBar();
        hBar->setValue(se->contentPos().x());
        vBar->setValue(se->contentPos().y());

        QPoint os = overshoot.value(area);
#ifdef Q_WS_WIN
        typedef BOOL (*PtrBeginPanningFeedback)(HWND);
        typedef BOOL (*PtrUpdatePanningFeedback)(HWND, LONG, LONG, BOOL);
        typedef BOOL (*PtrEndPanningFeedback)(HWND, BOOL);

        static PtrBeginPanningFeedback ptrBeginPanningFeedback = 0;
        static PtrUpdatePanningFeedback ptrUpdatePanningFeedback = 0;
        static PtrEndPanningFeedback ptrEndPanningFeedback = 0;

        if (!ptrBeginPanningFeedback)
            ptrBeginPanningFeedback = (PtrBeginPanningFeedback) QLibrary::resolve(QLatin1String("UxTheme"), "BeginPanningFeedback");
        if (!ptrUpdatePanningFeedback)
            ptrUpdatePanningFeedback = (PtrUpdatePanningFeedback) QLibrary::resolve(QLatin1String("UxTheme"), "UpdatePanningFeedback");
        if (!ptrEndPanningFeedback)
            ptrEndPanningFeedback = (PtrEndPanningFeedback) QLibrary::resolve(QLatin1String("UxTheme"), "EndPanningFeedback");

        if (ptrBeginPanningFeedback && ptrUpdatePanningFeedback && ptrEndPanningFeedback) {
            WId wid = area->window()->winId();

            if (!se->overshootDistance().isNull() && os.isNull())
                ptrBeginPanningFeedback(wid);
            if (!se->overshootDistance().isNull())
                ptrUpdatePanningFeedback(wid, -se->overshootDistance().x(), -se->overshootDistance().y(), false);
            if (se->overshootDistance().isNull() && !os.isNull())
                ptrEndPanningFeedback(wid, true);
        } else
#endif
        {
            QPoint delta = os - se->overshootDistance().toPoint();
            if (!delta.isNull())
                area->viewport()->move(area->viewport()->pos() + delta);
        }
        overshoot[area] = se->overshootDistance().toPoint();
        return true;
    }
    default:
        return false;
    }
}

struct HackedAbstractItemView : public QAbstractItemView
{
    void hackedExecuteDelayedItemsLayout()
    {
        executeDelayedItemsLayout();
    }
};

void QtScrollerFilter::stateChanged_QAbstractItemView(QAbstractItemView *view, QtScroller::State state)
{
    switch (state) {
    case QtScroller::Pressed:
        if (view->selectionModel()) {
            oldSelection = view->selectionModel()->selection();
            oldCurrent = view->selectionModel()->currentIndex();
        } else {
            oldSelection = QItemSelection();
            oldCurrent = QModelIndex();
        }
        break;

    case QtScroller::Dragging:
        // restore the old selection if we really start scrolling
        if (view->selectionModel()) {
            view->selectionModel()->select(oldSelection, QItemSelectionModel::ClearAndSelect);
            view->selectionModel()->setCurrentIndex(oldCurrent, QItemSelectionModel::NoUpdate);
        }
        // fall through

    default:
        oldSelection = QItemSelection();
        oldCurrent = QModelIndex();
        break;
    }
}

bool QtScrollerFilter::eventFilter_QAbstractItemView(QAbstractItemView *view, QEvent *event)
{
    switch (event->type()) {
    case QtScrollPrepareEvent::ScrollPrepare:
        static_cast<HackedAbstractItemView *>(view)->hackedExecuteDelayedItemsLayout();
        break;
        
    default:
        break;
    }
  
    return false;
}

bool QtScrollerFilter::canStartScrollingAt_QAbstractScrollArea(QAbstractScrollArea *area, const QPoint &startPos) const
{
    // don't start scrolling when a drag mode has been set.
    // don't start scrolling on a movable item.
    if (QGraphicsView *view = qobject_cast<QGraphicsView *>(area)) {
        if (view->dragMode() != QGraphicsView::NoDrag)
            return false;

        QGraphicsItem *childItem = view->itemAt(startPos);

        if (childItem && (childItem->flags() & QGraphicsItem::ItemIsMovable))
            return false;
    }

    // don't start scrolling on a QAbstractSlider
    if (qobject_cast<QAbstractSlider *>(area->viewport()->childAt(startPos))) {
        return false;
    }

    return true;
}

