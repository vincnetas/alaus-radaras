#include "brandlist.h"
#include "ui_brandlist.h"
#include "brandlistmodel.h"
#include "publist.h"

BrandList::BrandList(QWidget *parent, BrandListType tp, QString ids) :
    QMainWindow(parent),
    id(ids),
    type(tp),
    ui(new Ui::BrandList)
{
    ui->setupUi(this);

    brandListModel = new BrandListModel(this);
    switch(this->type)
    {
    case BRAND_COUNTRY:
        brandListModel->setQuery(QString("SELECT icon, title, id FROM brands b INNER JOIN brands_countries bc ON b.id = bc.brand_id AND bc.country = '%1'").arg(this->id));
        break;
    case BRAND_TAG:
        brandListModel->setQuery(QString("SELECT icon, title, id FROM brands b INNER JOIN brands_tags bt ON b.id = bt.brand_id AND bt.tag = '%1'").arg(this->id));
        break;
    }

    ui->brandListView->setModel(brandListModel);
    QListView::connect(ui->brandListView, SIGNAL(pressed(QModelIndex)) , this , SLOT(brandList_itemClicked(QModelIndex)));
}

void BrandList::brandList_itemClicked(const QModelIndex &current)
{
    QVariant data = current.data(Qt::EditRole);
    pubList = new PubList(this,BRAND,data.toString());
    pubList->setHeader(current.data().toString());
    pubList->showFullScreen();
    connect(this->pubList,SIGNAL(destroyed()),this,SLOT(publist_destroyed()));

}

void BrandList::setHeader(QString text)
{
    this->ui->txtHeader->setText(text);
}
void BrandList::on_btnBack_clicked()
{
    this->close();
}

void BrandList::publist_destroyed()
{
    delete pubList;
    pubList = NULL;

}


BrandList::~BrandList()
{
    delete pubList;
    delete brandListModel;
    delete ui;
}
