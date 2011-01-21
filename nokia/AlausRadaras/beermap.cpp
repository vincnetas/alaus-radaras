#include "beermap.h"
#include "ui_beermap.h"
#include "lightmaps.h"
#include "viewutils.h"

BeerMap::BeerMap(QWidget *parent) :
    QWidget(parent),
    maps(0),
    ui(new Ui::BeerMap)
{
    ui->setupUi(this);
    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));
}

void BeerMap::on_btnBack_clicked()
{
    emit Back();
}

void BeerMap::showPubs(QList<BeerPub*> &pubs)
{
    if(!maps) {
        maps = new LightMaps(this);
        connect(maps, SIGNAL(pubSelected(QString)),this, SLOT(showPub(QString)));
        this->ui->layout->addWidget(maps);
    }

    maps->setPubs(pubs);
    maps->setCenter(54.686647, 25.282788);

}


void BeerMap::showPub(QString pubId)
{
 emit PubSelected(pubId);
}


BeerMap::~BeerMap()
{
    delete ui;
}
