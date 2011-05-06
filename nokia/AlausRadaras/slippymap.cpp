#include "slippymap.h"
#include <QtNetwork>
#include <QtGui>
#include "calculationhelper.h"
#include "beerpub.h"
#include <QList>

uint qHash(const QPoint& p)
{
    return p.x() * 17 ^ p.y();
}


SlippyMap::SlippyMap(QObject *parent) :
    QObject(parent)
    , width(400)
    , height(300)
    , latitude(59.9138204)
    , longitude(10.7387413) {
    m_emptyTile = QPixmap(CalculationHelper::tileSize, CalculationHelper::tileSize);
m_emptyTile.fill(Qt::lightGray);

QPainter p(&m_emptyTile);
p.drawText(CalculationHelper::tileSize/3, CalculationHelper::tileSize/2, "Kraunasi....");

QNetworkDiskCache *cache = new QNetworkDiskCache;
cache->setCacheDirectory(QDesktopServices::storageLocation
                         (QDesktopServices::CacheLocation));
m_manager.setCache(cache);
connect(&m_manager, SIGNAL(finished(QNetworkReply*)),
        this, SLOT(handleNetworkData(QNetworkReply*)));
}

void SlippyMap::invalidate() {

    //qDebug() << "Inalidagting";
    if (width <= 0 || height <= 0)
        return;

    QPointF ct = CalculationHelper::tileForCoordinate(latitude, longitude);
    qreal tx = ct.x();
    qreal ty = ct.y();

    // top-left corner of the center tile
    int xp = width / 2 - (tx - floor(tx)) * CalculationHelper::tileSize;
    int yp = height / 2 - (ty - floor(ty)) * CalculationHelper::tileSize;

    // first tile vertical and horizontal
    int xa = (xp + CalculationHelper::tileSize - 1) / CalculationHelper::tileSize;
    int ya = (yp + CalculationHelper::tileSize - 1) / CalculationHelper::tileSize;
    int xs = static_cast<int>(tx) - xa;
    int ys = static_cast<int>(ty) - ya;

    // offset for top-left tile
    m_offset = QPoint(xp - xa * CalculationHelper::tileSize, yp - ya * CalculationHelper::tileSize);

    // last tile vertical and horizontal
    int xe = static_cast<int>(tx) + (width - xp - 1) / CalculationHelper::tileSize;
    int ye = static_cast<int>(ty) + (height - yp - 1) / CalculationHelper::tileSize;

    // build a rect
    m_tilesRect = QRect(xs, ys, xe - xs + 1, ye - ys + 1);

   // qDebug("having m_tilesrect withData center:x  %d center:y  %d width %d height %d",
   //        m_tilesRect.center().x(),m_tilesRect.center().y(), m_tilesRect.width(), m_tilesRect.height());
    if (m_url.isEmpty())
        download();

    emit updated(QRect(0, 0, width, height));
}

void SlippyMap::render(QPainter *p, const QRect &rect) {
    pubCoordinates.clear();
    for (int x = 0; x <= m_tilesRect.width(); ++x)
        for (int y = 0; y <= m_tilesRect.height(); ++y) {
            QPoint tp(x + m_tilesRect.left(), y + m_tilesRect.top());
            QRect box = tileRect(tp);

            if (rect.intersects(box)) {
              //    qDebug() << "rendering box with x" << box.x() << " and y " << box.y() << " and width " << box.width() << " and height " << box.height();
               //    qDebug() << "tp x" << tp.x() << " and tp y " << tp.y();
                if (m_tilePixmaps.contains(tp))
                    p->drawPixmap(box, m_tilePixmaps.value(tp));
                else
                    p->drawPixmap(box, m_emptyTile);

                for(int i = 0 ; i < pubs.size(); i++) {
                    // qDebug() << "pub x" << pubs[i]->tile().x() << " and pub y " << pubs[i]->tile().y();
                    if(pubs[i].tile.x() == tp.x() && pubs[i].tile.y() == tp.y()) {
                       // qDebug() << "looping";
                        // qDebug() << "oslo x" << tileForOslo.x() << " oslo y " << tileForOslo.y();
                        PubContainer container;
                        container.coordinates = QRect(box.x() + pubs[i].tilePixel.x() - 10,box.y() + pubs[i].tilePixel.y() - 32,35,50);
                        container.pubId = pubs[i].id;
                        pubCoordinates.append(container);
                        p->drawImage(container.coordinates.x(),container.coordinates.y(),QImage(":/images/pin.png"));
                    }
                }
            }
        }
}
void SlippyMap::pan(const QPoint &delta) {
    QPointF dx = QPointF(delta) / qreal(CalculationHelper::tileSize);
    QPointF center = CalculationHelper::tileForCoordinate(latitude, longitude) - dx;
    latitude = CalculationHelper::latitudeFromTile(center.y());
    longitude = CalculationHelper::longitudeFromTile(center.x());
    // qDebug() << "dx x is " << QString::number(dx.x()) << "dx y is " << QString::number(dx.y());
   // qDebug() << "center x is " << QString::number(center.x()) << "center y is " << QString::number(center.y());
    invalidate();
}

void SlippyMap::setPubs(const QVector<BeerPub> &pubs)
{
    //hack. We must show this every time we refresh this window. Move to
    //common settings class someday
    QSettings settings;
    this->canAccessInternet = settings.value("InternetEnabled",true).toBool();
    this->pubs = pubs;
    invalidate();
}

void SlippyMap::handleNetworkData(QNetworkReply *reply) {
    QImage img;
    QPoint tp = reply->request().attribute(QNetworkRequest::User).toPoint();
    QUrl url = reply->url();
    if (!reply->error())
        if (!img.load(reply, 0))
            img = QImage();
    reply->deleteLater();
    m_tilePixmaps[tp] = QPixmap::fromImage(img);
    if (img.isNull())
        m_tilePixmaps[tp] = m_emptyTile;
    emit updated(tileRect(tp));

    // purge unused spaces
    QRect bound = m_tilesRect.adjusted(-2, -2, 2, 2);
    foreach(QPoint tp, m_tilePixmaps.keys())
    if (!bound.contains(tp))
        m_tilePixmaps.remove(tp);

    download();
}

void SlippyMap::download() {
    QPoint grab(0, 0);
    for (int x = 0; x <= m_tilesRect.width(); ++x)
        for (int y = 0; y <= m_tilesRect.height(); ++y) {
            QPoint tp = m_tilesRect.topLeft() + QPoint(x, y);
            if (!m_tilePixmaps.contains(tp)) {
                grab = tp;
                break;
            }
        }
    if (grab == QPoint(0, 0)) {
        m_url = QUrl();
        return;
    }
    if(canAccessInternet) {
        QString path = "http://tile.openstreetmap.org/%1/%2/%3.png";
        m_url = QUrl(path.arg(CalculationHelper::zoom).arg(grab.x()).arg(grab.y()));
        QNetworkRequest request;
        request.setUrl(m_url);
        request.setRawHeader("User-Agent", "Nokia Beer Radar (Qt)");
        request.setAttribute(QNetworkRequest::User, QVariant(grab));
        m_manager.get(request);
    }
}
QRect SlippyMap::tileRect(const QPoint &tp) {
    QPoint t = tp - m_tilesRect.topLeft();
    int x = t.x() * CalculationHelper::tileSize + m_offset.x();
    int y = t.y() * CalculationHelper::tileSize + m_offset.y();
    return QRect(x, y, CalculationHelper::tileSize, CalculationHelper::tileSize);
}
