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
    const QVector<BeerPub> getAllPubs();
    const QVector<BeerPub> getPubsByBeerId(QString beerId);
    const QVector<BeerPub> getPubsByCountry(QString countryCode);
    const QVector<BeerPub> getPubsByTag(QString tagCode);
    const BeerPub getPub(QString pubId);
    FeelingLuckyInfo feelingLucky();
    QString getQuote(int count);
    signals:

public slots:
private:
    const QVector<BeerPub> generatePubsFromQuery(QSqlQuery query);

};

#endif // DATAPROVIDER_H
