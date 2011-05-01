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
#include <brandtag.h>
#include <pubbrand.h>
#include <brandcountry.h>

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
    void populateTags(const QVector<Tag> &tags);
    void populatePubs(const QVector<Pub> &pubs);
    void populateCountries(const QVector<Country> &countries);
    void populateQuotes(const QVector<Quote> &quotes);
    void populateBrandTags(const QVector<BrandTag> &brandTags);
    void populatePubBrands(const QVector<PubBrand> &pubBrands);
    void populateBrandCountries(const QVector<BrandCountry> &brandCountries);

    void deletePubBrands(const QString pubId);
    void deleteBrandTags(const QString brandId);
public slots:

private:
    static const uint DB_VERSION = 10;

    static QString QUERY_INSERT_BRANDS;
    static QString QUERY_INSERT_TAGS;
    static QString QUERY_INSERT_PUBS;
    static QString QUERY_INSERT_COUNTRIES;
    static QString QUERY_INSERT_QUOTES;
    static QString QUERY_INSERT_BRAND_TAGS;
    static QString QUERY_INSERT_PUB_BRANDS;
    static QString QUERY_INSERT_BRAND_COUNTRIES;

    void dropTables();
    Location getLocation(qreal latitude, qreal longitude);
};

#endif // DBMANAGER_H
