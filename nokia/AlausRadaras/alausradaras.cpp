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
#include <QMovie>

AlausRadaras::AlausRadaras(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::AlausRadaras)
{
    ui->setupUi(this);

    qtTranslator = new QTranslator(this);
    myappTranslator = new QTranslator(this);

    settingsMenu = new QMenu(this);
    ui->btnSettings->setMenu(settingsMenu);

   // ui->txtUpdate->setVisible(false);
    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));
    connect(ui->txtUpdate,SIGNAL(linkActivated(QString)),this,SLOT(loadUpdate(QString)));

    settingsView = new Settings(this);
    connect(settingsView,SIGNAL(accepted()),this,SLOT(settingsAccepted()));

    //show me a better way
    QSizePolicy counterSizePolicy(QSizePolicy::Fixed, QSizePolicy::Fixed);
    QSize counterSize = ViewUtils::GetMugSize();
    counterSizePolicy.setHorizontalStretch(4);
    counterSizePolicy.setVerticalStretch(4);
    counterSizePolicy.setHeightForWidth(true);
    ui->btnCounter->setSizePolicy(counterSizePolicy);
    ui->btnCounter->setMinimumSize(counterSize);
    ui->btnCounter->setMaximumSize(counterSize);
    ui->btnCounter->setSizeIncrement(QSize(1, 1));
    ui->btnCounter->setBaseSize(counterSize);

    connect(settingsView,SIGNAL(LanguageChanged(QLocale::Language)), this, SLOT(changeLanguage(QLocale::Language)));

    retranslateUi();

    progressMovie = new QMovie(":/images/spinner-24x24.gif");
    ui->waitLabel->setMovie(progressMovie);
    ui->waitLabel->setVisible(false);
}

void AlausRadaras::requestDbUpdate()
{
    emit RemoteDbUpdateRequested();
}

void AlausRadaras::showSettings()
{
   settingsView->setModal(true);
   settingsView->showFullScreen();
}
void AlausRadaras::settingsAccepted()
{
    settingsView->close();
}

void AlausRadaras::on_btnBeers_clicked()
{
   emit BeersSelected();
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

void AlausRadaras::setUpdateVersion(const QString &text)
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

void AlausRadaras::changeLanguage(QLocale::Language language)
{
    QString shortLang = ViewUtils::GetStringFromLanguage(language);
    bool loaded = qtTranslator->load("qt_" + shortLang, QLibraryInfo::location(QLibraryInfo::TranslationsPath));
    qApp->installTranslator(qtTranslator);

    loaded = myappTranslator->load("alausradaras_" +shortLang, ":/");
    qApp->installTranslator(myappTranslator);
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
        ui->btnSettings->showMenu();
    }
    QWidget::keyPressEvent(event);
}

void AlausRadaras::changeEvent(QEvent* event)
{
    if (event->type() == QEvent::LanguageChange)
    {
        ui->retranslateUi(this);
        retranslateUi();
    }
    QWidget::changeEvent(event);
}

void AlausRadaras::showProgress()
{
    qDebug() << "showing progress";
    ui->waitLabel->setVisible(true);
    if(progressMovie->state() != QMovie::Running) {
        progressMovie->start();
    }
}

void AlausRadaras::hideProgress()
{
    qDebug() << "hiding progress";
    ui->waitLabel->setVisible(false);
    if(progressMovie->state() == QMovie::Running) {
        progressMovie->stop();
    }
}

void AlausRadaras::retranslateUi()
{
    QSettings settings;
    ui->btnCounter->setText(settings.value("TotalCount",0).toString());

    settingsMenu->clear();
    settingsMenu->addAction(tr("Nustatymai"), this,SLOT(showSettings()));
    settingsMenu->addAction(tr("Atsviezinti aluti"), this,SLOT(requestDbUpdate()));
}
