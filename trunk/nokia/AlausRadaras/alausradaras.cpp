#include "alausradaras.h"
#include "ui_alausradaras.h"
#include <QThread>
#include <QDebug>
#include <QMenuBar>
#include <QDialog>
#include "brandtabs.h"
#include "beermap.h"
AlausRadaras::AlausRadaras(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::AlausRadaras)
{
    ui->setupUi(this);
    dbManager = new DbManager();
    dbManager->init();

    brandTabs = new BrandTabs(this);

    QAction* exitAction = new QAction(this);
    exitAction->setText("Remove");
    exitAction->setSoftKeyRole(QAction::NegativeSoftKey);
    this->addAction(exitAction);
}
void AlausRadaras::dbInitFinished()
{
}

void AlausRadaras::on_btnBrands_clicked()
{
    brandTabs->setWindowFlags( brandTabs->windowFlags() ^ Qt::WindowSoftkeysVisibleHint );
    brandTabs->showFullScreen();
}

void AlausRadaras::on_btnNear_clicked()
{

        BeerMap *map = new BeerMap(this);
        map->showFullScreen();

}

AlausRadaras::~AlausRadaras()
{
    delete dialog;
    delete ui;
    delete brandTabs;
    delete dbManager;
}
