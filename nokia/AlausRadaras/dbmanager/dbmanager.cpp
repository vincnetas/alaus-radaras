#include "dbmanager.h"
#include <QSqlQuery>
#include <QVariant>
#include <QStringList>
#include <QtCore/QDebug>
#include <QTextCodec>
#include <QDebug>
#include <QSqlError>
#include "calculationhelper.h"
#include <QStringBuilder>

QString DbManager::QUERY_INSERT_BEERS = "INSERT OR REPLACE INTO beers (id, brand_id, title, icon) VALUES (:id, :brand_id, :title, :icon)";
QString DbManager::QUERY_INSERT_BRANDS = "INSERT OR REPLACE INTO brands (id, title, country) VALUES (:id, :title, :country)";
QString DbManager::QUERY_INSERT_PUBS = "INSERT OR REPLACE INTO pubs VALUES (:id, :title, :city, :longitude, :latitude,  :address, :notes, :phone, :url, :tile_x, :tile_y,:tile_pixel_x, :tile_pixel_y )";
QString DbManager::QUERY_INSERT_QUOTES = "INSERT OR REPLACE INTO quotes VALUES (:amount, :text)";
QString DbManager::QUERY_INSERT_BEER_TAGS = "INSERT OR REPLACE INTO beer_tags VALUES (:beer_id, :tag)";
QString DbManager::QUERY_INSERT_PUB_BEERS = "INSERT OR REPLACE INTO pub_beers VALUES (:pub_id,:beer_id)";
DbManager::DbManager(QObject *parent) : QObject(parent)
{
}

bool DbManager::init()
{
    db = QSqlDatabase::addDatabase("QSQLITE");
    db.setDatabaseName("alaus.radaras.db");
    if (!db.open()) {
        return false;
    }
    return true;
}

bool DbManager::isDbLatest()
{
    QSqlQuery query("PRAGMA user_version");
         while (query.next()) {
             uint userVersion = query.value(0).toUInt();
             return userVersion == DB_VERSION;
         }
    query.clear();
    return false;
}

bool DbManager::createDb()
{
    this->dropTables();
    QSqlQuery query;
    query.exec("CREATE TABLE pubs("
                   "id 		TEXT PRIMARY KEY, "
                   "title 		TEXT NOT NULL, "
                   "city 		TEXT NOT NULL, "
                   "longtitude REAL NOT NULL, "
                   "latitude 	REAL NOT NULL, "
                   "address 	TEXT, "
                   "notes	 	TEXT, "
                   "phone	 	TEXT, "
                   "url	 	TEXT,"
                   "tile_x INTEGER,"
                   "tile_y INTEGER,"
                   "tile_pixel_x INTEGER,"
                   "tile_pixel_y INTEGER);");

    query.exec("CREATE TABLE brands("
               "id 			TEXT PRIMARY KEY, "
               "title 			TEXT NOT NULL, "
               "country			TEXT NOT NULL);");

    query.exec("CREATE TABLE beers("
               "id 			TEXT PRIMARY KEY, "
               "brand_id 		TEXT NOT NULL, "
               "title 			TEXT NOT NULL, "
               "icon			TEXT, "
               "description 	TEXT);");

    query.exec("CREATE TABLE pub_beers("
               "pub_id			TEXT NOT NULL, "
               "beer_id 		TEXT NOT NULL);");

    query.exec("CREATE TABLE beer_tags("
                "beer_id			TEXT NOT NULL,"
                "tag				TEXT NOT NULL);");

    query.exec("CREATE TABLE quotes("
                "amount			INTEGER NOT NULL,"
                "text			TEXT NOT NULL);");

    query.clear();
    return true;

}

void DbManager::populateBeers(const QVector<Beer> &beers)
{
     qDebug() << "begin inserting beer";
     QSqlQuery query;
     query.prepare(QUERY_INSERT_BEERS);

     for (int i = 0; i < beers.size(); ++i) {
         Beer beer = beers.at(i);
         query.bindValue(":id", beer.id);
         query.bindValue(":title", beer.title);
         query.bindValue(":icon", beer.icon);
         query.bindValue(":brand_id", beer.brandId);
         query.exec();
         if(query.lastError().isValid())
            qDebug() << query.lastError();
     }
     query.clear();
}


void DbManager::populateBrands(const QVector<Brand> &brands)
{
     qDebug() << "begin inserting brands";
     QSqlQuery query;
     query.prepare(QUERY_INSERT_BRANDS);

     for (int i = 0; i < brands.size(); ++i) {
         Brand brand = brands.at(i);
         query.bindValue(":id", brand.id);
         query.bindValue(":title", brand.title);
         query.bindValue(":country", brand.country);
         query.exec();
         if(query.lastError().isValid())
            qDebug() << query.lastError();
     }
     query.clear();
}

void DbManager::populatePubs(const QVector<Pub> &pubs)
{
     qDebug() << "begin inserting pubs";
     QSqlQuery query;
     query.prepare(QUERY_INSERT_PUBS);

     for (int i = 0; i < pubs.size(); ++i) {
        Pub pub = pubs.at(i);
        Location loc = getLocation(pub.latitude,pub.longtitude);
        query.bindValue(":id", pub.id);
        query.bindValue(":title", pub.title);
        query.bindValue(":address", pub.address);
        query.bindValue(":city", pub.city);
        query.bindValue(":phone", pub.phone);
        query.bindValue(":url", pub.url);
        query.bindValue(":latitude", pub.latitude);
        query.bindValue(":longitude", pub.longtitude);
        query.bindValue(":notes",pub.notes);
        query.bindValue(":tile_x", loc.tile.x());
        query.bindValue(":tile_y", loc.tile.y());
        query.bindValue(":tile_pixel_x", loc.tilePixel.x());
        query.bindValue(":tile_pixel_y",loc.tilePixel.y());
        query.exec();
        if(query.lastError().isValid())
            qDebug() << query.lastError();
     }
     query.clear();
}

void DbManager::populateQuotes(const QVector<Quote> &quotes)
{
    qDebug() << "begin inserting quotes";
    QSqlQuery query;
    query.prepare(QUERY_INSERT_QUOTES);

    for (int i = 0; i < quotes.size(); ++i) {
        Quote quote = quotes.at(i);
        query.bindValue(":amount", quote.amount);
        query.bindValue(":text", quote.text);
        query.exec();
        if(query.lastError().isValid())
           qDebug() << query.lastError();
    }
    query.clear();
}

void DbManager::populateBeerTags(const QVector<BeerTag> &beerTags)
{
    qDebug() << "begin inserting beer tags relationship";
    QSqlQuery query;
    query.prepare(QUERY_INSERT_BEER_TAGS);

    for (int i = 0; i < beerTags.size(); ++i) {
        BeerTag beerTag = beerTags.at(i);
        query.bindValue(":beer_id", beerTag.beerId);
        query.bindValue(":tag", beerTag.tag);
        query.exec();
        if(query.lastError().isValid())
           qDebug() << query.lastError();
    }
    query.clear();
}

void DbManager::populatePubBeers(const QVector<PubBeer> &pubBeers)
{
    qDebug() << "begin inserting pub beers relationship";
    QSqlQuery query;
    query.prepare(QUERY_INSERT_PUB_BEERS);

    for (int i = 0; i < pubBeers.size(); ++i) {
        PubBeer pubBeer = pubBeers.at(i);
        query.bindValue(":pub_id", pubBeer.pubId);
        query.bindValue(":beer_id", pubBeer.beerId);
        query.exec();
        if(query.lastError().isValid())
           qDebug() << query.lastError();
    }
    query.clear();
}

Location DbManager::getLocation(qreal latitude, qreal longitude)
{
    QPointF tileFullPoint = CalculationHelper::tileForCoordinate(latitude, longitude);
    Location loc;
    loc.tile = QPoint((int)tileFullPoint.x(),(int)tileFullPoint.y());
    loc.tilePixel = CalculationHelper::tilePixelForTile(tileFullPoint);
    return loc;
}

void DbManager::setLatest()
{
    QSqlQuery query;
    query.exec(QString("PRAGMA user_version=%1;").arg(DB_VERSION));
    query.clear();
}

void DbManager::deletePubBeers(const QString pubId)
{
    QSqlQuery query;
    query.exec(QString("DELETE FROM pubs_beers where pub_id=%1;").arg(pubId));
    query.clear();
}

void DbManager::deleteBeerTags(const QString beerId)
{
    QSqlQuery query;
    query.exec(QString("DELETE FROM beer_tags where beer_id=%1;").arg(beerId));
    query.clear();
}

void DbManager::dropTables()
{
    qDebug() << "Deleting tables and setting user version to initial value";
    QSqlQuery query;
    query.exec("drop table if exists beers");
    query.exec("drop table if exists brands");
    query.exec("drop table if exists pubs");
    query.exec("drop table if exists quotes");
    query.exec("drop table if exists beer_tags");
    query.exec("drop table if exists pub_beers");
    query.exec("PRAGMA user_version=1;");
    query.clear();
}

DbManager::~DbManager()
{
        if(db.isOpen()) {
            db.close();
        }
}
