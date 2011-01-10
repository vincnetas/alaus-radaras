#ifndef BEERMAP_H
#define BEERMAP_H

#include <QMainWindow>

namespace Ui {
    class BeerMap;
}

class BeerMap : public QMainWindow
{
    Q_OBJECT

public:
    explicit BeerMap(QWidget *parent = 0);
    ~BeerMap();

private:
    Ui::BeerMap *ui;
};

#endif // BEERMAP_H
