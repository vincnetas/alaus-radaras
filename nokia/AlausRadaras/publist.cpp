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


PubList::PubList(QWidget *parent, PubListType type, QString id) :
    QMainWindow(parent),
    ui(new Ui::PubList),
    id(id),
    type(type)
{
    ui->setupUi(this);

    dataProvider = new DataProvider(this);
    pubView = NULL;

    switch(this->type)
    {
        case ALL:
            pubs = dataProvider->getAllPubs();
        break;
        case BRAND:
            pubs = dataProvider->getPubsByBrandId(this->id);
        break;
        case COUNTRY:
            pubs = dataProvider->getPubsByCountry(this->id);
         break;
            case TAG:
                 pubs = dataProvider->getPubsByTag(this->id);
        break;
    }

    pubListModel = new PubListModel(this, pubs);
    this->ui->listView->setModel(pubListModel);
    this->ui->listView->show();
    QListView::connect(this->ui->listView, SIGNAL(pressed(QModelIndex)) , this , SLOT(pubList_itemClicked(QModelIndex)));
}

PubList::~PubList()
{
    for(int i = 0; i < pubs.size() ; i++) {
        delete pubs[i];
    }

    delete pubListModel;
    delete pubView;
    delete ui;
    delete dataProvider;
}

void PubList::showPub(QString pubId)
{
    QString newPub = pubId;
    pubView = new PubView(this,newPub);
    pubView->setModal(true);
    pubView->showFullScreen();
    connect(pubView,SIGNAL(accepted()), this,SLOT(pubview_accepted()));
}

void PubList::pubview_accepted()
{
    delete pubView;
    pubView = NULL;
}

void PubList::setHeader(QString text)
{
    this->ui->txtHeader->setText(text);
}

void PubList::on_btnBack_clicked()
{
 this->close();
}

void PubList::pubList_itemClicked(const QModelIndex &current)
{

    QVariant data = current.data(Qt::EditRole);
    this->showPub(data.toString());

}
