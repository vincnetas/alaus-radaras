#include "maincontroller.h"
#include "ui_maincontroller.h"
#include <QDebug>
#include <QGeoPositionInfoSource>
#include "dbupdatedownloader.h"

enum Views { MainView, BeerTabsView, BeerCounterView, PubListView, PubInfoView, FeelingThirstyView, BeerListView, BeerMapView, SinglePubMapView };

MainController::MainController(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainController)
{

    dbManager = new DbManager();
    dbManager->init();

    beerTabs = new BeerTabs(this);
    mainWidget = new AlausRadaras(this);
    counter = new BeerCounter(this);
    pubList = new PubList(this);
    pubView = new PubView(this);
    feelingThirsty = new FeelingThirsty(this);
    beerList = new BeerList(this);
    map = new BeerMap(this);
    singleMap = new BeerMap(this);

    ui->setupUi(this);
    ui->stackedWidget->addWidget(mainWidget);
    ui->stackedWidget->addWidget(beerTabs);
    ui->stackedWidget->addWidget(counter);
    ui->stackedWidget->addWidget(pubList);
    ui->stackedWidget->addWidget(pubView);
    ui->stackedWidget->addWidget(feelingThirsty);
    ui->stackedWidget->addWidget(beerList);
    ui->stackedWidget->addWidget(map);
    ui->stackedWidget->addWidget(singleMap);

    connect(mainWidget,SIGNAL(BeersSelected()),this,SLOT(showBeerTabs()));
    connect(mainWidget,SIGNAL(ExitApp()),this,SLOT(close()));
    connect(mainWidget,SIGNAL(LetsCount()),this,SLOT(showCounter()));
    connect(mainWidget,SIGNAL(PubListSelected(PubListType,QString,QString)),this,SLOT(showPubList(PubListType,QString,QString)));
    connect(mainWidget,SIGNAL(FeelingLucky()),this,SLOT(showFeelingThirsty()));

    connect(pubList,SIGNAL(PubSelected(QString)),this,SLOT(showPub(QString)));
    connect(pubList,SIGNAL(MapSelected(QList<BeerPub*>)),this, SLOT(showMap(QList<BeerPub*>)));
    connect(pubList,SIGNAL(Back()),this,SLOT(goBack()));

    connect(counter,SIGNAL(Back()),this,SLOT(goBack()));

    connect(beerTabs,SIGNAL(Back()),this,SLOT(goBack()));
    connect(beerTabs,SIGNAL(PubListSelected(PubListType,QString,QString)), this, SLOT(showPubList(PubListType,QString,QString)));
    connect(beerTabs,SIGNAL(BeerListSelected(BeerListType,QString,QString)), this, SLOT(showBeerList(BeerListType,QString,QString)));

    connect(feelingThirsty,SIGNAL(PubSelected(QString)),this,SLOT(showPub(QString)));
    connect(feelingThirsty,SIGNAL(PubListSelected(PubListType,QString,QString)), this, SLOT(showPubList(PubListType,QString,QString)));
    connect(feelingThirsty,SIGNAL(Back()),this,SLOT(goBack()));

    connect(beerList,SIGNAL(Back()),this,SLOT(goBack()));
    connect(beerList,SIGNAL(PubListSelected(PubListType,QString,QString)), this, SLOT(showPubList(PubListType,QString,QString)));

    connect(pubView,SIGNAL(Back()),this,SLOT(goBack()));
    connect(pubView,SIGNAL(PubMapSelected(QString)),this,SLOT(showPubMap(QString)));

    connect(map,SIGNAL(Back()),this,SLOT(goBack()));
    connect(map,SIGNAL(PubSelected(QString)),this,SLOT(showPub(QString)));

    connect(singleMap,SIGNAL(Back()),this,SLOT(goBack()));
    showWidget(MainView);

    connect(&updater,SIGNAL(updateCheckFinished(QString)),SLOT(onUpdateAvailable(QString)));
   // QTimer::singleShot(700,&updater,SLOT(checkForUpdates()));

    populator = NULL;
    progress = NULL;

    QTimer::singleShot(500,this,SLOT(initDbUpdate()));

    downloader.checkForUpdates();
}

void MainController::initDbUpdate()
{
    if(!dbManager->isDbLatest()) {
        progress = new WaitDialog(this);
        progress->setModal(true);
        progress->showFullScreen();

        populator = new DbPopulatorRunner(this,dbManager);
        populator->start();
        connect(populator,SIGNAL(finished()),this,SLOT(dbInitFinished()));
        //safeguard. Let it crash. after one minute, if its not done.
        QTimer::singleShot(60000,this,SLOT(dbInitFinished()));
    }

}

void MainController::showFeelingThirsty()
{
    feelingThirsty->chooseNext();
    showWidget(FeelingThirstyView);

}

void MainController::showMain()
{
    showWidget(MainView);
}


void MainController::showCounter()
{
    showWidget(BeerCounterView);
}

void MainController::showBeerTabs()
{
    showWidget(BeerTabsView);
}

void MainController::showWidget(int index)
{
    ui->stackedWidget->currentWidget()->hide();
    ui->stackedWidget->setCurrentIndex(index);
    ui->stackedWidget->currentWidget()->showFullScreen();
    history.push(index);
}

void MainController::showPubList(PubListType type, QString id, QString header)
{

    pubList->showPubList(type, id, header);
    showWidget(PubListView);
    QTimer::singleShot(500,this,SLOT(startLocationUpdates()));

}
void MainController::showBeerList(BeerListType type, QString id, QString header)
{
    beerList->showBeers(type, id, header);
    showWidget(BeerListView);
}

void MainController::showMap(QList<BeerPub*> pubs)
{
    map->showPubs(pubs);
    showWidget(BeerMapView);
    QTimer::singleShot(500,this,SLOT(startLocationUpdates()));
}

void MainController::showPub(QString pubId)
{
    pubView->showPub(pubId);
    showWidget(PubInfoView);
}


void MainController::dbInitFinished()
{
    if(progress) {
        progress->done(1);
        ui->stackedWidget->currentWidget()->showFullScreen();
    }
}

void MainController::clearHistory()
{
    while (!history.isEmpty())
        history.pop();
}

void MainController::goBack()
{
    if(ui->stackedWidget->currentIndex() == PubListView ||
       ui->stackedWidget->currentIndex() == BeerMapView ) {
        QTimer::singleShot(300,this,SLOT(stopLocationUpdates()));
    }
    history.pop();
    showWidget(history.pop());

}

void MainController::showPubMap(QString pubId)
{
    DataProvider d(this);
    BeerPub *pub = d.getPub(pubId);
    singleMap->showSinglePub(pub);
    showWidget(SinglePubMapView);
}

void MainController::positionUpdated(QGeoPositionInfo geoPositionInfo)
{
//    qDebug() << "Position updated";
    if (geoPositionInfo.isValid())
    {
        // Save the position information into a member variable
        myPositionInfo = geoPositionInfo;

        // Get the current location as latitude and longitude
        QGeoCoordinate geoCoordinate = geoPositionInfo.coordinate();
        qreal latitude = geoCoordinate.latitude();
        qreal longitude = geoCoordinate.longitude();
        if(ui->stackedWidget->currentIndex() == PubListView) {
            pubList->locationChanged(latitude,longitude);
        } else if (ui->stackedWidget->currentIndex() == BeerMapView) {
            map->locationChanged(latitude,longitude);
        }
       // qDebug() << QString("Latitude: %1 Longitude: %2").arg(latitude).arg(longitude);
    }
}
void MainController::stopLocationUpdates()
{
    if (locationDataSource)
    {
        locationDataSource->stopUpdates();

    }
}

void MainController::startLocationUpdates()
{
    // Obtain the location data source if it is not obtained already
   // qDebug() << "Start GPS";
    if (!locationDataSource)
    {
     //   qDebug() << "nolocation data source";
        locationDataSource =
            QGeoPositionInfoSource::createDefaultSource(this);
        // Whenever the location data source signals that the current
        // position is updated, the positionUpdated function is called
        connect(locationDataSource, SIGNAL(positionUpdated(QGeoPositionInfo)),
                      this, SLOT(positionUpdated(QGeoPositionInfo)));
        // Start listening for position updates

//         QFlags<QGeoPositionInfoSource::PositioningMethod> flags = locationDataSource->preferredPositioningMethods();
//         if(flags.testFlag(QGeoPositionInfoSource::SatellitePositioningMethods))
//             qDebug() << "satelite available";
//         if(flags.testFlag(QGeoPositionInfoSource::NonSatellitePositioningMethods))
//             qDebug() << "non-satelite available";
//         if(flags.testFlag(QGeoPositionInfoSource::NonSatellitePositioningMethods))
//             qDebug() << "nall methods available";
    }
    locationDataSource->requestUpdate();
    locationDataSource->setUpdateInterval(15000);
    locationDataSource->startUpdates();
}

void MainController::onUpdateAvailable(const QString &version)
{
    if(!version.isEmpty()) {
        mainWidget->setUpdateVersion(version);
    }
}

void MainController::changeEvent(QEvent* event)
{
    if (event->type() == QEvent::LanguageChange)
    {
        ui->retranslateUi(this);
    }
    QWidget::changeEvent(event);
}

MainController::~MainController()
{
    delete dbManager;
    delete ui;
}
