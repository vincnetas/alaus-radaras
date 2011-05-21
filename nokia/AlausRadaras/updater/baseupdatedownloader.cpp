#include "baseupdatedownloader.h"
#include <QNetworkAccessManager>
#include <QUuid>
#include <QSettings>
#include <QDebug>
#include <QDateTime>

const QString BaseUpdateDownloader::VERSION = QString("1.1");

BaseUpdateDownloader::BaseUpdateDownloader(QObject *parent) :
    QObject(parent)
{


}


void BaseUpdateDownloader::checkForUpdates()
{
    if(needToCheckForUpdates()) {
        qDebug() << "checking for updates";
        QNetworkAccessManager *manager = new QNetworkAccessManager(this);
        connect(manager, SIGNAL(finished(QNetworkReply*)),
                this, SLOT(replyFinished(QNetworkReply*)));
        QString url = getUrl();
          qDebug() << url;
        manager->get(QNetworkRequest(QUrl(url)));
    } else {
         emit updateCheckFinished("");
    }
}

bool BaseUpdateDownloader::needToCheckForUpdates()
{
    if(settings.value("UpdatesEnabled",true).toBool() && settings.value("InternetEnabled",true).toBool()) {
        if(settings.value("UpdateFrequency",0).toInt() == 0) {
            return true;
        } else {
            int date = settings.value(getUpdateType(),0).toInt();
            QDateTime dateTime = QDateTime::fromTime_t(date).toUTC().addDays(7);
            if(dateTime < QDateTime::currentDateTime()) {
                return true;
            }
        }
    }
    return false;
}

void BaseUpdateDownloader::replyFinished(QNetworkReply* reply)
{   qDebug() << "replyFinished";
    QString replyText = "";
    if(!reply->error()) {
        qDebug() << "no error";
        replyText = reply->readAll();
    }
    emit updateCheckFinished(replyText);
    reply->deleteLater();
}

QString BaseUpdateDownloader::getUpdateType()
{
    return "";
}

QString BaseUpdateDownloader::getUrl()
{
   return "";
}

QString BaseUpdateDownloader::getUniqueDeviceId()
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
