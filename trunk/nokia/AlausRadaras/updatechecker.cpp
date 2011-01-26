#include "updatechecker.h"
#include <QNetworkAccessManager>
#include <QUuid>
#include <QSettings>
#include <QDebug>

const QString UpdateChecker::VERSION = QString("1.0");

UpdateChecker::UpdateChecker(QObject *parent) :
    QObject(parent)
{


}

void UpdateChecker::checkForUpdates()
{
    QNetworkAccessManager *manager = new QNetworkAccessManager(this);
    connect(manager, SIGNAL(finished(QNetworkReply*)),
            this, SLOT(replyFinished(QNetworkReply*)));

    QString url = QString("http://alausradaras.lt/nokia/update.php?ver=%1&id=%2").arg(VERSION,getUniqueDeviceId());
    qDebug() << url;
    manager->get(QNetworkRequest(QUrl(url)));
    qDebug() << "check for updates";
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
