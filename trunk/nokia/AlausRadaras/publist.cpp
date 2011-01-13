#include "publist.h"
#include "ui_publist.h"
#include <QList>
#include <QSqlQuery>
#include <QDebug>
#include <beerpub.h>
#include <pubview.h>
#include <publistmodel.h>




PubList::PubList(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::PubList)
{
    ui->setupUi(this);

    QAction* backAction = new QAction(this);
    backAction->setText("Atgal");
    backAction->setSoftKeyRole(QAction::NegativeSoftKey);
    connect(backAction, SIGNAL(triggered()), this, SLOT(close()));
    this->addAction(backAction);
}

PubList::~PubList()
{
    delete ui;
}


QList<BeerPub*> PubList::getPubs()
{
    QString sqlQuery = NULL;
    switch(this->type) {
        case ALL:
        sqlQuery = QString("SELECT id, title, longtitude, latitude from pubs");
        break;
        case BRAND:
        sqlQuery = QString("SELECT id, title, address, notes, phone, url, latitude, longtitude FROM pubs p INNER JOIN pubs_brands pb ON p.id = pb.pub_id AND pb.brand_id = '%1'").arg(this->id);
        break;
    }
    QSqlQuery query(sqlQuery);
    QList<BeerPub*> pubs;
    while(query.next()) {
        BeerPub* pub = new BeerPub();
        pub->setId(query.value(0).toString());
        pub->setTitle(query.value(1).toString());
        pub->setLongitude(query.value(2).toDouble());
        pub->setLatitude(query.value(3).toDouble());
        pub->setDistance(0);
        pubs.append(pub);
    }

   return pubs;

}

void PubList::showPub(QString pubId)
{
    QString newPub = pubId;
    PubView *view = new PubView(this,newPub);
    view->setWindowFlags( view->windowFlags() ^ Qt::WindowSoftkeysVisibleHint );
    view->setModal(true);
    view->showFullScreen();
}

void PubList::showPubList(PubListType type, QString id)
{
    this->id = id;
    this->type = type;
    QList<BeerPub*> pubs = this->getPubs();
    PubListModel* model = new PubListModel(this, pubs);
    this->ui->listView->setModel(model);
    this->ui->listView->show();
    QListView::connect(this->ui->listView, SIGNAL(pressed(QModelIndex)) , this , SLOT(pubList_itemClicked(QModelIndex)));
}

void PubList::pubList_itemClicked(const QModelIndex &current)
{

    QVariant data = current.data(Qt::EditRole);
    this->showPub(data.toString());

}
