#include "alausradaras.h"
#include "ui_alausradaras.h"
#include <QThread>
#include <QDebug>
#include <QMenuBar>
#include <QDialog>
#include "brandtabs.h"
#include "publist.h"
#include "dbpopulator.h"
AlausRadaras::AlausRadaras(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::AlausRadaras)
{
    ui->setupUi(this);
    dbManager = new DbManager();
    dbManager->init();

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
}
void AlausRadaras::dbInitFinished()
{
    progress->close();
    delete progress;
}

void AlausRadaras::on_btnBrands_clicked()
{
    brandTabs = new BrandTabs(this);
    brandTabs->showFullScreen();
    connect(brandTabs,SIGNAL(destroyed()),this,SLOT(brandTabs_destroyed()));
}

void AlausRadaras::on_btnNear_clicked()
{
    pubList = new PubList(this,ALL,"");
    pubList->setHeader("Visi barai");
    pubList->showFullScreen();
    connect(pubList,SIGNAL(destroyed()),this,SLOT(pubList_destroyed()));
}

void AlausRadaras::on_btnLucky_clicked()
{
    feelingLucky = new FeelingLucky(this);
    feelingLucky->showFullScreen();
    connect(feelingLucky,SIGNAL(destroyed()),this,SLOT(feelingLucky_destroyed()));
}

void AlausRadaras::feelingLucky_destroyed()
{
    delete feelingLucky;
    feelingLucky = NULL;
}

void AlausRadaras::brandTabs_destroyed()
{
    delete brandTabs;
    brandTabs = NULL;
}

void AlausRadaras::pubList_destroyed()
{
    delete pubList;
    pubList = NULL;
}

void AlausRadaras::on_btnExit_clicked()
{
    this->close();
}

void AlausRadaras::on_btnCounter_clicked()
{
    counter = new BeerCounter(this);
    counter->showFullScreen();
    connect(counter,SIGNAL(destroyed()),this,SLOT(beerCounter_destroyed()));
}

void AlausRadaras::beerCounter_destroyed()
{
    QSettings settings;
    ui->btnCounter->setText(settings.value("TotalCount",0).toString());

    delete counter;
    counter = NULL;
}


AlausRadaras::~AlausRadaras()
{
    delete counter;
    delete ui;
    delete populator;
    delete brandTabs;
    delete pubList;
    delete dbManager;
    delete feelingLucky;
}
