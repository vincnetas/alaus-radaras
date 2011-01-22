#include "maincontroller.h"
#include "ui_maincontroller.h"
#include <QDebug>
enum Views { MainView, BrandTabsView, BeerCounterView, PubListView, PubInfoView, FeelingLuckyView, BrandListView, BeerMapView };

MainController::MainController(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainController)
{

    dbManager = new DbManager();
    dbManager->init();

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

    brandTabs = new BrandTabs(this);
    mainWidget = new AlausRadaras(this);
    counter = new BeerCounter(this);
    pubList = new PubList(this);
    pubView = new PubView(this);
    feelingLucky = new FeelingLucky(this);
    brandList = new BrandList(this);
    map = new BeerMap(this);

    ui->setupUi(this);
    ui->stackedWidget->addWidget(mainWidget);
    ui->stackedWidget->addWidget(brandTabs);
    ui->stackedWidget->addWidget(counter);
    ui->stackedWidget->addWidget(pubList);
    ui->stackedWidget->addWidget(pubView);
    ui->stackedWidget->addWidget(feelingLucky);
    ui->stackedWidget->addWidget(brandList);
    ui->stackedWidget->addWidget(map);

    connect(mainWidget,SIGNAL(BrandsSelected()),this,SLOT(showBrandTabs()));
    connect(mainWidget,SIGNAL(ExitApp()),this,SLOT(close()));
    connect(mainWidget,SIGNAL(LetsCount()),this,SLOT(showCounter()));
    connect(mainWidget,SIGNAL(PubListSelected(PubListType,QString,QString)),this,SLOT(showPubList(PubListType,QString,QString)));
    connect(mainWidget,SIGNAL(FeelingLucky()),this,SLOT(showFeelingLucky()));

    connect(pubList,SIGNAL(PubSelected(QString)),this,SLOT(showPub(QString)));
    connect(pubList,SIGNAL(MapSelected(QList<BeerPub*>)),this, SLOT(showMap(QList<BeerPub*>)));
    connect(pubList,SIGNAL(Back()),this,SLOT(goBack()));

    connect(counter,SIGNAL(Back()),this,SLOT(goBack()));

    connect(brandTabs,SIGNAL(Back()),this,SLOT(goBack()));
    connect(brandTabs,SIGNAL(PubListSelected(PubListType,QString,QString)), this, SLOT(showPubList(PubListType,QString,QString)));
    connect(brandTabs,SIGNAL(BrandListSelected(BrandListType,QString,QString)), this, SLOT(showBrandList(BrandListType,QString,QString)));

    connect(feelingLucky,SIGNAL(PubSelected(QString)),this,SLOT(showPub(QString)));
    connect(feelingLucky,SIGNAL(PubListSelected(PubListType,QString,QString)), this, SLOT(showPubList(PubListType,QString,QString)));
    connect(feelingLucky,SIGNAL(Back()),this,SLOT(goBack()));

    connect(brandList,SIGNAL(Back()),this,SLOT(goBack()));
    connect(brandList,SIGNAL(PubListSelected(PubListType,QString,QString)), this, SLOT(showPubList(PubListType,QString,QString)));

    connect(pubView,SIGNAL(Back()),this,SLOT(goBack()));
    connect(pubView,SIGNAL(PubMapSelected(QString)),this,SLOT(showPubMap(QString)));

    connect(map,SIGNAL(Back()),this,SLOT(goBack()));
    connect(map,SIGNAL(PubSelected(QString)),this,SLOT(showPub(QString)));

    showWidget(MainView);
}

void MainController::showFeelingLucky()
{
        feelingLucky->chooseNext();
    showWidget(FeelingLuckyView);

}

void MainController::showMain()
{
    showWidget(MainView);
}


void MainController::showCounter()
{
    showWidget(BeerCounterView);
}

void MainController::showBrandTabs()
{
    showWidget(BrandTabsView);
}

void MainController::showWidget(int index)
{
    qDebug() << "showWidget " << QString::number(index);
    ui->stackedWidget->setCurrentIndex(index);
    ui->stackedWidget->currentWidget()->showFullScreen();
    history.push(index);
}

void MainController::showPubList(PubListType type, QString id, QString header)
{

    pubList->showPubList(type, id, header);
    showWidget(PubListView);

}
void MainController::showBrandList(BrandListType type, QString id, QString header)
{
    brandList->showBrands(type, id, header);
    showWidget(BrandListView);
}

void MainController::showMap(QList<BeerPub*> pubs)
{
    map->showPubs(pubs);
    showWidget(BeerMapView);
}

void MainController::showPub(QString pubId)
{
    pubView->showPub(pubId);
    showWidget(PubInfoView);
}


void MainController::dbInitFinished()
{
    progress->close();
    progress->deleteLater();
}

void MainController::clearHistory()
{
    while (!history.isEmpty())
        history.pop();
}

void MainController::goBack()
{
    history.pop();
    showWidget(history.pop());
}

void MainController::showPubMap(QString pubId)
{
    DataProvider d(this);
    BeerPub *pub = d.getPub(pubId);
    qDebug() << pub->id();
    map->showSinglePub(pub);
    showWidget(BeerMapView);
}



MainController::~MainController()
{
    delete dbManager;
    delete ui;
}
