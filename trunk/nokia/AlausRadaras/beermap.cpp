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

    if(!maps) {
        maps = new LightMaps(this);
        connect(maps, SIGNAL(pubSelected(QString)),this, SLOT(showPubInfo(QString)));
        this->ui->layout->addWidget(maps);
    }

}

void BeerMap::on_btnBack_clicked()
{
    emit Back();
}

void BeerMap::showPubs(QList<BeerPub*> &pubs)
{
    maps->setPubs(pubs);
    maps->setCenter(54.686647, 25.282788);

}


void BeerMap::showPubInfo(QString pubId)
{
 emit PubSelected(pubId);
}

void BeerMap::showSinglePub(BeerPub* pub)
{
   QList<BeerPub*> list;
   list.append(pub);
   qDebug() << list.size();
   maps->setPubs(list);
   maps->setCenter(pub->latitude(),pub->longitude());

}


BeerMap::~BeerMap()
{
    delete ui;
}
