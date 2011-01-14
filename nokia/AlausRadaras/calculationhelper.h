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
    static uint qHash(const QPoint& p);
    static QPointF tileForCoordinate(qreal lat, qreal lng, int zoom);
    static qreal longitudeFromTile(qreal tx, int zoom);
    static qreal latitudeFromTile(qreal ty, int zoom);
};

#endif // CALCULATIONHELPER_H
