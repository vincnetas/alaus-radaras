#include "waitdialog.h"
#include "ui_waitdialog.h"
#include <QMovie>
#include "viewutils.h"
WaitDialog::WaitDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::WaitDialog)
{
    ui->setupUi(this);
    ui->progressBar->setMinimum(1);
    ui->progressBar->setMaximum(10);

     timer = new QTimer(this);
     connect(timer, SIGNAL(timeout()), this, SLOT(updateProgress()));
     timer->start(500);

     setAutoFillBackground(true);
     setPalette(ViewUtils::GetBackground(palette()));
}

void WaitDialog::updateProgress()
{
    if(ui->progressBar->value() < 10)
        ui->progressBar->setValue(ui->progressBar->value() + 1);
    else
        ui->progressBar->setValue(1);
}

void WaitDialog::changeEvent(QEvent* event)
{
    if (event->type() == QEvent::LanguageChange)
    {
        ui->retranslateUi(this);
    }
    QWidget::changeEvent(event);
}

WaitDialog::~WaitDialog()
{
    timer->stop();
    delete timer;
    delete ui;
}
