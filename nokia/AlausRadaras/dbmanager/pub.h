#ifndef PUB_H
#define PUB_H

#include "qstring.h"
struct Pub
{
    QString id;
    QString title;
    QString city;
    qreal longtitude;
    qreal latitude;
    QString address;
    QString notes;
    QString phone;
    QString url;
    int tileX;
    int tileY;
    int tilePixelX;
    int tilePixelY;
};

#endif // PUB_H
