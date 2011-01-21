#include "beermap.h"
#include "ui_beermap.h"
#include "lightmaps.h"

BeerMap::BeerMap(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::BeerMap)
{
    ui->setupUi(this);
    maps = new LightMaps(this);
    maps->setCenter(54.686647, 25.282788);
    connect(maps, SIGNAL(pubSelected(QString)),this, SLOT(showPub(QString)));
    this->ui->layout->addWidget(maps);
    pub = NULL;
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


void BeerMap::showPub(QString pubId)
{
//    pub = new PubView(this,pubId);
//    //pub->setModal(true);
//    pub->showFullScreen();
//    connect(pub,SIGNAL(accepted()), this,SLOT(pub_accepted()));
}

void BeerMap::pub_accepted()
{
    delete pub;
    pub = NULL;
}

BeerMap::~BeerMap()
{
    delete pub;
    delete maps;
    delete ui;
}
