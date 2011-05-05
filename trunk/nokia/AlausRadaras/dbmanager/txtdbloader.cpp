#include "txtdbloader.h"
#include <QFile>
#include <QTextStream>
#include <QVector>
#include <beer.h>
#include <brand.h>
#include <pub.h>
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
        insertBeers();
        insertPubs();
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
       brand.title = columns.at(1);
       brand.country = columns.at(2);
       brands.append(brand);
    }
    file.close();
    dbManager->populateBrands(brands);
}

void TxtDbLoader::insertBeers()
{
    QVector<Beer> beers;
    QFile file(":/db/beers.txt");
    file.open(QFile::ReadOnly | QFile::Text);
    QTextStream in(&file);
    in.setCodec(QTextCodec::codecForName("UTF-8"));
    QString line = NULL;
    while (!in.atEnd()) {
       line = in.readLine();

       QStringList columns = line.split("\t");
       Beer beer;
       beer.id = columns.at(0);
       beer.icon = columns.at(1);
       beer.title = columns.at(2);
       beer.brandId = columns.at(5);
       beers.append(beer);
    }
    file.close();
    dbManager->populateBeers(beers);
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
     QVector<BeerTag> beerTags;
     QVector<PubBeer> pubBeers;

     QFile file(":/db/beers.txt");
     file.open(QFile::ReadOnly | QFile::Text);
     QTextStream in(&file);
     in.setCodec(QTextCodec::codecForName("UTF-8"));
     QString line = NULL;
     while (!in.atEnd()) {
        line = in.readLine();

        QStringList columns = line.split("\t");

        QStringList pubs = columns.at(3).split(",");
        for (int i = 0; i < pubs.length(); i++) {
            PubBeer pubBeer;
            pubBeer.beerId = columns.at(0);
            pubBeer.pubId = pubs.at(i).trimmed();
            pubBeers.append(pubBeer);
        }

        QStringList tags = columns.at(4).split(",");
        for (int i = 0; i < tags.length(); i++) {
            BeerTag beerTag;
            beerTag.beerId = columns.at(0);
            beerTag.tag = tags.at(i).trimmed();
            beerTags.append(beerTag);
        }
     }
     file.close();
     dbManager->populateBeerTags(beerTags);
     dbManager->populatePubBeers(pubBeers);
}
