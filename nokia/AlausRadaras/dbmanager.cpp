#include "dbmanager.h"
#include <QSqlQuery>
#include <QVariant>
#include <QFile>
#include <QTextStream>
#include <QStringList>
#include <QtCore/QDebug>
#include <QTextCodec>

DbManager::DbManager()
{
    db = NULL;
}

bool DbManager::init()
{

    QSqlDatabase db = QSqlDatabase::addDatabase("QSQLITE");
       db.setDatabaseName("alaus.radaras.db");
       if (!db.open()) {
           return false;
       }

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
    return false;
}

bool DbManager::createDb()
{
    QSqlQuery query;
    query.exec("CREATE TABLE pubs("
                   "id 		TEXT PRIMARY KEY, "
                   "title 		TEXT NOT NULL, "
                   "longtitude REAL NOT NULL, "
                   "latitude 	REAL NOT NULL, "
                   "address 	TEXT, "
                   "notes	 	TEXT, "
                   "phone	 	TEXT, "
                   "url	 	TEXT);");

    query.exec("CREATE TABLE brands("
               "id 			TEXT PRIMARY KEY, "
               "title 			TEXT NOT NULL, "
               "icon			TEXT, "
               "description 	TEXT);");

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
    query.exec("CREATE TABLE qoutes("
                "amount			INTEGER NOT NULL,"
                "text			TEXT NOT NULL);");

    insertBrands(query);

//    insertPubs(query);
//    insertTags(query);
//    insertCountries(query);
//    insertQoutes(query);
//    insertAssociations(query);
    return false;

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

     }
     file.close();

     qDebug() << "end inserting brands";

}

DbManager::~DbManager()
{
        qDebug() << "deletting tables";
    QSqlQuery query;
    query.exec("drop table pubs");
    query.exec("drop table brands");
    query.exec("drop table pubs_brands");
    query.exec("drop table countries");
    query.exec("drop table brands_countries");
    query.exec("drop table brands_tags");
    query.exec("drop table qoutes");

    if(db != NULL) {
        if(db->isOpen()) {
            db->close();
        }
    }
    delete db;
}
