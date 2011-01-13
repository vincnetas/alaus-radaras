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
    map = NULL;
}
void AlausRadaras::dbInitFinished()
{
}

void AlausRadaras::on_btnBrands_clicked()
{
    brandTabs = new BrandTabs(this);
    brandTabs->setWindowFlags( brandTabs->windowFlags() ^ Qt::WindowSoftkeysVisibleHint );
    brandTabs->showFullScreen();
}

void AlausRadaras::on_btnNear_clicked()
{

        map = new PubList(this);
        map->showPubList(ALL,"");
        map->setWindowFlags(map->windowFlags() ^ Qt::WindowSoftkeysVisibleHint );
        map->showFullScreen();

}

AlausRadaras::~AlausRadaras()
{
    delete ui;
    delete brandTabs;
    delete map;
    delete dbManager;
}
