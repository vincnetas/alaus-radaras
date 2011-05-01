#include "dbupdater.h"
#include "qdebug.h"
#include "dbmanager.h"
#include "brandtag.h"

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
    QVector<PubBrand> pubBrands;
    foreach(QVariant rawPub, update["pubs"].toList()) {
        QVariantMap pubData = rawPub.toMap();
        Pub pub = populatePub(pubData);
        pubs.append(pub);

        foreach(PubBrand pubBrand, popuplatePubBrands(pub.id, pubData["beerIds"].toList())) {
            pubBrands.append(pubBrand);
        }
        //FIXME : we don't have pubBrands ready, so no delete
        dbManager.deletePubBrands(pub.id);
    }
    dbManager.populatePubs(pubs);
    //FIXME : structure is with id, which we don't have
    dbManager.populatePubBrands(pubBrands);

    QVector<Brand> brands;
    QVector<BrandTag> brandTags;
    foreach(QVariant rawPub, update["beers"].toList()) {
        QVariantMap brandData = rawPub.toMap();
        Brand brand = populateBrand(brandData);
        brands.append(brand);

        foreach(BrandTag brandTag, popuplateBrandTags(brand.id, brandData["tags"].toList())) {
            brandTags.append(brandTag);
        }
        //FIXME: ids are wrong
        dbManager.deleteBrandTags(brand.id);
    }
    //FIXME: ids are wrong
    dbManager.populateBrands(brands);
    dbManager.populateBrandTags(brandTags);

    return true;
}
const Brand& DbUpdater::populateBrand(const QVariantMap &brandData)
{
    Brand brand;
    brand.title = brandData["title"].toString();
    brand.icon = brandData["icon"].toString();
    brand.description = brandData["description"].toString();
    brand.id = brandData["id"].toString(); //FIXME : this is incorrect id
    return brand;
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

const QVector<PubBrand>& DbUpdater::popuplatePubBrands(const QString pubId, const QList<QVariant> &brandIds)
{
    QVector<PubBrand> pubBrands;
    foreach(QVariant brandId, brandIds) {
       PubBrand pubBrand;
       pubBrand.brandId = brandId.toString();
       pubBrand.pubId = pubId;
       pubBrands.append(pubBrand);
    }
    return pubBrands;
}

const QVector<BrandTag>& DbUpdater::popuplateBrandTags(const QString brandId, const QList<QVariant> &tags)
{
    QVector<BrandTag> brandTags;
    foreach(QVariant tag, tags) {
       BrandTag brandTag;
       brandTag.brandId = brandId;
       brandTag.tag = tag.toString();
       brandTags.append(brandTag);
    }
    return brandTags;
}
