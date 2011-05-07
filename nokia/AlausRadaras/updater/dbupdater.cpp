#include "dbupdater.h"
#include "qdebug.h"
#include "dbmanager.h"
#include "beertag.h"
#include "QDebug"

DbUpdater::DbUpdater(QObject *parent) :
    QObject(parent)
{
}


bool DbUpdater::updateDb(const QVariantMap &data)
{
    //FIXME : move this out of here. Could i have some Guice ?
    DbManager dbManager;
    QVariantMap update = data["update"].toMap();
    QVector<Pub> pubs;
    QVector<PubBeer> pubBeers;
    foreach(QVariant rawPub, update["pubs"].toList()) {
        QVariantMap pubData = rawPub.toMap();
        Pub pub = populatePub(pubData);
        qDebug() << "updating pub with id " << pub.id;
        pubs.append(pub);

        QVector<PubBeer> pubBeerResult = popuplatePubBeers(pub.id, pubData["beerIds"].toList());
        qDebug() << "number for beers for pub " << pubBeers.size();
        foreach(PubBeer pubBeer, pubBeerResult) {
            pubBeers.append(pubBeer);
        }
        qDebug() << "Deleting beers for pub " << pub.id;
        dbManager.deletePubBeers(pub.id);
    }
    qDebug() << "Number of pubs " << pubs.size();
    dbManager.populatePubs(pubs);
    dbManager.populatePubBeers(pubBeers);

    QVector<Beer> beers;
    QVector<BeerTag> beerTags;
    foreach(QVariant rawPub, update["beers"].toList()) {
        QVariantMap beerData = rawPub.toMap();
        Beer beer = populateBeer(beerData);
        qDebug() << "updating beer " << beer.id;
        beers.append(beer);

        QVector<BeerTag> beerTagResult = popuplateBeerTags(beer.id, beerData["tags"].toList());
        qDebug() << "number for beer tags " << beerTags.size();
        foreach(BeerTag beerTag, beerTagResult) {
            beerTags.append(beerTag);
        }
        qDebug() << "Deleting tags for beer " << beer.id;
        dbManager.deleteBeerTags(beer.id);
    }

    qDebug() << "Number of beers " << beers.size();
    dbManager.populateBeers(beers);
    dbManager.populateBeerTags(beerTags);

    QVector<Brand> brands;
    foreach(QVariant rawPub, update["brands"].toList()) {
        QVariantMap brandData = rawPub.toMap();
        Brand brand = populateBrand(brandData);
        qDebug() << "updating brand " << brand.id;
        brands.append(brand);
    }
    qDebug() << "Number of brands " << brands.size();
    dbManager.populateBrands(brands);


    QVariantMap deleteData = data["delete"].toMap();
    foreach(QVariant beerId, deleteData["beers"].toList()) {
        qDebug() << "deleting beer " << beerId;
        dbManager.deleteBeer(beerId.toString());
    }
    foreach(QVariant brandId, deleteData["brands"].toList()) {
        qDebug() << "deleting brand " << brandId;
        dbManager.deleteBrand(brandId.toString());
    }

    foreach(QVariant pubId, deleteData["pubs"].toList()) {
        qDebug() << "deleting pub " << pubId;
        dbManager.deletePub(pubId.toString());
    }


    return true;
}

const Brand DbUpdater::populateBrand(const QVariantMap &brandData)
{
    Brand brand;
    brand.id = brandData["id"].toString();
    brand.title = brandData["title"].toString();
    brand.country = brandData["country"].toString();
    return brand;
}

const Beer DbUpdater::populateBeer(const QVariantMap &beerData)
{
    Beer beer;
    beer.title = beerData["title"].toString();
    beer.brandId = beerData["brandId"].toString();
    beer.icon = beerData["icon"].toString();
    beer.description = beerData["description"].toString();
    beer.id = beerData["id"].toString();
    return beer;
}


const Pub DbUpdater::populatePub(const QVariantMap &pubData)
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

const QVector<PubBeer> DbUpdater::popuplatePubBeers(const QString pubId, const QVariantList &beerIds)
{
    QVector<PubBeer> pubBeers;
    foreach(QVariant beerId, beerIds) {
       PubBeer pubBeer;
       pubBeer.beerId = beerId.toString();
       pubBeer.pubId = pubId;
       pubBeers.append(pubBeer);
    }
    return pubBeers;
}

const QVector<BeerTag> DbUpdater::popuplateBeerTags(const QString beerId, const QVariantList &tags)
{
    QVector<BeerTag> beerTags;
    foreach(QVariant tag, tags) {
       BeerTag beerTag;
       beerTag.beerId = beerId;
       beerTag.tag = tag.toString();
       beerTags.append(beerTag);
    }
    return beerTags;
}
