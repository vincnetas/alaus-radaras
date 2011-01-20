#include "beercounter.h"
#include "ui_beercounter.h"
#include "viewutils.h"

BeerCounter::BeerCounter(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::BeerCounter)
{
    dataProvider = new DataProvider(this);
    ui->setupUi(this);

    int currentCount = settings.value("CurrentCount", 0).toInt();
    ui->btnCount->setText(QString::number(currentCount));
    this->showQoute(currentCount);

    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));
}

void BeerCounter::on_btnBack_clicked()
{
    this->close();
}

void BeerCounter::on_btnCount_clicked()
{

    int currentCount = settings.value("CurrentCount", 0).toInt();
    settings.setValue("CurrentCount",++currentCount);

    int totalCount = settings.value("TotalCount", 0).toInt();
    settings.setValue("TotalCount",++totalCount);

    ui->btnCount->setText(QString::number(currentCount));

    this->showQoute(currentCount);

}

void BeerCounter::on_btnClear_clicked()
{
    settings.setValue("CurrentCount",0);
    ui->btnCount->setText("0");
    this->showQoute(0);
}

void BeerCounter::showQoute(int count)
{
    this->ui->qouteLabel->setText(dataProvider->getQoute(count));
}

BeerCounter::~BeerCounter()
{
    delete dataProvider;
    delete ui;
}