#include "dbupdater.h"
#include "qdebug.h"
#include "pub.h"

DbUpdater::DbUpdater(QObject *parent) :
    QObject(parent)
{
}


bool DbUpdater::updateDb(const QVariantMap &data)
{
    QVariantMap update = data["update"].toMap();

    foreach(QVariant rawPub, update["pubs"].toList()) {
        QVariantMap pubData = rawPub.toMap();
        Pub pub = populatePub(pubData);
        qDebug() << "\t-" << pubData;
        //Submit pubs
        //Delete beers
        //Reinsert beers
    }

    foreach(QVariant rawPub, update["beers"].toList()) {
        QVariantMap beerData = rawPub.toMap();
        Pub pub = populatePub(pubData);
        qDebug() << "\t-" << pubData;
        //submit beers
        //delete tags
        //reinsert tags;
    }

    return true;
}

Pub DbUpdater::populatePub(const QVariantMap &pubData)
{
        Pub pub;
        pub.id = pubData["id"].toString();
        pub.title = pubData["title"].toString();
        pub.address = pubData["address"].toString();
        pub.city = pubData["city"].toString();
        pub.phone = pubData["phone"].toString();
        pub.url = pubData["url"].toString();
        pub.latitude = pubData["latitude"].toDouble();
        pub.longtitude = pubData["longtitude"].toDouble();
        pub.notes = pubData["notes"].toString();
        return pub;
}
