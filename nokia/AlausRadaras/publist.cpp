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
PubList::PubList(QWidget *parent) :
    QWidget(parent),
    pubListModel(0),
    ui(new Ui::PubList)
{
    ui->setupUi(this);

    dataProvider = new DataProvider(this);
    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));

    pubListScroller = new QsKineticScroller(this);
    pubListScroller->enableKineticScrollFor( ui->pubListView);

    QListView::connect(ui->pubListView, SIGNAL(pressed(QModelIndex)) , this , SLOT(pubList_itemClicked(QModelIndex)));


}

void PubList::showPubList(PubListType type, QString id, QString header)
{
    if(pubs.size() > 0) {

        for(int i = 0; i < pubs.size() ; i++) {
            delete pubs[i];
        }
    }
    if(pubListModel) {
        delete pubListModel;
    }

    switch(type)
    {
        case ALL:
            pubs = dataProvider->getAllPubs();
        break;
        case BRAND:
            pubs = dataProvider->getPubsByBrandId(id);
        break;
        case COUNTRY:
            pubs = dataProvider->getPubsByCountry(id);
        break;
        case TAG:
            pubs = dataProvider->getPubsByTag(id);
        break;
    }

    pubListModel = new PubListModel(this, pubs);
    ui->pubListView->setModel(pubListModel);

    setHeader(header);
}

void PubList::on_btnMap_clicked()
{
    emit MapSelected(this->pubs);
}

void PubList::setHeader(QString text)
{
    this->ui->txtHeader->setText(text);
}

void PubList::on_btnBack_clicked()
{
    emit Back();
}

void PubList::pubList_itemClicked(const QModelIndex &current)
{

    emit PubSelected(current.data(Qt::EditRole).toString());

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
    delete dataProvider;
    delete ui;
}
