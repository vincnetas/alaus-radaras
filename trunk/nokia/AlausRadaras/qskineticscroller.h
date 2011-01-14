#ifndef QSKINETICSCROLLER_H
#define QSKINETICSCROLLER_H

#include <QObject>
#include <QScopedPointer>

class QsKineticScrollerImpl;
class QAbstractScrollArea;
class QEvent;

//! Vertical kinetic scroller implementation without overshoot and bouncing.
//! A temporary solution to get kinetic-like scrolling on Symbian.
class QsKineticScroller: public QObject
{
   Q_OBJECT
public:
   QsKineticScroller(QObject* parent = 0);
   ~QsKineticScroller();
   //! enabled for one widget only, new calls remove previous association
   void enableKineticScrollFor(QAbstractScrollArea* scrollArea);

protected:
   bool eventFilter(QObject* object, QEvent* event);

private slots:
   void onKineticTimerElapsed();

private:
   QScopedPointer<QsKineticScrollerImpl> d;
};

#endif // QSKINETICSCROLLER_H
