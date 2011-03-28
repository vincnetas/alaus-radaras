#include "alausradaras.h"
#include "ui_alausradaras.h"
#include <QThread>
#include <QDebug>
#include <QMenuBar>
#include <QDialog>
#include "enums.h"
#include "viewutils.h"
#include <QDesktopServices>
#include <QKeyEvent>
AlausRadaras::AlausRadaras(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::AlausRadaras)
{
    ui->setupUi(this);

    QSettings settings;
    ui->btnCounter->setText(settings.value("TotalCount",0).toString());
    ui->txtUpdate->setVisible(false);
    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));
    connect(ui->txtUpdate,SIGNAL(linkActivated(QString)),this,SLOT(loadUpdate(QString)));

    settingsView = new Settings(this);
    connect(settingsView,SIGNAL(accepted()),this,SLOT(settings_accepted()));

    //show me a better way
    QSizePolicy counterSizePolicy(QSizePolicy::Fixed, QSizePolicy::Fixed);
    QSize counterSize = (ViewUtils::HighRes) ? QSize(250,250) : QSize(36,36);
    counterSizePolicy.setHorizontalStretch(4);
    counterSizePolicy.setVerticalStretch(4);
    counterSizePolicy.setHeightForWidth(true);
    ui->btnCounter->setSizePolicy(counterSizePolicy);
    ui->btnCounter->setMinimumSize(counterSize);
    ui->btnCounter->setMaximumSize(counterSize);
    ui->btnCounter->setSizeIncrement(QSize(1, 1));
    ui->btnCounter->setBaseSize(counterSize);

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
   emit PubListSelected(ALL,"",tr("Visi barai"));
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
    if(!text.isNull() & !text.isEmpty()) {
        ui->txtUpdate->setText(text);
        ui->txtUpdate->setVisible(true);
    }
}

void AlausRadaras::loadUpdate(QString string)
{
    QDesktopServices::openUrl(QUrl(string));
}


AlausRadaras::~AlausRadaras()
{
    delete ui;
}

void AlausRadaras::keyPressEvent(QKeyEvent* event)
{
    if(event->nativeVirtualKey() == CancelKey) {
        on_btnExit_clicked();
    } else if (event->nativeVirtualKey() == OkKey) {
        on_btnSettings_clicked();
    }
    QWidget::keyPressEvent(event);
}

