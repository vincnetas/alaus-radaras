#ifndef BEERPUB_H
#define BEERPUB_H

#include <QObject>
#include <QPoint>

struct BeerPub
{
   QString id;
   QString title;
   QString city;
   qreal longitude;
   qreal latitude;
   qreal distance;
   QPoint tile;
   QPoint tilePixel;
};

#endif // BEERPUB_H
