#include "maincontroller.h"
#include "ui_maincontroller.h"
#include <QDebug>
#include <QGeoPositionInfoSource>
enum Views { MainView, BrandTabsView, BeerCounterView, PubListView, PubInfoView, FeelingThirstyView, BrandListView, BeerMapView, SinglePubMapView };

MainController::MainController(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainController)
{

    dbManager = new DbManager();
    dbManager->init();

    brandTabs = new BrandTabs(this);
    mainWidget = new AlausRadaras(this);
    counter = new BeerCounter(this);
    pubList = new PubList(this);
    pubView = new PubView(this);
    feelingThirsty = new FeelingThirsty(this);
    brandList = new BrandList(this);
    map = new BeerMap(this);
    singleMap = new BeerMap(this);

    ui->setupUi(this);
    ui->stackedWidget->addWidget(mainWidget);
    ui->stackedWidget->addWidget(brandTabs);
    ui->stackedWidget->addWidget(counter);
    ui->stackedWidget->addWidget(pubList);
    ui->stackedWidget->addWidget(pubView);
    ui->stackedWidget->addWidget(feelingThirsty);
    ui->stackedWidget->addWidget(brandList);
    ui->stackedWidget->addWidget(map);
    ui->stackedWidget->addWidget(singleMap);

    connect(mainWidget,SIGNAL(BrandsSelected()),this,SLOT(showBrandTabs()));
    connect(mainWidget,SIGNAL(ExitApp()),this,SLOT(close()));
    connect(mainWidget,SIGNAL(LetsCount()),this,SLOT(showCounter()));
    connect(mainWidget,SIGNAL(PubListSelected(PubListType,QString,QString)),this,SLOT(showPubList(PubListType,QString,QString)));
    connect(mainWidget,SIGNAL(FeelingLucky()),this,SLOT(showFeelingThirsty()));

    connect(pubList,SIGNAL(PubSelected(QString)),this,SLOT(showPub(QString)));
    connect(pubList,SIGNAL(MapSelected(QList<BeerPub*>)),this, SLOT(showMap(QList<BeerPub*>)));
    connect(pubList,SIGNAL(Back()),this,SLOT(goBack()));

    connect(counter,SIGNAL(Back()),this,SLOT(goBack()));

    connect(brandTabs,SIGNAL(Back()),this,SLOT(goBack()));
    connect(brandTabs,SIGNAL(PubListSelected(PubListType,QString,QString)), this, SLOT(showPubList(PubListType,QString,QString)));
    connect(brandTabs,SIGNAL(BrandListSelected(BrandListType,QString,QString)), this, SLOT(showBrandList(BrandListType,QString,QString)));

    connect(feelingThirsty,SIGNAL(PubSelected(QString)),this,SLOT(showPub(QString)));
    connect(feelingThirsty,SIGNAL(PubListSelected(PubListType,QString,QString)), this, SLOT(showPubList(PubListType,QString,QString)));
    connect(feelingThirsty,SIGNAL(Back()),this,SLOT(goBack()));

    connect(brandList,SIGNAL(Back()),this,SLOT(goBack()));
    connect(brandList,SIGNAL(PubListSelected(PubListType,QString,QString)), this, SLOT(showPubList(PubListType,QString,QString)));

    connect(pubView,SIGNAL(Back()),this,SLOT(goBack()));
    connect(pubView,SIGNAL(PubMapSelected(QString)),this,SLOT(showPubMap(QString)));

    connect(map,SIGNAL(Back()),this,SLOT(goBack()));
    connect(map,SIGNAL(PubSelected(QString)),this,SLOT(showPub(QString)));

    connect(singleMap,SIGNAL(Back()),this,SLOT(goBack()));
    showWidget(MainView);

    connect(&updater,SIGNAL(updateAvalable(QString)),SLOT(onUpdateAvailable(QString)));
    QTimer::singleShot(500,&updater,SLOT(checkForUpdates()));

    if(!dbManager->isDbLatest()) {
        progress = new WaitDialog(this);
        progress->setModal(true);
        progress->showFullScreen();

        populator = new DbPopulator(this,dbManager);
        populator->start();
        connect(populator,SIGNAL(finished()),this,SLOT(dbInitFinished()));
        //safeguard. Let it crash. after one minute, if its not done.
        QTimer::singleShot(60000,this,SLOT(dbInitFinished()));

    } else {
        populator = NULL;
        progress = NULL;
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

void MainController::showBrandTabs()
{
    showWidget(BrandTabsView);
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
void MainController::showBrandList(BrandListType type, QString id, QString header)
{
    brandList->showBrands(type, id, header);
    showWidget(BrandListView);
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
        progress->close();
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

void MainController::onUpdateAvailable(QString version)
{
    mainWidget->setUpdateVersion(version);
}

MainController::~MainController()
{
    delete dbManager;
    delete ui;
}
