#include "dbpopulatorrunner.h"

DbPopulatorRunner::DbPopulatorRunner(QObject *parent, DbManager *manager) :
    QThread(parent),
    db(manager),
    dbLoader(parent,db)
{
}

void DbPopulatorRunner::run()
{
    dbLoader.populateIfNotLatest();
}
