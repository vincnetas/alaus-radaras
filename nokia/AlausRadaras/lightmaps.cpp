#include "lightmaps.h"
#include "beerpub.h"
#include <QList>

LightMaps::LightMaps(QWidget *parent) :
    QWidget(parent)
    , pressed(false)
    , snapped(false)
{
m_normalMap = new SlippyMap(this);
connect(m_normalMap, SIGNAL(updated(QRect)), SLOT(updateMap(QRect)));
}
void LightMaps::setCenter(qreal lat, qreal lng) {
    m_normalMap->latitude = lat;
    m_normalMap->longitude = lng;
    m_normalMap->invalidate();
}

void LightMaps::updateMap(const QRect &r) {
    update(r);
}

void LightMaps::resizeEvent(QResizeEvent *) {
    this->m_normalMap->width = width();
    this->m_normalMap->height = height();
    this->m_normalMap->invalidate();
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

void LightMaps::setPubs(QList<BeerPub*> &pubs)
{
    this->m_normalMap->setPubs(pubs);
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

void LightMaps::mouseReleaseEvent(QMouseEvent *event) {
    //qDebug("released on x :%d, y:%d",event->pos().x(),event->pos().y());
    for(int i = 0; i < m_normalMap->pubCoordinates.size(); i++) {
//        qDebug() << "looping";
        if(m_normalMap->pubCoordinates[i].coordinates.contains(event->pos())) {
            //qDebug() << "pub id" << m_normalMap->pubCoordinates[i].pubId;
            emit pubSelected(m_normalMap->pubCoordinates[i].pubId);
            break;
        }
    }
    update();
}

