#ifndef LIGHTMAPS_H
#define LIGHTMAPS_H

#include <QWidget>
#include "slippymap.h"
class LightMaps : public QWidget
{
    Q_OBJECT
public:
    explicit LightMaps(QWidget *parent = 0);
    void setCenter(qreal lat, qreal lng);
    void setPubs(QList<BeerPub*> &pubs);
signals:

private slots:
     void updateMap(const QRect &r);
protected:
     void resizeEvent(QResizeEvent *);
     void paintEvent(QPaintEvent *event);
     void mousePressEvent(QMouseEvent *event);
     void mouseMoveEvent(QMouseEvent *event);
     void mouseReleaseEvent(QMouseEvent *);
private:
    SlippyMap *m_normalMap;
    SlippyMap *m_largeMap;
    bool pressed;
    bool snapped;
    QPoint pressPos;
    QPoint dragPos;
    QPixmap zoomPixmap;
    QPixmap maskPixmap;
};

#endif // LIGHTMAPS_H
