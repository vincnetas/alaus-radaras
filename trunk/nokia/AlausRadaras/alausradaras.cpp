#include "alausradaras.h"
#include "ui_alausradaras.h"
#include <QThread>
#include <QDebug>
#include <QMenuBar>
#include <QDialog>
#include "brandtabs.h"
#include "publist.h"
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
}
void AlausRadaras::dbInitFinished()
{
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


AlausRadaras::~AlausRadaras()
{
    delete ui;
    delete brandTabs;
    delete pubList;
    delete dbManager;
    delete feelingLucky;
}
