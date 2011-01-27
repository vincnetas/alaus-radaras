#include "settings.h"
#include "ui_settings.h"
#include <QSettings>
#include "viewutils.h"


Settings::Settings(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::Settings)
{
    ui->setupUi(this);

    ui->comboUpdateFrequency->addItem(QString::fromUtf8("Visada"),0);
    ui->comboUpdateFrequency->addItem(QString::fromUtf8("Savaitinis"),1);
    ui->comboUpdateFrequency->addItem(QString::fromUtf8("Niekada"),2);

    QSettings settings;
    ui->cbEnableInternet->setChecked(settings.value("InternetEnabled",true).toBool());
    ui->comboUpdateFrequency->setCurrentIndex(settings.value("UpdateFrequency",0).toInt());

    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));
}

void Settings::on_btnBack_clicked()
{
    QSettings settings;
    settings.setValue("UpdatesEnabled",ui->comboUpdateFrequency->currentIndex() != 2);
    settings.setValue("InternetEnabled",ui->cbEnableInternet->isChecked());
    settings.setValue("UpdateFrequency",ui->comboUpdateFrequency->currentIndex());
    emit accepted();
}

Settings::~Settings()
{
    delete ui;
}
