#include "settings.h"
#include "ui_settings.h"
#include <QSettings>
#include "viewutils.h"
#include <QKeyEvent>
#include "enums.h"

Settings::Settings(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::Settings)
{
    ui->setupUi(this);
    retranslateUi();

    //we don't need to translate these...
    ui->comboLanguage->addItem(trUtf8("Lietuviu"),0);
    ui->comboLanguage->addItem(trUtf8("English"),1);
    ui->comboLanguage->addItem(trUtf8("P??????"),2);

    QSettings settings;
    ui->cbEnableInternet->setChecked(settings.value("InternetEnabled",true).toBool());
    ui->comboUpdateFrequency->setCurrentIndex(settings.value("UpdateFrequency",0).toInt());
    ui->comboLanguage->setCurrentIndex(settings.value("Language",ViewUtils::GetLanguageIndex(ViewUtils::ActiveLanguage)).toInt());

    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));
    connect(ui->comboLanguage,SIGNAL(currentIndexChanged(int)),this,SLOT(languageSelected(int)));

}

void Settings::on_btnBack_clicked()
{
    QSettings settings;
    settings.setValue("UpdatesEnabled",ui->comboUpdateFrequency->currentIndex() != 2);
    settings.setValue("InternetEnabled",ui->cbEnableInternet->isChecked());
    settings.setValue("UpdateFrequency",ui->comboUpdateFrequency->currentIndex());
    settings.setValue("Language", ui->comboLanguage->currentIndex());
    emit accepted();
}
void Settings::languageSelected(int index)
{
    emit LanguageChanged(ViewUtils::GetLanguage(index));
}

void Settings::keyPressEvent(QKeyEvent* event)
{
    if(event->nativeVirtualKey() == CancelKey) {
        on_btnBack_clicked();
    }
    QWidget::keyPressEvent(event);
}

void Settings::changeEvent(QEvent* event)
{
    if (event->type() == QEvent::LanguageChange)
    {
        ui->retranslateUi(this);
        retranslateUi();
    }
    QWidget::changeEvent(event);
}

void Settings::retranslateUi()
{
    int index = ui->comboUpdateFrequency->currentIndex();
    ui->comboUpdateFrequency->clear();
    ui->comboUpdateFrequency->addItem(tr("Visada"),0);
    ui->comboUpdateFrequency->addItem(tr("Savaitinis"),1);
    ui->comboUpdateFrequency->addItem(tr("Niekada"),2);
    ui->comboUpdateFrequency->setCurrentIndex(index);
}

Settings::~Settings()
{
    delete ui;
}
