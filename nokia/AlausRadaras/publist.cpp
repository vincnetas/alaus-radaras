#include "publist.h"
#include "ui_publist.h"
#include <QList>
#include <QSqlQuery>
#include <QDebug>
#include <beerpub.h>
#include <pubview.h>
#include <publistmodel.h>
#include <lightmaps.h>
#include "dataprovider.h"
#include <QGeoCoordinate>
#include <QGeoPositionInfoSource>
#include "viewutils.h"
#include "qtscroller.h"
#include "calculationhelper.h"
#include <QKeyEvent>
#include "enums.h"

bool sortPubsByDistance( const BeerPub &p1 , const BeerPub &p2 )
{
        return p2.distance > p1.distance;
}

PubList::PubList(QWidget *parent) :
    QWidget(parent),
    pubListModel(0),
    ui(new Ui::PubList)
{
    ui->setupUi(this);

    dataProvider = new DataProvider(this);
    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));

    QtScroller::grabGesture(ui->pubListView->viewport(), QtScroller::QtScroller::LeftMouseButtonGesture);
    QListView::connect(ui->pubListView, SIGNAL(activated(QModelIndex)) , this , SLOT(pubList_itemClicked(QModelIndex)));

    QAction *okSoftKeyAction = new QAction(QString("Next"), this);
    okSoftKeyAction->setSoftKeyRole(QAction::NegativeSoftKey);
    connect(okSoftKeyAction, SIGNAL(triggered()), this, SLOT(on_btnBack_clicked()));
    addAction(okSoftKeyAction);

    if(ViewUtils::HighRes)
        ui->btnMap->setIconSize(QSize(36, 36));
    else
        ui->btnMap->setIconSize(QSize(24, 24));

}


void PubList::locationChanged(qreal lat, qreal lon)
{
    if(pubListModel) {
        if(pubs.size() > 0) {
            for(int i = 0; i < pubs.size(); i++) {
                qreal p1 = pubs[i].latitude;
                qreal p2 = pubs[i].longitude;
                qreal distance = CalculationHelper::getDistance(p1,p2,lat,lon,'M');
                //qDebug() << QString::number(distance);
                pubs[i].distance = distance;
            }
            qSort(pubs.begin(),pubs.end(),sortPubsByDistance);
            pubListModel->refresh();
        }
    }
}

void PubList::showPubList(PubListType type, QString id, QString header)
{
    if(pubListModel) {
        delete pubListModel;
    }

    switch(type)
    {
        case ALL:
            pubs = dataProvider->getAllPubs();
        break;
        case BEER:
            pubs = dataProvider->getPubsByBeerId(id);
        break;
        case COUNTRY:
            pubs = dataProvider->getPubsByCountry(id);
        break;
        case TAG:
            pubs = dataProvider->getPubsByTag(id);
        break;
    }

    pubListModel = new PubListModel(this, pubs);
    ui->pubListView->setModel(pubListModel);

    setHeader(header);
}

void PubList::on_btnMap_clicked()
{
    emit MapSelected(this->pubs);
}

void PubList::setHeader(QString text)
{
    this->ui->txtHeader->setText(text);
}

void PubList::on_btnBack_clicked()
{
    emit Back();
}

void PubList::pubList_itemClicked(const QModelIndex &current)
{

    emit PubSelected(current.data(Qt::EditRole).toString());

}

void PubList::keyPressEvent(QKeyEvent* event)
{
    if(event->nativeVirtualKey() == CancelKey) {
        on_btnBack_clicked();
    } else if (event->nativeVirtualKey() == OkKey) {
        on_btnMap_clicked();
    }
    QWidget::keyPressEvent(event);
}

void PubList::changeEvent(QEvent* event)
{
    if (event->type() == QEvent::LanguageChange)
    {
        ui->retranslateUi(this);
    }
    QWidget::changeEvent(event);
}

PubList::~PubList()
{
    delete dataProvider;
    delete ui;
}
