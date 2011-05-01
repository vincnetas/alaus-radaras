#ifndef DBUPDATER_H
#define DBUPDATER_H

#include <QObject>
#include <QVariant>

class DbUpdater : public QObject
{
    Q_OBJECT
public:
    explicit DbUpdater(QObject *parent = 0);
    bool updateDb(const QVariantMap &data);
signals:

public slots:

};

#endif // DBUPDATER_H
