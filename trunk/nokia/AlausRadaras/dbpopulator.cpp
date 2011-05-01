#include "dbpopulator.h"

DbPopulator::DbPopulator(QObject *parent, DbManager *manager) :
    QThread(parent),
    db(manager),
    dbLoader(parent,db)
{
}

void DbPopulator::run()
{
    dbLoader.populateIfNotLatest();
}
