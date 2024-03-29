#ifndef CALCULATIONHELPER_H
#define CALCULATIONHELPER_H

#include <math.h>
#include <QtCore>

#ifndef M_PI
#define M_PI 3.14159265358979323846
#endif

class CalculationHelper
{
public:
    static const int tileSize = 256;
    static const int zoom = 16;
    static uint qHash(const QPoint& p);
    static QPointF tileForCoordinate(qreal lat, qreal lng);
    static qreal longitudeFromTile(qreal tx);
    static qreal latitudeFromTile(qreal ty);
    static QPoint tilePixelForTile(QPointF tile);
    static QPointF tileForCoordinatePrecise(qreal lat, qreal lon);
    static qreal getDistance(qreal &lat1, qreal &lon1, qreal &lat2, qreal &lon2,char unit);
};

#endif // CALCULATIONHELPER_H
