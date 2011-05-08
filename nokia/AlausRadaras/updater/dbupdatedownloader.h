#ifndef DBUPDATEDOWNLOADER_H
#define DBUPDATEDOWNLOADER_H

#include <QObject>
#include "baseupdatedownloader.h"

class DbUpdateDownloader : public BaseUpdateDownloader
{
    Q_OBJECT
public:
    explicit DbUpdateDownloader(QObject *parent = 0);
protected:
    QString getUrl();
    QString getUpdateType();
private slots:
    void saveUpdate(const QString &text);
signals:
    void DbUpdateFinished();
};

#endif // DBUPDATEDOWNLOADER_H
