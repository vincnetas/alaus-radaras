#ifndef BEERMAP_H
#define BEERMAP_H

#include <QMainWindow>

namespace Ui {
    class BeerMap;
}

enum BeerMapType { BRAND, COUNTRY, TAG, ALL };

class BeerMap : public QMainWindow
{
    Q_OBJECT

public:
    explicit BeerMap(QWidget *parent = 0);
    ~BeerMap();
    void showBeerMap(BeerMapType type, QString id);
private:
    Ui::BeerMap *ui;
    BeerMapType type;
    QString id;

private slots:
    void pageLoad_finished(bool ok);
    void attachJavascript();
public slots:
    QString getMarkerData();
    void showPub(QString pubId);
};

#endif // BEERMAP_H
