#include "calculationhelper.h"


uint CalculationHelper::qHash(const QPoint& p)
{
    return p.x() * 17 ^ p.y();
}

QPointF CalculationHelper::tileForCoordinate(qreal lat, qreal lng)
{
    qreal zn = static_cast<qreal>(1 << zoom);
    qreal tx = (lng + 180.0) / 360.0;
    qreal ty = (1.0 - log(tan(lat * M_PI / 180.0) +
                          1.0 / cos(lat * M_PI / 180.0)) / M_PI) / 2.0;
    return QPointF(tx * zn, ty * zn);
}

qreal CalculationHelper::longitudeFromTile(qreal tx)
{
    qreal zn = static_cast<qreal>(1 << zoom);
    qreal lat = tx / zn * 360.0 - 180.0;
    return lat;
}

qreal CalculationHelper::latitudeFromTile(qreal ty)
{
    qreal zn = static_cast<qreal>(1 << zoom);
    qreal n = M_PI - 2 * M_PI * ty / zn;
    qreal lng = 180.0 / M_PI * atan(0.5 * (exp(n) - exp(-n)));
    return lng;
}
QPoint CalculationHelper::tilePixelForTile(QPointF tile)
{
    QPoint point;
    point.setX(int((tile.x() - int(tile.x())) * tileSize));
    point.setY(int((tile.x() - int(tile.x())) * tileSize));
    return point;
}



