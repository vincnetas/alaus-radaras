#ifndef DBMANAGER_H
#define DBMANAGER_H
#include <QSqlDatabase>

class DbManager
{
public:
    QSqlDatabase* db;
    explicit DbManager();
    bool init();
    ~DbManager();
private:
    enum DbInserts { PUBS, BRANDS, TAGS, COUNTRIES, QOUTES, ASSOCIATIONS };
    static const uint DB_VERSION = 2;
    bool isDbLatest();
    bool createDb();

    void dropTables();
    void insertBrands(QSqlQuery &query);
    void insertPubs(QSqlQuery &query);
    void insertTags(QSqlQuery &query);
    void insertCountries(QSqlQuery &query);
    void insertQoutes(QSqlQuery &query);
    void insertAssociations(QSqlQuery &query);

};

#endif // DBMANAGER_H
