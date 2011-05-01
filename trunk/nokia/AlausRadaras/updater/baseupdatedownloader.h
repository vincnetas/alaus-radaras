#ifndef UPDATECHECKER_H
#define UPDATECHECKER_H

#include <QObject>
#include <QNetworkReply>
#include "qsettings.h"

class BaseUpdateDownloader : public QObject
{
    Q_OBJECT
public:
    explicit BaseUpdateDownloader(QObject *parent = 0);
    static const QString VERSION;
protected:
    virtual QString getUrl();
    virtual bool needToCheckForUpdates();
    virtual QString getUpdateType();
    virtual QString getUniqueDeviceId();
    QSettings settings;
public slots:
    virtual void checkForUpdates();
protected slots:
    virtual void replyFinished(QNetworkReply* reply);
signals:
    void updateCheckFinished(const QString &text);

};

#endif // UPDATECHECKER_H
