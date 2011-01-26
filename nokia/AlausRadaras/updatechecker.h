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
private slots:
        void replyFinished(QNetworkReply* reply);
signals:
    void updateAvalable(QString text);
private:
    QString getUniqueDeviceId();

};

#endif // UPDATECHECKER_H
