#ifndef DBPOPULATOR_H
#define DBPOPULATOR_H

#include <QObject>
#include "dbmanager.h"
class DbPopulator : public QThread
{
    Q_OBJECT
public:
    explicit DbPopulator(QObject *parent = 0, DbManager *manager=0);
    void run();
signals:

public slots:

private:
    DbManager *db;

};

#endif // DBPOPULATOR_H
