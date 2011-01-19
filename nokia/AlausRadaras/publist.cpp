#include "publist.h"
#include "ui_publist.h"
#include <QList>
#include <QSqlQuery>
#include <QDebug>
#include <beerpub.h>
#include <pubview.h>
#include <publistmodel.h>
#include <lightmaps.h>
#include "dataprovider.h"
#include <QGeoCoordinate>
#include <QGeoPositionInfoSource>
#include "viewutils.h"
#include "qskineticscroller.h"
PubList::PubList(QWidget *parent, PubListType type, QString id) :
    QMainWindow(parent),
    ui(new Ui::PubList),
    id(id),
    type(type)
{
    ui->setupUi(this);

    dataProvider = new DataProvider(this);
    pubView = NULL;
    map = NULL;

    switch(this->type)
    {
        case ALL:
            pubs = dataProvider->getAllPubs();
        break;
        case BRAND:
            pubs = dataProvider->getPubsByBrandId(this->id);
        break;
        case COUNTRY:
            pubs = dataProvider->getPubsByCountry(this->id);
         break;
            case TAG:
                 pubs = dataProvider->getPubsByTag(this->id);
        break;
    }

    //this->startGPS();

    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));

    pubListModel = new PubListModel(this, pubs);
    ui->pubListView->setModel(pubListModel);

    pubListScroller = new QsKineticScroller(this);
    pubListScroller->enableKineticScrollFor( ui->pubListView);

    QListView::connect(ui->pubListView, SIGNAL(pressed(QModelIndex)) , this , SLOT(pubList_itemClicked(QModelIndex)));


}


void PubList::showPub(QString pubId)
{
    QString newPub = pubId;
    pubView = new PubView(this,newPub);
    pubView->setModal(true);
    pubView->showFullScreen();
}

void PubList::on_btnMap_clicked()
{
    if(map == NULL) {
        map = new BeerMap(this);
    }
    map->setPubs(this->pubs);
    map->showFullScreen();
}

void PubList::pubview_accepted()
{
    delete pubView;
    pubView = NULL;
}

void PubList::setHeader(QString text)
{
    this->ui->txtHeader->setText(text);
}

void PubList::on_btnBack_clicked()
{
 this->close();
}

void PubList::pubList_itemClicked(const QModelIndex &current)
{

    QVariant data = current.data(Qt::EditRole);
    this->showPub(data.toString());

}

void PubList::startGPS()
{
    // Obtain the location data source if it is not obtained already
    if (!locationDataSource)
    {
        locationDataSource =
            QGeoPositionInfoSource::createDefaultSource(this);
        // Whenever the location data source signals that the current
        // position is updated, the positionUpdated function is called
        connect(locationDataSource, SIGNAL(positionUpdated(QGeoPositionInfo)),
                      this, SLOT(positionUpdated(QGeoPositionInfo)));

        // Start listening for position updates
        locationDataSource->startUpdates();
    }
}

void PubList::positionUpdated(QGeoPositionInfo geoPositionInfo)
{
    if (geoPositionInfo.isValid())
    {
//        // We've got the position. No need to continue the listening.
//        locationDataSource->stopUpdates();

        // Save the position information into a member variable
        myPositionInfo = geoPositionInfo;

        // Get the current location as latitude and longitude
        QGeoCoordinate geoCoordinate = geoPositionInfo.coordinate();
        qreal latitude = geoCoordinate.latitude();
        qreal longitude = geoCoordinate.longitude();
        qDebug() << QString("Latitude: %1 Longitude: %2").arg(latitude).arg(longitude);
    }
}


PubList::~PubList()
{
    for(int i = 0; i < pubs.size() ; i++) {
        delete pubs[i];
    }
    delete ui;
    delete pubView;
    delete pubListModel;
    delete dataProvider;
    delete map;
}
