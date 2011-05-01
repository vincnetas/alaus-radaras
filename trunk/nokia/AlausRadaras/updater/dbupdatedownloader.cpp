#include "dbupdatedownloader.h"
#include "QDateTime"
#include "qfile.h"
#include "QTextStream"
#include "json.h"
#include "qdebug.h"
#include "dbupdater.h"

DbUpdateDownloader::DbUpdateDownloader(QObject *parent) :
    BaseUpdateDownloader(parent)
{
    connect(this,SIGNAL(updateCheckFinished(QString)),SLOT(saveUpdate(QString)));
}

QString DbUpdateDownloader::getUpdateType()
{
    return "DbLastUpdateCheck";
}

QString DbUpdateDownloader::getUrl()
{
   int date = settings.value(getUpdateType(),QDateTime::fromString("2011-04-07","yyyy-MM-dd").toTime_t()).toInt();
   return QString("http://www.alausradaras.lt/json?latestUpdate=%1").arg(QDateTime::fromTime_t(date).toString("yyyy-MM-dd"));
}

void DbUpdateDownloader::saveUpdate(const QString &text)
{
    bool ok;
    QVariantMap result = Json::parse(text, ok).toMap();

    if(ok) {
        DbUpdater updater;
        ok = updater.updateDb(result);
        if(ok) {
            //TODO: set last update;
        }
    }
}
