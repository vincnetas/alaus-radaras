#ifndef DBMANAGER_H
#define DBMANAGER_H
#include <QObject>
#include <QSqlDatabase>
#include <QtCore>
#include <QSqlQuery>
#include <QVector>
#include <brand.h>
#include <tag.h>
#include <pub.h>
#include <country.h>
#include <quote.h>

struct Location {
    QPoint tile;
    QPoint tilePixel;
};


class DbManager : public QObject
{
    Q_OBJECT
public:
    QSqlDatabase db;
    explicit DbManager(QObject *parent = 0);
    ~DbManager();
     bool init();
    bool createDb();
    bool isDbLatest();
    void populateBrands(const QVector<Brand> &brands);
    void populateTags(const QVector<Tag> &tags);
    void populatePubs(const QVector<Pub> &pubs);
    void populateCountries(const QVector<Country> &countries);
    void populateQuotes(const QVector<Quote> &quotes);
public slots:

private:
    static const uint DB_VERSION = 10;

    static QString QUERY_INSERT_BRANDS;
    static QString QUERY_INSERT_TAGS;
    static QString QUERY_INSERT_PUBS;
    static QString QUERY_INSERT_COUNTRIES;
    static QString QUERY_INSERT_QUOTES;

    void dropTables();
    void insertAssociations(QSqlQuery &query);
    Location getLocation(qreal latitude, qreal longitude);

};

#endif // DBMANAGER_H
