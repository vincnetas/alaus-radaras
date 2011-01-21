#include "alausradaras.h"
#include "ui_alausradaras.h"
#include <QThread>
#include <QDebug>
#include <QMenuBar>
#include <QDialog>
#include "enums.h"
#include "viewutils.h"
AlausRadaras::AlausRadaras(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::AlausRadaras)
{
    ui->setupUi(this);

    QSettings settings;
    ui->btnCounter->setText(settings.value("TotalCount",0).toString());

    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));
}

void AlausRadaras::on_btnBrands_clicked()
{
   emit BrandsSelected();
}

void AlausRadaras::on_btnNear_clicked()
{
   emit PubListSelected(ALL,"","Visi barai");
}

void AlausRadaras::on_btnLucky_clicked()
{
   emit FeelingLucky();
}

void AlausRadaras::on_btnCounter_clicked()
{
    emit LetsCount();
}

void AlausRadaras::on_btnExit_clicked()
{
    emit ExitApp();
}

AlausRadaras::~AlausRadaras()
{
    delete ui;
}
