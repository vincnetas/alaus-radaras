#ifndef DBMANAGER_H
#define DBMANAGER_H
#include <QObject>
#include <QSqlDatabase>
#include <QtCore>
#include <QSqlQuery>
struct Location {
    QPoint tile;
    QPoint tilePixel;
};


class DbManager : public QObject
{
    Q_OBJECT
public:
    QSqlDatabase* db;
    explicit DbManager(QObject *parent = 0);
    ~DbManager();
     bool init();
     bool populateIfNotLatest();
    bool isDbLatest();
public slots:

private:
    static const uint DB_VERSION = 3;

    bool createDb();

    void dropTables();
    void insertBrands(QSqlQuery &query);
    void insertPubs(QSqlQuery &query);
    void insertTags(QSqlQuery &query);
    void insertCountries(QSqlQuery &query);
    void insertQoutes(QSqlQuery &query);
    void insertAssociations(QSqlQuery &query);
    Location getLocation(QString latitude, QString longitude);

};

#endif // DBMANAGER_H
