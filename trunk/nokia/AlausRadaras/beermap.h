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
    void showPubs(const QVector<BeerPub> &pubs);
    void showSinglePub(const BeerPub &pub);
    void locationChanged(qreal lat, qreal lon);
    void keyPressEvent(QKeyEvent* event);
    void changeEvent(QEvent* event);
    ~BeerMap();

private:
    Ui::BeerMap *ui;
    LightMaps *maps;
    bool locationAlreadyUpdated;
private slots:
    void on_btnBack_clicked();
    void showPubInfo(QString pubId);

signals:
    void Back();
    void PubSelected(QString pubId);

};

#endif // BEERMAP_H
