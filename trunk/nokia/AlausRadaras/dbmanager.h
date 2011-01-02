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
    static const uint DB_VERSION = 1;
    bool isDbLatest();
    bool createDb();
    void insertBrands(QSqlQuery &query);

};

#endif // DBMANAGER_H
