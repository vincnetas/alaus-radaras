#ifndef MAINCONTROLLER_H
#define MAINCONTROLLER_H

#include <QMainWindow>
#include "beertabs.h"
#include "alausradaras.h"
#include "beercounter.h"
#include "publist.h"
#include "beerlist.h"
#include "beermap.h"
#include "feelingthirsty.h"

#include <qgeopositioninfosource.h>
#include <qgeosatelliteinfosource.h>
#include <qgeopositioninfo.h>
#include <qgeosatelliteinfo.h>
#include "appupdatechecker.h"
#include "dbupdatedownloader.h"
#include "dbmanager/dbpopulatorrunner.h"
QTM_USE_NAMESPACE

namespace Ui {
    class MainController;
}

class MainController : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainController(QWidget *parent = 0);
    void changeEvent(QEvent* event);
    ~MainController();

private:
    Ui::MainController *ui;

    BeerTabs *beerTabs;
    AlausRadaras *mainWidget;

    WaitDialog *progress;
    DbManager *dbManager;
    DbPopulatorRunner *populator;
    BeerCounter *counter;
    PubList *pubList;
    PubView *pubView;
    FeelingThirsty *feelingThirsty;
    BeerList *beerList;
    BeerMap *map;
    BeerMap *singleMap;
    AppUpdateChecker updater;
    DbUpdateDownloader downloader;

    QStack<int> history;
    void clearHistory();

    QPointer<QGeoPositionInfoSource> locationDataSource;
    QGeoPositionInfo myPositionInfo;


private slots:
    void dbInitFinished();
    void showWidget(int index);
    void showBeerTabs();
    void showCounter();
    void showPubList(PubListType type, QString id, QString header);
    void showBeerList(BeerListType type, QString id, QString header);
    void showMap(QList<BeerPub*> pubs);
    void showPub(QString pubId);
    void showMain();
    void showFeelingThirsty();
    void goBack();
    void showPubMap(QString pubId);
    void positionUpdated(QGeoPositionInfo geoPositionInfo);
    void startLocationUpdates();
    void stopLocationUpdates();
    void onUpdateAvailable(const QString &version);
    void initDbUpdate();

};

#endif // MAINCONTROLLER_H
