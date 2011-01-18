#include "alausradaras.h"
#include "ui_alausradaras.h"
#include <QThread>
#include <QDebug>
#include <QMenuBar>
#include <QDialog>
#include "brandtabs.h"
#include "publist.h"
#include "dbpopulator.h"
#include "viewutils.h"
AlausRadaras::AlausRadaras(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::AlausRadaras)
{
    ui->setupUi(this);
    dbManager = new DbManager();
    dbManager->init();

    counter = NULL;
    brandTabs = NULL;
    pubList = NULL;
    feelingLucky = NULL;

    if(!dbManager->isDbLatest()) {
        progress = new WaitDialog(this);
        progress->setModal(true);
        progress->showFullScreen();

        populator = new DbPopulator(this,dbManager);
        populator->start();
        connect(populator,SIGNAL(finished()),this,SLOT(dbInitFinished()));

    } else {
        populator = NULL;
        progress = NULL;
    }
    QSettings settings;
    ui->btnCounter->setText(settings.value("TotalCount",0).toString());

    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));
}
void AlausRadaras::dbInitFinished()
{
    progress->close();
    delete progress;
}

void AlausRadaras::on_btnBrands_clicked()
{
    if(brandTabs == NULL) {
        brandTabs = new BrandTabs(this);
    }
    brandTabs->showFullScreen();

}

void AlausRadaras::on_btnNear_clicked()
{
    if(pubList == NULL) {
        pubList = new PubList(this,ALL,"");
        pubList->setHeader("Visi barai");
    }

    pubList->showFullScreen();
}

void AlausRadaras::on_btnLucky_clicked()
{
    if(feelingLucky == NULL) {
        feelingLucky = new FeelingLucky(this);
    }
    feelingLucky->showFullScreen();

}

void AlausRadaras::on_btnCounter_clicked()
{
    if(counter == NULL) {
        counter = new BeerCounter(this);
    }
    counter->showFullScreen();
}

void AlausRadaras::on_btnExit_clicked()
{
    this->close();
}

AlausRadaras::~AlausRadaras()
{
    delete ui;
    delete brandTabs;
    delete pubList;
    delete feelingLucky;
    delete progress;
    delete counter;
    delete dbManager;
    delete populator;
}
