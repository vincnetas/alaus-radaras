#include "txtdbloader.h"
#include <QFile>
#include <QTextStream>
#include <QVector>
#include <brand.h>
#include <tag.h>
#include <pub.h>
#include <country.h>
#include <qdebug.h>

TxtDbLoader::TxtDbLoader(QObject *parent, DbManager *dbManager) :
    QObject(parent)
{
    this->dbManager = dbManager;
}


bool TxtDbLoader::populateIfNotLatest()
{

    if(!dbManager->isDbLatest())
    {
        qDebug() << "Db is not latest";
        if(!dbManager->createDb())
        {
            return false;
        }

        //cowboy coding. But i hate c++ delegates with pointers
        insertBrands();
        insertPubs();
        insertTags();
        insertCountries();
        insertQuotes();
        insertAssociations();
        dbManager->setLatest();
    }
    return true;
}

void TxtDbLoader::insertBrands()
{
    QVector<Brand> brands;
    QFile file(":/db/brands.txt");
    file.open(QFile::ReadOnly | QFile::Text);
    QTextStream in(&file);
    in.setCodec(QTextCodec::codecForName("UTF-8"));
    QString line = NULL;
    while (!in.atEnd()) {
       line = in.readLine();

       QStringList columns = line.split("\t");
       Brand brand;
       brand.id = columns.at(0);
       brand.icon = columns.at(0);
       brand.title = columns.at(1);
       brands.append(brand);
    }
    file.close();
    dbManager->populateBrands(brands);
}

void TxtDbLoader::insertTags()
{
    QVector<Tag> tags;
    QFile file(":/db/tags.txt");
    file.open(QFile::ReadOnly | QFile::Text);
    QTextStream in(&file);
    in.setCodec(QTextCodec::codecForName("UTF-8"));
    QString line = NULL;
    while (!in.atEnd()) {
       line = in.readLine();

       QStringList columns = line.split("\t");
       Tag tag;
       tag.code = columns.at(0);
       tag.title = columns.at(1);
       tags.append(tag);
    }
    file.close();
    dbManager->populateTags(tags);
}

void TxtDbLoader::insertPubs()
{
    QVector<Pub> pubs;
    QFile file(":/db/pubs.txt");
    file.open(QFile::ReadOnly | QFile::Text);
    QTextStream in(&file);
    in.setCodec(QTextCodec::codecForName("UTF-8"));
    QString line = NULL;
    while (!in.atEnd()) {
       line = in.readLine();
       QStringList columns = line.split("\t");
       Pub pub;
       pub.id = columns.at(0);
       pub.title = columns.at(1);
       pub.address = columns.at(2);
       pub.city = columns.at(3);
       pub.phone = columns.at(4);
       pub.url = columns.at(5);
       pub.latitude = columns.at(6).toDouble();
       pub.longtitude = columns.at(7).toDouble();
       pub.notes = "";
       pubs.append(pub);
    }
    file.close();
    dbManager->populatePubs(pubs);
}

void TxtDbLoader::insertCountries()
{
    QVector<Country> countries;
    QFile file(":/db/countries.txt");
    file.open(QFile::ReadOnly | QFile::Text);
    QTextStream in(&file);
    in.setCodec(QTextCodec::codecForName("UTF-8"));
    QString line = NULL;
    while (!in.atEnd()) {
       line = in.readLine();

       QStringList columns = line.split("\t");
       Country country;
       country.code = columns.at(0);
       country.name = columns.at(1);
       countries.append(country);
    }
    file.close();
    dbManager->populateCountries(countries);
}

void TxtDbLoader::insertQuotes()
{
    QVector<Quote> quotes;
    QFile file(":/db/quotes.txt");
    file.open(QFile::ReadOnly | QFile::Text);
    QTextStream in(&file);
    in.setCodec(QTextCodec::codecForName("UTF-8"));
    QString line = NULL;
    while (!in.atEnd()) {
       line = in.readLine();

       QStringList columns = line.split("\t");
       Quote quote;
       quote.amount = columns.at(0).toInt();
       quote.text = columns.at(1);
       quotes.append(quote);
    }
    file.close();
    dbManager->populateQuotes(quotes);
}

void TxtDbLoader::insertAssociations()
{
     QVector<BrandCountry> brandCountries;
     QVector<BrandTag> brandTags;
     QVector<PubBrand> pubBrands;

     QFile file(":/db/brands.txt");
     file.open(QFile::ReadOnly | QFile::Text);
     QTextStream in(&file);
     in.setCodec(QTextCodec::codecForName("UTF-8"));
     QString line = NULL;
     while (!in.atEnd()) {
        line = in.readLine();

        QStringList columns = line.split("\t");

        QStringList pubs = columns.at(2).split(",");
        for (int i = 0; i < pubs.length(); i++) {
            PubBrand pubBrand;
            pubBrand.brandId = columns.at(0);
            pubBrand.pubId = pubs.at(i).trimmed();
            pubBrands.append(pubBrand);
        }

        QStringList countries = columns.at(3).split(",");
        for (int i = 0; i < countries.length(); i++) {
            BrandCountry brandCountry;
            brandCountry.brandId = columns.at(0);
            brandCountry.country = countries.at(i).trimmed();
            brandCountries.append(brandCountry);
        }

        QStringList tags = columns.at(4).split(",");
        for (int i = 0; i < tags.length(); i++) {
            BrandTag brandTag;
            brandTag.brandId = columns.at(0);
            brandTag.tag = tags.at(i).trimmed();
            brandTags.append(brandTag);
        }
     }
     file.close();
     dbManager->populateBrandCountries(brandCountries);
     dbManager->populateBrandTags(brandTags);
     dbManager->populatePubBrands(pubBrands);
}
