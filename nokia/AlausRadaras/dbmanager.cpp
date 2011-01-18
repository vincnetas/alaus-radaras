#include "dbmanager.h"
#include <QSqlQuery>
#include <QVariant>
#include <QFile>
#include <QTextStream>
#include <QStringList>
#include <QtCore/QDebug>
#include <QTextCodec>
#include <QDebug>
#include <QSqlError>
#include "calculationhelper.h"


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

bool DbManager::populateIfNotLatest()
{

    if(!isDbLatest())
    {
        qDebug() << "Db is not latest";
        if(!createDb())
        {
            return false;
        }
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
    if(query.lastError().isValid())
        qDebug() << query.lastError();

    query.exec("CREATE TABLE brands("
               "id 			TEXT PRIMARY KEY, "
               "title 			TEXT NOT NULL, "
               "icon			TEXT, "
               "description 	TEXT);");
    if(query.lastError().isValid())
        qDebug() << query.lastError();

    query.exec("CREATE TABLE tags("
               "code			TEXT NOT NULL, "
               "title			TEXT NOT NULL);");
    if(query.lastError().isValid())
        qDebug() << query.lastError();

    query.exec("CREATE TABLE pubs_brands("
               "pub_id			TEXT NOT NULL, "
               "brand_id 		TEXT NOT NULL);");
    if(query.lastError().isValid())
        qDebug() << query.lastError();

    query.exec( "CREATE TABLE countries("
                "code			TEXT NOT NULL,"
                "name			TEXT NOT NULL);");
    if(query.lastError().isValid())
        qDebug() << query.lastError();

    query.exec("CREATE TABLE brands_countries("
               "brand_id			TEXT NOT NULL,"
               "country			TEXT NOT NULL);");
    if(query.lastError().isValid())
        qDebug() << query.lastError();

    query.exec("CREATE TABLE brands_tags("
                "brand_id			TEXT NOT NULL,"
                "tag				TEXT NOT NULL);");
    if(query.lastError().isValid())
        qDebug() << query.lastError();
    query.exec("CREATE TABLE qoutes("
                "amount			INTEGER NOT NULL,"
                "text			TEXT NOT NULL);");
    if(query.lastError().isValid())
        qDebug() << query.lastError();

    //cowboy coding. But i hate c++ delegates with pointers
    insertBrands(query);
    insertPubs(query);
    insertTags(query);
    insertCountries(query);
    insertQoutes(query);
    insertAssociations(query);
    query.exec(QString("PRAGMA user_version=%1;").arg(DB_VERSION));
    query.clear();
    return true;

}

void DbManager::insertBrands(QSqlQuery &query)
{

     qDebug() << "begin sinserting brands";
     query.prepare("INSERT INTO brands (id, title, icon) "
                       "VALUES (:id, :title, :icon)");

     QFile file(":/db/brands.txt");
     file.open(QFile::ReadOnly | QFile::Text);
     QTextStream in(&file);
     in.setCodec(QTextCodec::codecForName("UTF-8"));
     QString line = NULL;
     while (!in.atEnd()) {
        line = in.readLine();

        QStringList columns = line.split("\t");
        query.bindValue(":id", columns.at(0));
             query.bindValue(":title", columns.at(1));
             query.bindValue(":icon", columns.at(0));
             query.exec();
             if(query.lastError().isValid())
                 qDebug() << query.lastError();

     }
     file.close();
     query.clear();
     qDebug() << "end inserting brands";

}

void DbManager::insertTags(QSqlQuery &query)
{

     qDebug() << "begin inserting tags";

     query.prepare("INSERT INTO tags "
                       "VALUES (:code, :title)");

     QFile file(":/db/tags.txt");
     file.open(QFile::ReadOnly | QFile::Text);
     QTextStream in(&file);
     in.setCodec(QTextCodec::codecForName("UTF-8"));
     QString line = NULL;
     while (!in.atEnd()) {
        line = in.readLine();

        QStringList columns = line.split("\t");
        query.bindValue(":code", columns.at(0));
        query.bindValue(":title", columns.at(1));
        query.exec();
        if(query.lastError().isValid())
            qDebug() << query.lastError();

     }
     file.close();
     query.clear();
     qDebug() << "end inserting tags";

}

void DbManager::insertPubs(QSqlQuery &query)
{

     qDebug() << "begin inserting pubs";

     query.prepare("INSERT INTO pubs VALUES (:id, :title, :longitude, :latitude,  :address, :notes, :phone, :url, :tile_x, :tile_y,:tile_pixel_x, :tile_pixel_y )");

     QFile file(":/db/pubs.txt");
     file.open(QFile::ReadOnly | QFile::Text);
     QTextStream in(&file);
     in.setCodec(QTextCodec::codecForName("UTF-8"));
     QString line = NULL;
     while (!in.atEnd()) {
        line = in.readLine();


        QStringList columns = line.split("\t");

        qDebug() << "\n" << columns.at(1);
        Location loc = getLocation(columns.at(5),columns.at(6));
        qDebug() << loc.tile.x() << " " << loc.tile.y() << " " << loc.tilePixel.x() << " " << loc.tilePixel.y();

        query.bindValue(":id", columns.at(0));
        query.bindValue(":title", columns.at(1));
        query.bindValue(":address", columns.at(2));
        query.bindValue(":phone", columns.at(3));
        query.bindValue(":url", columns.at(4));
        query.bindValue(":latitude", columns.at(5));
        query.bindValue(":longitude", columns.at(6));
        query.bindValue(":notes","");
        query.bindValue(":tile_x", loc.tile.x());
        query.bindValue(":tile_y", loc.tile.y());
        query.bindValue(":tile_pixel_x", loc.tilePixel.x());
        query.bindValue(":tile_pixel_y",loc.tilePixel.y());
        query.exec();
        if(query.lastError().isValid())
            qDebug() << query.lastError();

     }
     file.close();
     query.clear();
     qDebug() << "end inserting pubs";

}

Location DbManager::getLocation(QString latitude, QString longitude)
{
    Location loc;
    qDebug() << "Lat long" << QString::number(latitude.toDouble()) << " " << QString::number(longitude.toDouble());
    QPointF tileFullPoint = CalculationHelper::tileForCoordinate(latitude.toDouble(), longitude.toDouble());
    qDebug() << "FullPoint: " << QString::number(tileFullPoint.x()) << " " << QString::number(tileFullPoint.y());
    loc.tile = QPoint((int)tileFullPoint.x(),(int)tileFullPoint.y());
    loc.tilePixel = CalculationHelper::tilePixelForTile(tileFullPoint);
    return loc;
}

void DbManager::insertCountries(QSqlQuery &query)
{

     qDebug() << "begin inserting countries";

     query.prepare("INSERT INTO countries VALUES (:code, :name)");

     QFile file(":/db/countries.txt");
     file.open(QFile::ReadOnly | QFile::Text);
     QTextStream in(&file);
     in.setCodec(QTextCodec::codecForName("UTF-8"));
     QString line = NULL;
     while (!in.atEnd()) {
        line = in.readLine();

        QStringList columns = line.split("\t");
        query.bindValue(":code", columns.at(0));
        query.bindValue(":name", columns.at(1));
        query.exec();
        if(query.lastError().isValid())
            qDebug() << query.lastError();

     }
     file.close();
     query.clear();
     qDebug() << "end inserting countries";

}

void DbManager::insertQoutes(QSqlQuery &query)
{

     qDebug() << "begin inserting qoutes";

     query.prepare("INSERT INTO qoutes VALUES (:amount, :text)");

     QFile file(":/db/qoutes.txt");
     file.open(QFile::ReadOnly | QFile::Text);
     QTextStream in(&file);
     in.setCodec(QTextCodec::codecForName("UTF-8"));
     QString line = NULL;
     while (!in.atEnd()) {
        line = in.readLine();

        QStringList columns = line.split("\t");
        query.bindValue(":amount", columns.at(0));
        query.bindValue(":text", columns.at(1));
        query.exec();
        if(query.lastError().isValid())
            qDebug() << query.lastError();

     }
     file.close();
     query.clear();
     qDebug() << "end inserting qoutes";

}

void DbManager::insertAssociations(QSqlQuery &query)
{

     qDebug() << "begin inserting associations";


     QFile file(":/db/brands.txt");
     file.open(QFile::ReadOnly | QFile::Text);
     QTextStream in(&file);
     in.setCodec(QTextCodec::codecForName("UTF-8"));
     QString line = NULL;
     while (!in.atEnd()) {
        line = in.readLine();

        QStringList columns = line.split("\t");

        qDebug() << "Inserting brand<->pub association: ";
        QStringList pubs = columns.at(2).split(",");
        query.prepare("INSERT INTO pubs_brands VALUES (:pub_id,:brand_id)");
        for (int i = 0; i < pubs.length(); i++) {
            query.bindValue(":brand_id", columns.at(0));
            query.bindValue(":pub_id", pubs.at(i).trimmed());
            query.exec();
            if(query.lastError().isValid())
                qDebug() << query.lastError();
        }

        qDebug() << "Inserting brand<->country association: ";
        QStringList countries = columns.at(3).split(",");
        query.prepare("INSERT INTO brands_countries VALUES (:brand_id, :country)");
        for (int i = 0; i < countries.length(); i++) {
            query.bindValue(":brand_id", columns.at(0));
            query.bindValue(":country", countries.at(i).trimmed());
            query.exec();
            if(query.lastError().isValid())
                qDebug() << query.lastError();
        }

        qDebug() << "Inserting brand<->tag association: ";
        QStringList tags = columns.at(4).split(",");
        query.prepare("INSERT INTO brands_tags VALUES (:brand_id, :country)");
        for (int i = 0; i < tags.length(); i++) {
            query.bindValue(":brand_id", columns.at(0));
            query.bindValue(":tag", tags.at(i).trimmed());
            query.exec();
            if(query.lastError().isValid())
                qDebug() << query.lastError();
        }
     }
     file.close();
     query.clear();
     qDebug() << "end inserting associations";

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
       query.exec("drop table if exists qoutes");
       query.exec("PRAGMA user_version=1;");
       query.clear();

}

DbManager::~DbManager()
{
        if(db.isOpen()) {
            db.close();
        }
}
