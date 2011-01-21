#ifndef BEERMAP_H
#define BEERMAP_H

#include <QMainWindow>
#include <QList>
#include "beerpub.h"
#include "lightmaps.h"
#include "pubview.h"

namespace Ui {
    class BeerMap;
}

class BeerMap : public QWidget
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
    PubView *pub;
private slots:
    void on_btnBack_clicked();
    void pub_accepted();
    void showPub(QString pubId);
signals:
    void Back();

};

#endif // BEERMAP_H
