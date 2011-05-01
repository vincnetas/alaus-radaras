#ifndef DBUPDATER_H
#define DBUPDATER_H

#include <QObject>
#include <QVariant>
#include "pubbrand.h"
#include "pub.h"
#include "brandtag.h"
#include "brand.h"

class DbUpdater : public QObject
{
    Q_OBJECT
public:
    explicit DbUpdater(QObject *parent = 0);
    bool updateDb(const QVariantMap &data);
private:
    const QVector<PubBrand>& popuplatePubBrands(const QString pubId, const QVariantList &brandIds);
    const QVector<BrandTag>& popuplateBrandTags(const QString brandId, const QVariantList &tags);
    const Pub& populatePub(const QVariantMap &pubData);
    const Brand& populateBrand(const QVariantMap &brandData);
signals:

public slots:

};

#endif // DBUPDATER_H
