#ifndef TXTDBLOADER_H
#define TXTDBLOADER_H

#include <QObject>
#include <dbmanager.h>

class TxtDbLoader : public QObject
{
    Q_OBJECT
public:
    TxtDbLoader(QObject *parent, DbManager *dbManager);
    bool populateIfNotLatest();
signals:
private:
    void insertBrands();
    void insertBeers();
    void insertPubs();
    void insertQuotes();
    void insertAssociations();
    DbManager *dbManager;

public slots:

};

#endif // TXTDBLOADER_H
