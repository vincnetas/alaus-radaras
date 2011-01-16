#ifndef DATAPROVIDER_H
#define DATAPROVIDER_H

#include <QObject>
#include "beerpub.h"
#include <QSqlQuery>
#include <QList>
#include "feelingluckyinfo.h"

class DataProvider : public QObject
{
    Q_OBJECT
public:
    explicit DataProvider(QObject *parent = 0);
    QList<BeerPub*> getAllPubs();
    QList<BeerPub*> getPubsByBrandId(QString brandId);
    QList<BeerPub*> getPubsByCountry(QString countryCode);
    QList<BeerPub*> getPubsByTag(QString tagCode);
    FeelingLuckyInfo feelingLucky();
    QString getQoute(int count);
    signals:

public slots:
private:
    QList<BeerPub*> generatePubsFromQuery(QSqlQuery* query);

};

#endif // DATAPROVIDER_H
