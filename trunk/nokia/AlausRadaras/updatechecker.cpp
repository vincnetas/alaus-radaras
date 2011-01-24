#include "updatechecker.h"
#include <QNetworkAccessManager>
#include <QUuid>
#include <QSettings>

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
    manager->get(QNetworkRequest(QUrl(url)));
    manager->deleteLater();
}

void UpdateChecker::replyFinished(QNetworkReply* reply)
{
    QUrl url = reply->url();
    if(!reply->error()) {

        QString version(reply->readAll());
        if(version != VERSION) {
            emit updateAvalable(version);
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
