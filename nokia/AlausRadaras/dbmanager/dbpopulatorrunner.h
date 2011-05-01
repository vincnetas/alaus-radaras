#ifndef DBPOPULATOR_H
#define DBPOPULATOR_H

#include <QObject>
#include "dbmanager.h"
#include <dbmanager/txtdbloader.h>
class DbPopulatorRunner : public QThread
{
    Q_OBJECT
public:
    explicit DbPopulatorRunner(QObject *parent = 0, DbManager *manager=0);
    void run();
signals:

public slots:

private:
    DbManager *db;
    TxtDbLoader dbLoader;

};

#endif // DBPOPULATOR_H
