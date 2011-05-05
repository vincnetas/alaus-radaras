#include "dbupdater.h"
#include "qdebug.h"
#include "dbmanager.h"
#include "beertag.h"

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
        pubs.append(pub);

        foreach(PubBeer pubBeer, popuplatePubBeers(pub.id, pubData["beerIds"].toList())) {
            pubBeers.append(pubBeer);
        }
        dbManager.deletePubBeers(pub.id);
    }
    dbManager.populatePubs(pubs);
    dbManager.populatePubBeers(pubBeers);

    QVector<Beer> beers;
    QVector<BeerTag> beerTags;
    foreach(QVariant rawPub, update["beers"].toList()) {
        QVariantMap beerData = rawPub.toMap();
        Beer beer = populateBeer(beerData);
        beers.append(beer);

        foreach(BeerTag beerTag, popuplateBeerTags(beer.id, beerData["tags"].toList())) {
            beerTags.append(beerTag);
        }
        dbManager.deleteBeerTags(beer.id);
    }
    dbManager.populateBeers(beers);
    dbManager.populateBeerTags(beerTags);

    QVector<Brand> brands;
    foreach(QVariant rawPub, update["brands"].toList()) {
        QVariantMap brandData = rawPub.toMap();
        Brand brand = populateBrand(brandData);
        brands.append(brand);
    }
    dbManager.populateBrands(brands);

    return true;
}

const Brand& DbUpdater::populateBrand(const QVariantMap &brandData)
{
    Brand brand;
    brand.id = brandData["id"].toString();
    brand.title = brandData["title"].toString();
    brand.country = brandData["country"].toString();
    return brand;
}

const Beer& DbUpdater::populateBeer(const QVariantMap &beerData)
{
    Beer beer;
    beer.title = beerData["title"].toString();
    beer.brandId = beerData["brandId"].toString();
    beer.icon = beerData["icon"].toString();
    beer.description = beerData["description"].toString();
    beer.id = beerData["id"].toString();
    return beer;
}


const Pub& DbUpdater::populatePub(const QVariantMap &pubData)
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

const QVector<PubBeer>& DbUpdater::popuplatePubBeers(const QString pubId, const QVariantList &beerIds)
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

const QVector<BeerTag>& DbUpdater::popuplateBeerTags(const QString beerId, const QVariantList &tags)
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
