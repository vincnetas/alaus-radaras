#ifndef DBUPDATER_H
#define DBUPDATER_H

#include <QObject>
#include <QVariant>
#include "pubbeer.h"
#include "pub.h"
#include "beertag.h"
#include "beer.h"
#include "brand.h"

class DbUpdater : public QObject
{
    Q_OBJECT
public:
    explicit DbUpdater(QObject *parent = 0);
    bool updateDb(const QVariantMap &data);
private:
    const QVector<PubBeer> popuplatePubBeers(const QString pubId, const QVariantList &beerIds);
    const QVector<BeerTag> popuplateBeerTags(const QString beerId, const QVariantList &tags);
    const Pub populatePub(const QVariantMap &pubData);
    const Beer populateBeer(const QVariantMap &beerData);
    const Brand populateBrand(const QVariantMap &brandData);
signals:

public slots:

};

#endif // DBUPDATER_H
