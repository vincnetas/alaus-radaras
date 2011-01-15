#ifndef BEERMAP_H
#define BEERMAP_H

#include <QMainWindow>
#include <QList>
#include "beerpub.h"
#include "lightmaps.h"

namespace Ui {
    class BeerMap;
}

class BeerMap : public QMainWindow
{
    Q_OBJECT

public:
    explicit BeerMap(QWidget *parent = 0);
    void setPubs(QList<BeerPub*> &pubs);
    ~BeerMap();

private:
    Ui::BeerMap *ui;
    QList<BeerPub*> pubs;
    LightMaps *maps;
private slots:
    void on_btnBack_clicked();

};

#endif // BEERMAP_H
