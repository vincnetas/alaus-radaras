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

QString DbManager::QUERY_INSERT_BRANDS = "INSERT OR REPLACE INTO brands (id, title, icon) VALUES (:id, :title, :icon)";
QString DbManager::QUERY_INSERT_TAGS = "INSERT OR REPLACE INTO tags  VALUES (:code, :title)";
QString DbManager::QUERY_INSERT_PUBS = "INSERT OR REPLACE INTO pubs VALUES (:id, :title, :city, :longitude, :latitude,  :address, :notes, :phone, :url, :tile_x, :tile_y,:tile_pixel_x, :tile_pixel_y )";
QString DbManager::QUERY_INSERT_COUNTRIES = "INSERT OR REPLACE INTO countries VALUES (:code, :name)";
QString DbManager::QUERY_INSERT_QUOTES = "INSERT OR REPLACE INTO countries VALUES (:code, :name)";
QString DbManager::QUERY_INSERT_BRAND_TAGS = "INSERT OR REPLACE INTO brands_tags VALUES (:brand_id, :country)";
QString DbManager::QUERY_INSERT_PUB_BRANDS = "INSERT OR REPLACE INTO pubs_brands VALUES (:pub_id,:brand_id)";
QString DbManager::QUERY_INSERT_BRAND_COUNTRIES = "INSERT OR REPLACE INTO brands_countries VALUES (:brand_id, :country)";
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
               "icon			TEXT, "
               "description 	TEXT);");

    query.exec("CREATE TABLE tags("
               "code			TEXT NOT NULL, "
               "title			TEXT NOT NULL);");

    query.exec("CREATE TABLE pubs_brands("
               "pub_id			TEXT NOT NULL, "
               "brand_id 		TEXT NOT NULL);");

    query.exec( "CREATE TABLE countries("
                "code			TEXT NOT NULL,"
                "name			TEXT NOT NULL);");

    query.exec("CREATE TABLE brands_countries("
               "brand_id			TEXT NOT NULL,"
               "country			TEXT NOT NULL);");

    query.exec("CREATE TABLE brands_tags("
                "brand_id			TEXT NOT NULL,"
                "tag				TEXT NOT NULL);");

    query.exec("CREATE TABLE quotes("
                "amount			INTEGER NOT NULL,"
                "text			TEXT NOT NULL);");

    query.clear();
    return true;

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
         query.bindValue(":icon", brand.icon);
         query.exec();
         if(query.lastError().isValid())
            qDebug() << query.lastError();
     }
     query.clear();
}

void DbManager::populateTags(const QVector<Tag> &tags)
{
     qDebug() << "begin inserting tags";
     QSqlQuery query;
     query.prepare(QUERY_INSERT_TAGS);

     for (int i = 0; i < tags.size(); ++i) {
         Tag tag = tags.at(i);
         query.bindValue(":code", tag.code);
         query.bindValue(":title", tag.title);
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

void DbManager::populateCountries(const QVector<Country> &countries)
{
    qDebug() << "begin inserting countries";
    QSqlQuery query;
    query.prepare(QUERY_INSERT_COUNTRIES);

    for (int i = 0; i < countries.size(); ++i) {
        Country country = countries.at(i);
        query.bindValue(":code", country.code);
        query.bindValue(":name", country.name);
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

void DbManager::populateBrandTags(const QVector<BrandTag> &brandTags)
{
    qDebug() << "begin inserting brands tags relationship";
    QSqlQuery query;
    query.prepare(QUERY_INSERT_BRAND_TAGS);

    for (int i = 0; i < brandTags.size(); ++i) {
        BrandTag brandTag = brandTags.at(i);
        query.bindValue(":brand_id", brandTag.brandId);
        query.bindValue(":tag", brandTag.tag);
        query.exec();
        if(query.lastError().isValid())
           qDebug() << query.lastError();
    }
    query.clear();
}

void DbManager::populatePubBrands(const QVector<PubBrand> &pubBrands)
{
    qDebug() << "begin inserting pub brands relationship";
    QSqlQuery query;
    query.prepare(QUERY_INSERT_PUB_BRANDS);

    for (int i = 0; i < pubBrands.size(); ++i) {
        PubBrand pubBrand = pubBrands.at(i);
        query.bindValue(":pub_id", pubBrand.pubId);
        query.bindValue(":brand_id", pubBrand.brandId);
        query.exec();
        if(query.lastError().isValid())
           qDebug() << query.lastError();
    }
    query.clear();
}

void DbManager::populateBrandCountries(const QVector<BrandCountry> &brandCountries)
{
    qDebug() << "begin inserting brands countries relationship";
    QSqlQuery query;
    query.prepare(QUERY_INSERT_BRAND_COUNTRIES);

    for (int i = 0; i < brandCountries.size(); ++i) {
        BrandCountry brandCountry = brandCountries.at(i);
        query.bindValue(":brand_id", brandCountry.brandId);
        query.bindValue(":country", brandCountry.country);
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

void DbManager::dropTables()
{
    qDebug() << "Deleting tables and setting user version to initial value";
    QSqlQuery query;
    query.exec("drop table if exists pubs");
    query.exec("drop table if exists brands");
    query.exec("drop table if exists tags");
    query.exec("drop table if exists pubs_brands");
    query.exec("drop table if exists countries");
    query.exec("drop table if exists brands_countries");
    query.exec("drop table if exists brands_tags");
    query.exec("drop table if exists quotes");
    query.exec("PRAGMA user_version=1;");
    query.clear();
}

DbManager::~DbManager()
{
        if(db.isOpen()) {
            db.close();
        }
}
