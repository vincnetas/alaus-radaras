#include "alausradaras.h"
#include "ui_alausradaras.h"
#include <QThread>
#include <QDebug>
#include <QMenuBar>
#include <QDialog>
#include "enums.h"
#include "viewutils.h"
#include <QDesktopServices>
AlausRadaras::AlausRadaras(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::AlausRadaras)
{
    ui->setupUi(this);

    QSettings settings;
    ui->btnCounter->setText(settings.value("TotalCount",0).toString());

    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));
    connect(ui->txtUpdate,SIGNAL(linkActivated(QString)),this,SLOT(loadUpdate(QString)));

    settingsView = new Settings(this);
    connect(settingsView,SIGNAL(accepted()),this,SLOT(settings_accepted()));
}

void AlausRadaras::on_btnSettings_clicked()
{
    settingsView->setModal(true);
    settingsView->showFullScreen();
}
void AlausRadaras::settings_accepted()
{
    settingsView->close();
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

void AlausRadaras::setUpdateVersion(QString text)
{
    ui->txtUpdate->setText(text);
}

void AlausRadaras::loadUpdate(QString string)
{
    QDesktopServices::openUrl(QUrl(string));
}


AlausRadaras::~AlausRadaras()
{
    delete ui;
}
