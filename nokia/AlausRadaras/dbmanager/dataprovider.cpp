#include "dataprovider.h"
#include <QSqlQuery>
#include <QVariant>
#include <QDebug>
#include <QSqlError>
#include "dbmanager.h"

DataProvider::DataProvider(QObject *parent) :
    QObject(parent)
{
}

const QVector<BeerPub> DataProvider::getAllPubs()
{
    return this->generatePubsFromQuery(DbManager::SELECT_BEERPUB_PUBS);
}

const QVector<BeerPub>  DataProvider::getPubsByBeerId(QString beerId)
{
    return this->generatePubsFromQuery(QSqlQuery(DbManager::SELECT_BEERPUB_PUBS_BY_BEER.arg(beerId)));
}

const QVector<BeerPub> DataProvider::getPubsByCountry(QString countryCode)
{
    return this->generatePubsFromQuery(QSqlQuery(DbManager::SELECT_BEERPUB_PUBS_BY_COUNTRY.arg(countryCode)));
}

const QVector<BeerPub> DataProvider::getPubsByTag(QString tagCode)
{
    return this->generatePubsFromQuery(QSqlQuery(DbManager::SELECT_BEERPUB_PUBS_BY_TAG.arg(tagCode)));
}

const BeerPub DataProvider::getPub(QString pubId)
{
    return this->generatePubsFromQuery(QSqlQuery(DbManager::SELECT_BEERPUB_PUB.arg(pubId))).at(0);
}

FeelingLuckyInfo DataProvider::feelingLucky()
{
    FeelingLuckyInfo lucky;

    QSqlQuery pubQuery(DbManager::SELECT_RANDOM_PUB);
    while(pubQuery.next()) {
        lucky.pubId = pubQuery.value(0).toString();
        lucky.pubName = pubQuery.value(1).toString();
    }
    pubQuery.clear();

    QSqlQuery beerQuery(DbManager::SELECT_RANDOM_BEER_BY_PUB.arg(lucky.pubId));

    while(beerQuery.next()) {
        lucky.beerId = beerQuery.value(0).toString();
        lucky.beerName = beerQuery.value(1).toString();
    }
    beerQuery.clear();
    return lucky;
}


QString DataProvider::getQuote(int count)
{


    QSqlQuery query(DbManager::SELECT_RANDOM_QUOTE.arg(count));
    while(query.next()) {
       return query.value(0).toString();
    }
    query.clear();
    return tr("Dar po viena!");
}

const QVector<BeerPub> DataProvider::generatePubsFromQuery(QSqlQuery query)
{

    if(query.lastError().isValid())
       qDebug() << query.lastError();

    QVector<BeerPub> pubs;
    while(query.next()) {
        BeerPub pub;
        pub.id = query.value(0).toString();
        pub.title = query.value(1).toString();
        pub.longitude = query.value(2).toDouble();
        pub.latitude = query.value(3).toDouble();
        pub.tile = QPoint(query.value(4).toInt(),query.value(5).toInt());
        pub.tilePixel = QPoint(query.value(6).toInt(),query.value(7).toInt());
        pub.distance = -1;
        pub.city = query.value(8).toString();
        pubs.append(pub);
        //qDebug() << query->value(4).toString() << " "  << query->value(5).toString();
    }

    query.clear();

   return pubs;

}
