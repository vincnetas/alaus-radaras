#ifndef PUBLIST_H
#define PUBLIST_H

#include <QWidget>
#include <QList>
#include <QModelIndex>

#include <qgeopositioninfosource.h>
#include <qgeosatelliteinfosource.h>
#include <qgeopositioninfo.h>
#include <qgeosatelliteinfo.h>
#include <qskineticscroller.h>

#include "publistmodel.h"
#include "dataprovider.h"
#include "enums.h"
#include <beerpub.h>

QTM_USE_NAMESPACE

namespace Ui {
    class PubList;
}



class PubList : public QWidget
{
    Q_OBJECT

public:
    explicit PubList(QWidget *parent = 0);
    ~PubList();
    void setHeader(QString text);
    void showPubList(PubListType type, QString id, QString header);
private slots:
    void pubList_itemClicked(const QModelIndex &current);
    void on_btnBack_clicked();
    void on_btnMap_clicked();
    void positionUpdated(QGeoPositionInfo geoPositionInfo);

private:

    Ui::PubList *ui;
    QList<BeerPub*> pubs;
    PubListModel* pubListModel;
    DataProvider* dataProvider;

    QPointer<QGeoPositionInfoSource> locationDataSource;
    QGeoPositionInfo myPositionInfo;

    QsKineticScroller *pubListScroller;

    void startGPS();

signals:
    void MapSelected(QList<BeerPub*> pubs);
    void PubSelected(QString pubId);
    void Back();
};

#endif // PUBLIST_H
