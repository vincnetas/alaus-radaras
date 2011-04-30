#ifndef APPUPDATECHECKER_H
#define APPUPDATECHECKER_H

#include <QObject>
#include "baseupdatedownloader.h"

class AppUpdateChecker : public BaseUpdateDownloader
{
    Q_OBJECT
public:
    explicit AppUpdateChecker(QObject *parent = 0);
protected:
    QString getUrl();
    QString getUpdateType();
protected slots:
     void setLastUpdate(const QString &text);

};

#endif // APPUPDATECHECKER_H
