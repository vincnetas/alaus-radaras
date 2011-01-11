#include "beermap.h"
#include "ui_beermap.h"
#include <QUrl>
#include <QWebFrame>
#include <QSqlQuery>
#include <QDebug>
#include <QSqlError>
#include "pubview.h"

BeerMap::BeerMap(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::BeerMap)
{

    ui->setupUi(this);
    QWebView::connect(ui->webView, SIGNAL(loadFinished(bool)), this, SLOT(pageLoad_finished(bool)));
    attachJavascript();
    connect( ui->webView->page()->mainFrame(), SIGNAL(javaScriptWindowObjectCleared()), this, SLOT(attachJavascript()) );


    //http://wiki.forum.nokia.com/index.php/Getting_the_location_in_Qt
}


void BeerMap::attachJavascript()
{
    ui->webView->page()->mainFrame()->addToJavaScriptWindowObject( QString("BeerMap"), this );
}
void BeerMap::pageLoad_finished(bool ok)
{
    //ui->webView->page()->mainFrame()->evaluateJavaScript("createMarker(54.6896, 25.2799);");
}

QString BeerMap::getMarkerData()
{
    QString  pubFormat("{\"id\" : \"%1\", \"lat\": \"%2\", \"lng\": \"%3\", \"name\": \"%4\"}");
    QString sqlQuery = NULL;
    switch(this->type) {
        case ALL:
        sqlQuery = QString("SELECT id, title, longtitude, latitude from pubs");
        break;
    }
    QString output = QString("[");
    QSqlQuery query(sqlQuery);

    if(query.lastError().isValid())
        qDebug() << query.lastError();

           while (query.next()) {
                output.append(pubFormat.arg(query.value(0).toString(),
                                            query.value(3).toString(),
                                             query.value(2).toString(),
                                              query.value(1).toString()));
                output.append(",");
           }
    output.chop(1);
    output.append("]");

    return output;

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
    this->ui->webView->load(QUrl("qrc:/web/index.html"));
}

BeerMap::~BeerMap()
{
    delete ui;
}
