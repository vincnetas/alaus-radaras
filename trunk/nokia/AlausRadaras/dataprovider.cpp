#include "dataprovider.h"
#include <QSqlQuery>
#include <QVariant>
DataProvider::DataProvider(QObject *parent) :
    QObject(parent)
{
}

QList<BeerPub*> DataProvider::getAllPubs()
{
    return this->generatePubsFromQuery(new QSqlQuery("SELECT id, title, longtitude, latitude, tile_x, tile_y, tile_pixel_x, tile_pixel_y, city  from pubs"));
}

QList<BeerPub*> DataProvider::getPubsByBrandId(QString brandId)
{
    return this->generatePubsFromQuery(new QSqlQuery(QString("SELECT id, title, longtitude, latitude, tile_x, tile_y, tile_pixel_x, tile_pixel_y, city FROM pubs p INNER JOIN pubs_brands pb ON p.id = pb.pub_id AND pb.brand_id = '%1'").arg(brandId)));
}

QList<BeerPub*> DataProvider::getPubsByCountry(QString countryCode)
{
    return this->generatePubsFromQuery(new QSqlQuery(QString("SELECT DISTINCT id, title, longtitude, latitude, tile_x, tile_y, tile_pixel_x, tile_pixel_y, city  FROM pubs p  INNER JOIN pubs_brands pb ON p.id = pb.pub_id  INNER JOIN brands_countries bc ON bc.brand_id = pb.brand_id AND bc.country = '%1'").arg(countryCode)));
}

QList<BeerPub*> DataProvider::getPubsByTag(QString tagCode)
{
    return this->generatePubsFromQuery(new QSqlQuery(QString("SELECT DISTINCT id, title, longtitude, latitude, tile_x, tile_y, tile_pixel_x, tile_pixel_y, city FROM pubs p  INNER JOIN pubs_brands pb ON p.id = pb.pub_id INNER JOIN brands_tags bt ON bt.brand_id = pb.brand_id AND bt.tag = '%1'").arg(tagCode)));
}

BeerPub* DataProvider::getPub(QString pubId)
{
    return this->generatePubsFromQuery(new QSqlQuery(QString("SELECT id, title, longtitude, latitude, tile_x, tile_y, tile_pixel_x, tile_pixel_y, city  from pubs where id='%1'").arg(pubId))).at(0);
}

FeelingLuckyInfo DataProvider::feelingLucky()
{
    FeelingLuckyInfo lucky;

    QSqlQuery pubQuery("SELECT id, title || '\n(' || city || ')' FROM pubs p  INNER JOIN pubs_brands pb ON p.id = pb.pub_id  ORDER BY RANDOM() LIMIT 1");
    while(pubQuery.next()) {
        lucky.pubId = pubQuery.value(0).toString();
        lucky.pubName = pubQuery.value(1).toString();
    }
    pubQuery.clear();

    QSqlQuery brandQuery(QString("SELECT id, title FROM brands b  INNER JOIN pubs_brands pb ON b.id = pb.brand_id WHERE pb.pub_id= '%1' ORDER BY RANDOM() LIMIT 1").arg(lucky.pubId));

    while(brandQuery.next()) {
        lucky.brandId = brandQuery.value(0).toString();
        lucky.brandName = brandQuery.value(1).toString();
    }
    brandQuery.clear();
    return lucky;
}


QString DataProvider::getQoute(int count)
{


    QSqlQuery query(QString("SELECT text FROM qoutes q WHERE q.amount = %1 ORDER BY RANDOM() LIMIT 1").arg(count));
    while(query.next()) {
       return query.value(0).toString();
    }
    query.clear();
    return tr("Dar po viena!");
}

QList<BeerPub*> DataProvider::generatePubsFromQuery(QSqlQuery* query)
{
    QList<BeerPub*> pubs;
    while(query->next()) {
        BeerPub* pub = new BeerPub();
        pub->setId(query->value(0).toString());
        pub->setTitle(query->value(1).toString());
        pub->setLongitude(query->value(2).toDouble());
        pub->setLatitude(query->value(3).toDouble());
        pub->setTile(QPoint(query->value(4).toInt(),query->value(5).toInt()));
        pub->setTilePixel(QPoint(query->value(6).toInt(),query->value(7).toInt()));
        pub->setDistance(-1);
        pub->setCity(query->value(8).toString());
        pubs.append(pub);
        //qDebug() << query->value(4).toString() << " "  << query->value(5).toString();
    }

    query->clear();

   return pubs;

}
