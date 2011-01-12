#include "beermap.h"
#include "ui_beermap.h"
#include <QUrl>
#include <QSqlQuery>
#include <QDebug>
#include <QSqlError>
#include "pubview.h"

BeerMap::BeerMap(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::BeerMap)
{


    //http://wiki.forum.nokia.com/index.php/Getting_the_location_in_Qt
}


QString BeerMap::getMarkerData()
{
    QString sqlQuery = NULL;
    switch(this->type) {
        case ALL:
        sqlQuery = QString("SELECT id, title, longtitude, latitude from pubs");
        break;
    }
    QSqlQuery query(sqlQuery);

   return NULL;

}

void BeerMap::showPub(QString pubId)
{
    QString newPub = pubId;
    PubView *view = new PubView(this,newPub);
    view->setWindowFlags( view->windowFlags() ^ Qt::WindowSoftkeysVisibleHint );
    view->showFullScreen();
}

void BeerMap::showBeerMap(BeerMapType type, QString id)
{
    this->id = id;
    this->type = type;
}

BeerMap::~BeerMap()
{
    delete ui;
}
