#include "beermap.h"
#include "ui_beermap.h"
#include "lightmaps.h"

BeerMap::BeerMap(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::BeerMap)
{
    ui->setupUi(this);
    maps = new LightMaps(this);
    maps->setCenter(54.686647, 25.282788);
    this->setCentralWidget(maps);
}

void BeerMap::on_btnBack_clicked()
{
    this->close();
}

void BeerMap::setPubs(QList<BeerPub*> &pubs)
{
    this->pubs = pubs;
    this->maps->setPubs(pubs);
}

BeerMap::~BeerMap()
{
    delete maps;
    delete ui;
}
