#include "beermap.h"
#include "ui_beermap.h"
#include <QUrl>

BeerMap::BeerMap(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::BeerMap)
{
    ui->setupUi(this);
    ui->webView->load(QUrl("qrc:/web/index.html"));
    //http://wiki.forum.nokia.com/index.php/Getting_the_location_in_Qt
}

BeerMap::~BeerMap()
{
    delete ui;
}
