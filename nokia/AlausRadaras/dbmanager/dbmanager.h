#ifndef DBMANAGER_H
#define DBMANAGER_H
#include <QObject>
#include <QSqlDatabase>
#include <QtCore>
#include <QSqlQuery>
#include <QVector>
#include <beer.h>
#include <pub.h>
#include <quote.h>
#include <beertag.h>
#include <pubbeer.h>
#include <brand.h>
#include <brandbeer.h>

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
    void setLatest();
    void populateBrands(const QVector<Brand> &brands);
    void populateBeers(const QVector<Beer> &beers);
    void populatePubs(const QVector<Pub> &pubs);
    void populateQuotes(const QVector<Quote> &quotes);
    void populateBeerTags(const QVector<BeerTag> &beerTags);
    void populatePubBeers(const QVector<PubBeer> &pubBeers);

    void deletePubBeers(const QString pubId);
    void deleteBeerTags(const QString beerId);

    void deletePub(const QString pubId);
    void deleteBrand(const QString brandId);
    void deleteBeer(const QString beer);

    //anti-pattern
    static QString SELECT_RANDOM_PUB;
    static QString SELECT_RANDOM_BEER_BY_PUB;
    static QString SELECT_BEERPUB_PUBS;
    static QString SELECT_BEERPUB_PUBS_BY_BEER;
    static QString SELECT_BEERPUB_PUBS_BY_TAG;
    static QString SELECT_BEERPUB_PUB;
    static QString SELECT_BEERPUB_PUBS_BY_COUNTRY;
    static QString SELECT_RANDOM_QUOTE;

public slots:

private:
    static const uint DB_VERSION = 12;

    static QString QUERY_INSERT_BEERS;
    static QString QUERY_INSERT_TAGS;
    static QString QUERY_INSERT_PUBS;
    static QString QUERY_INSERT_BRANDS;

    static QString QUERY_INSERT_QUOTES;
    static QString QUERY_INSERT_BEER_TAGS;
    static QString QUERY_INSERT_PUB_BEERS;

    void dropTables();
    Location getLocation(qreal latitude, qreal longitude);
};

#endif // DBMANAGER_H
