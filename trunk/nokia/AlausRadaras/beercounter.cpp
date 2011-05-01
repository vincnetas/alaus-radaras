#include "beercounter.h"
#include "ui_beercounter.h"
#include "viewutils.h"
#include <QKeyEvent>
#include "enums.h"

BeerCounter::BeerCounter(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::BeerCounter)
{
    dataProvider = new DataProvider(this);
    ui->setupUi(this);

    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));

    QSizePolicy counterSizePolicy(QSizePolicy::Fixed, QSizePolicy::Fixed);
    QSize counterSize = ViewUtils::GetMugSize();
    counterSizePolicy.setHorizontalStretch(4);
    counterSizePolicy.setVerticalStretch(4);
    counterSizePolicy.setHeightForWidth(true);
    ui->btnCount->setSizePolicy(counterSizePolicy);
    ui->btnCount->setMinimumSize(counterSize);
    ui->btnCount->setMaximumSize(counterSize);
    ui->btnCount->setSizeIncrement(QSize(1, 1));
    ui->btnCount->setBaseSize(counterSize);

    retranslateUi();
}

void BeerCounter::on_btnBack_clicked()
{
    emit Back();
}

void BeerCounter::on_btnCount_clicked()
{

    int currentCount = settings.value("CurrentCount", 0).toInt();
    settings.setValue("CurrentCount",++currentCount);

    int totalCount = settings.value("TotalCount", 0).toInt();
    settings.setValue("TotalCount",++totalCount);

    ui->btnCount->setText(QString::number(currentCount));

    this->showQuote(currentCount);

}

void BeerCounter::on_btnClear_clicked()
{
    settings.setValue("CurrentCount",0);
    ui->btnCount->setText("0");
    this->showQuote(0);
}

void BeerCounter::showQuote(int count)
{
    this->ui->qouteLabel->setText(dataProvider->getQuote(count));
}

BeerCounter::~BeerCounter()
{
    delete dataProvider;
    delete ui;
}

void BeerCounter::keyPressEvent(QKeyEvent* event)
{
    if(event->nativeVirtualKey() == CancelKey) {
        on_btnBack_clicked();
    } else if (event->nativeVirtualKey() == OkKey) {
        on_btnClear_clicked();
    }
    QWidget::keyPressEvent(event);
}

void BeerCounter::changeEvent(QEvent* event)
{
    if (event->type() == QEvent::LanguageChange)
    {
        ui->retranslateUi(this);
        retranslateUi();
    }
    QWidget::changeEvent(event);
}

void BeerCounter::retranslateUi()
{
    int currentCount = settings.value("CurrentCount", 0).toInt();
    ui->btnCount->setText(QString::number(currentCount));
    this->showQuote(currentCount);
}

