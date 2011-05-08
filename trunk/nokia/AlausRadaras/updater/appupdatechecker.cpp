#include "appupdatechecker.h"
#include "qdatetime.h"

AppUpdateChecker::AppUpdateChecker(QObject *parent) :
    BaseUpdateDownloader(parent)
{
    connect(this,SIGNAL(updateCheckFinished(QString)),this,SLOT(setLastUpdate(QString)));
}

QString AppUpdateChecker::getUpdateType()
{
    return "LastUpdateCheck";
}

QString AppUpdateChecker::getUrl()
{
   return QString("http://alausradaras.lt/nokia/update.php?ver=%1&id=%2").arg(VERSION,getUniqueDeviceId());
}

void AppUpdateChecker::setLastUpdate(const QString &text)
{
   settings.setValue(getUpdateType(),QDateTime::currentDateTimeUtc().toTime_t());
}

