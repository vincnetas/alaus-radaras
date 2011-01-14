#include "lightmaps.h"

LightMaps::LightMaps(QWidget *parent) :
    QWidget(parent)
    , pressed(false)
    , snapped(false)
{
m_normalMap = new SlippyMap(this);
m_largeMap = new SlippyMap(this);
connect(m_normalMap, SIGNAL(updated(QRect)), SLOT(updateMap(QRect)));
connect(m_largeMap, SIGNAL(updated(QRect)), SLOT(update()));
}
void LightMaps::setCenter(qreal lat, qreal lng) {
    m_normalMap->latitude = lat;
    m_normalMap->longitude = lng;
    m_normalMap->invalidate();
    m_largeMap->invalidate();
}

void LightMaps::updateMap(const QRect &r) {
    update(r);
}

void LightMaps::resizeEvent(QResizeEvent *) {
    this->m_normalMap->width = width();
    this->m_normalMap->height = height();
    this->m_normalMap->invalidate();
    this->m_largeMap->width = m_normalMap->width * 2;
    this->m_largeMap->height = m_normalMap->height * 2;
    this-> m_largeMap->invalidate();
}

void LightMaps::paintEvent(QPaintEvent *event) {
    QPainter p;
    p.begin(this);
    m_normalMap->render(&p, event->rect());
    p.setPen(Qt::black);
#if defined(Q_OS_SYMBIAN)
    QFont font = p.font();
    font.setPixelSize(13);
    p.setFont(font);
#endif
    p.drawText(rect(),  Qt::AlignBottom | Qt::TextWordWrap,
               "Map data CCBYSA 2009 OpenStreetMap.org contributors");
    p.end();
}


void LightMaps::mousePressEvent(QMouseEvent *event) {
    if (event->buttons() != Qt::LeftButton)
        return;
    pressed = snapped = true;
    pressPos = dragPos = event->pos();
    //qDebug("clicked on x :%d, y:%d",pressPos.x(),pressPos.y());
}

void LightMaps::mouseMoveEvent(QMouseEvent *event) {
    if (!event->buttons())
        return;
        if (!pressed || !snapped) {
            QPoint delta = event->pos() - pressPos;
            pressPos = event->pos();
            m_normalMap->pan(delta);
            return;
        } else {
            const int threshold = 10;
            QPoint delta = event->pos() - pressPos;
            if (snapped) {
                snapped &= delta.x() < threshold;
                snapped &= delta.y() < threshold;
                snapped &= delta.x() > -threshold;
                snapped &= delta.y() > -threshold;
            }
        }
}

void LightMaps::mouseReleaseEvent(QMouseEvent *) {
    update();
}

