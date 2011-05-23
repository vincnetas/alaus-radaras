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
    int date = settings.value(getUpdateType(),QDateTime::fromString("2011-05-21","yyyy-MM-dd").toUTC().toTime_t()).toInt();
    //int date = QDateTime::fromString("2011-04-07T00:00:00","yyyy-MM-ddThh:mm:ss").toUTC().toTime_t();
    return QString("http://www.alausradaras.lt/json?lastUpdate=%1").arg(QDateTime::fromTime_t(date).toUTC().toString("yyyy-MM-ddThh:mm:ss"));
}

void DbUpdateDownloader::saveUpdate(const QString &text)
{
    bool ok;
    QVariantMap result = Json::parse(text, ok).toMap();

    if(ok) {
        DbUpdater updater;
        ok = updater.updateDb(result);
        if(ok) {
           settings.setValue(getUpdateType(),QDateTime::currentDateTime().toTime_t());
        }
    }
    qDebug() << "emiting DbUpdateFinished";
    emit DbUpdateFinished();
}
