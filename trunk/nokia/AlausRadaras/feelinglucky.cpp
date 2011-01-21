#include "feelinglucky.h"
#include "ui_feelinglucky.h"
#include "dataprovider.h"
#include "feelingluckyinfo.h"
#include "viewutils.h"

FeelingLucky::FeelingLucky(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::FeelingLucky)
{
    ui->setupUi(this);
    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));
}

void FeelingLucky::chooseNext()
{
    DataProvider provider(this);
    luckyInfo = provider.feelingLucky();

    ui->btnBeer->setText(luckyInfo.brandName);
    ui->btnPub->setText(luckyInfo.pubName);
}

void FeelingLucky::on_btnBeer_clicked()
{
    emit PubListSelected(BRAND, luckyInfo.brandId,luckyInfo.brandName);
}

void FeelingLucky::on_btnBack_clicked()
{
    emit Back();
}


void FeelingLucky::on_btnPub_clicked()
{
    emit PubSelected(luckyInfo.pubId);
}

FeelingLucky::~FeelingLucky()
{
    delete ui;
}
