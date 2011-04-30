#include "beermap.h"
#include "ui_beermap.h"
#include "lightmaps.h"
#include "viewutils.h"
#include <QKeyEvent>
#include "enums.h"

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
    locationAlreadyUpdated = false;

}

void BeerMap::on_btnBack_clicked()
{
    locationAlreadyUpdated = false;
    emit Back();
}

void BeerMap::showPubs(QList<BeerPub*> &pubs)
{
    maps->setPubs(pubs);
    maps->setCenter(54.686647, 25.282788);

}

void BeerMap::locationChanged(qreal lat, qreal lon)
{

    if(!locationAlreadyUpdated) {
        maps->setCenter(lat, lon);
    }
    locationAlreadyUpdated = true;

}


void BeerMap::showPubInfo(QString pubId)
{
 emit PubSelected(pubId);
}

void BeerMap::showSinglePub(BeerPub* pub)
{
   locationAlreadyUpdated = true;
   QList<BeerPub*> list;
   list.append(pub);
   maps->setPubs(list);
   maps->setCenter(pub->latitude(),pub->longitude());

}

void BeerMap::keyPressEvent(QKeyEvent* event)
{
    if(event->nativeVirtualKey() == CancelKey) {
        on_btnBack_clicked();
    }
    QWidget::keyPressEvent(event);
}

void BeerMap::changeEvent(QEvent* event)
{
    if (event->type() == QEvent::LanguageChange)
    {
        ui->retranslateUi(this);
    }
    QWidget::changeEvent(event);
}

BeerMap::~BeerMap()
{
    delete ui;
}
