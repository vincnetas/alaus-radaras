#include "updatechecker.h"
#include <QNetworkAccessManager>
#include <QUuid>
#include <QSettings>
#include <QDebug>
#include <QDateTime>

const QString UpdateChecker::VERSION = QString("1.0");

UpdateChecker::UpdateChecker(QObject *parent) :
    QObject(parent)
{


}

void UpdateChecker::checkForUpdates()
{
    if(needToCheckForUpdates()) {
        QNetworkAccessManager *manager = new QNetworkAccessManager(this);
        connect(manager, SIGNAL(finished(QNetworkReply*)),
                this, SLOT(replyFinished(QNetworkReply*)));

        QString url = QString("http://alausradaras.lt/nokia/update.php?ver=%1&id=%2").arg(VERSION,getUniqueDeviceId());
        manager->get(QNetworkRequest(QUrl(url)));
    }
}

bool UpdateChecker::needToCheckForUpdates()
{
    QSettings settings;
    if(settings.value("UpdatesEnabled",true).toBool() && settings.value("InternetEnabled",true).toBool()) {
        if(settings.value("UpdateFrequency",0).toInt() == 0) {
            return true;
        } else {
            int date = settings.value("LastUpdateCheck",0).toInt();
            QDateTime dateTime = QDateTime::fromTime_t(date).addDays(7);
            if(dateTime < QDateTime::currentDateTime()) {
                return true;
            }
        }
    }
    return false;

}

void UpdateChecker::replyFinished(QNetworkReply* reply)
{
    qDebug() << "reply";
    QUrl url = reply->url();
    if(!reply->error()) {

        QString replyText(reply->readAll());
        if(!replyText.isEmpty()) {
            emit updateAvalable(replyText);
        }
        QSettings settings;
        settings.setValue("LastUpdateCheck",QDateTime::currentDateTime().toTime_t());

    }
    reply->deleteLater();

}
QString UpdateChecker::getUniqueDeviceId()
{
    QSettings settings(this);
    QVariant uniqueId = settings.value("UNIQUE_ID");
    if(uniqueId == QVariant()) {
        QString id = QUuid::createUuid().toString();
        settings.setValue("UNIQUE_ID",id);
        return id;
    } else {
        return uniqueId.toString();
    }

}
