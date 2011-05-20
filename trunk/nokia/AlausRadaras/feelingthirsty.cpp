#include "feelingthirsty.h"
#include "ui_feelingthirsty.h"
#include "viewutils.h"
#include <QKeyEvent>
#include "enums.h"

FeelingThirsty::FeelingThirsty(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::FeelingThirsty)
{
    ui->setupUi(this);
    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));
}

FeelingThirsty::~FeelingThirsty()
{
    delete ui;
}

void FeelingThirsty::chooseNext()
{
    DataProvider provider(this);
    luckyInfo = provider.feelingLucky();

    ui->btnBeer->setText(ViewUtils::WrapText(luckyInfo.beerName, 18));
    ui->btnPub->setText(ViewUtils::WrapText(luckyInfo.pubName, 18) + "\n (" + luckyInfo.city + ")");
}

void FeelingThirsty::on_btnBeer_clicked()
{
    emit PubListSelected(BEER, luckyInfo.beerId,luckyInfo.beerName);
}

void FeelingThirsty::on_btnBack_clicked()
{
    emit Back();
}
void FeelingThirsty::on_btnPub_clicked()
{
    emit PubSelected(luckyInfo.pubId);
}

void FeelingThirsty::changeEvent(QEvent* event)
{
    if (event->type() == QEvent::LanguageChange)
    {
        ui->retranslateUi(this);
    }
    QWidget::changeEvent(event);
}

void FeelingThirsty::keyPressEvent(QKeyEvent* event)
{
    if(event->nativeVirtualKey() == CancelKey) {
        on_btnBack_clicked();
    }
    QWidget::keyPressEvent(event);
}
