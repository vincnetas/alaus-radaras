#ifndef UPDATECHECKER_H
#define UPDATECHECKER_H

#include <QObject>
#include <QNetworkReply>

class UpdateChecker : public QObject
{
    Q_OBJECT
public:
    explicit UpdateChecker(QObject *parent = 0);
    static const QString VERSION;
signals:

public slots:
    void checkForUpdates();

signals:
    void updateAvalable(QString version);
private:
    void replyFinished(QNetworkReply* reply);
    QString getUniqueDeviceId();

};

#endif // UPDATECHECKER_H
