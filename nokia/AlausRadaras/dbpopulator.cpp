#include "dbpopulator.h"

DbPopulator::DbPopulator(QObject *parent, DbManager *manager) :
    QThread(parent),
    db(manager)
{

}


void DbPopulator::run()
{
    db->populateIfNotLatest();
}
