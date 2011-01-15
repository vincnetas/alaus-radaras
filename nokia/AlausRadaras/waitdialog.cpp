#include "waitdialog.h"
#include "ui_waitdialog.h"
#include <QMovie>

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
}

void WaitDialog::updateProgress()
{
    if(ui->progressBar->value() < 10)
        ui->progressBar->setValue(ui->progressBar->value() + 1);
    else
        ui->progressBar->setValue(1);
}

WaitDialog::~WaitDialog()
{
    timer->stop();
    delete timer;
    delete ui;
}
