#ifndef MAINCONTROLLER_H
#define MAINCONTROLLER_H

#include <QMainWindow>
#include "brandtabs.h"
#include "alausradaras.h"
#include "beercounter.h"
#include "publist.h"
#include "brandlist.h"
#include "beermap.h"
#include "feelingthirsty.h"

#include <qgeopositioninfosource.h>
#include <qgeosatelliteinfosource.h>
#include <qgeopositioninfo.h>
#include <qgeosatelliteinfo.h>
#include "updatechecker.h"
QTM_USE_NAMESPACE

namespace Ui {
    class MainController;
}

class MainController : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainController(QWidget *parent = 0);
    ~MainController();

private:
    Ui::MainController *ui;

    BrandTabs *brandTabs;
    AlausRadaras *mainWidget;

    WaitDialog *progress;
    DbManager *dbManager;
    DbPopulator *populator;
    BeerCounter *counter;
    PubList *pubList;
    PubView *pubView;
    FeelingThirsty *feelingThirsty;
    BrandList *brandList;
    BeerMap *map;
    BeerMap *singleMap;
    UpdateChecker updater;

    QStack<int> history;
    void clearHistory();

    QPointer<QGeoPositionInfoSource> locationDataSource;
    QGeoPositionInfo myPositionInfo;


private slots:
    void dbInitFinished();
    void showWidget(int index);
    void showBrandTabs();
    void showCounter();
    void showPubList(PubListType type, QString id, QString header);
    void showBrandList(BrandListType type, QString id, QString header);
    void showMap(QList<BeerPub*> pubs);
    void showPub(QString pubId);
    void showMain();
    void showFeelingThirsty();
    void goBack();
    void showPubMap(QString pubId);
    void positionUpdated(QGeoPositionInfo geoPositionInfo);
    void startLocationUpdates();
    void stopLocationUpdates();
    void onUpdateAvailable(QString version);

};

#endif // MAINCONTROLLER_H
