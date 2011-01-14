#include "feelinglucky.h"
#include "ui_feelinglucky.h"
#include "dataprovider.h"
#include "feelingluckyinfo.h"

FeelingLucky::FeelingLucky(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::FeelingLucky)
{
    ui->setupUi(this);
    DataProvider provider(this);
    luckyInfo = provider.feelingLucky();

    ui->btnBeer->setText(luckyInfo.brandName);
    ui->btnPub->setText(luckyInfo.pubName);

    pubList = NULL;
    pubView = NULL;
}

void FeelingLucky::on_btnBeer_clicked()
{
    pubList = new PubList(this,BRAND,luckyInfo.brandId);
    pubList->setHeader(luckyInfo.brandId);
    pubList->setHeader(luckyInfo.brandName);
    pubList->showFullScreen();
    connect(this->pubList,SIGNAL(destroyed()),this,SLOT(publist_destroyed()));

}
void FeelingLucky::publist_destroyed()
{
    delete pubList;
    pubList = NULL;
}

void FeelingLucky::on_btnBack_clicked()
{
    this->close();
}

void FeelingLucky::pubview_accepted()
{
    delete pubView;
    pubView = NULL;

}

void FeelingLucky::on_btnPub_clicked()
{
    pubView = new PubView(this,luckyInfo.pubId);
    pubView->setModal(true);
    pubView->showFullScreen();
    connect(pubView,SIGNAL(accepted()), this,SLOT(pubview_accepted()));

}

FeelingLucky::~FeelingLucky()
{
    delete pubView;
    delete pubList;
    delete ui;
}
