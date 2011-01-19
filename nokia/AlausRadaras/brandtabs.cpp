#include "brandtabs.h"
#include "ui_brandtabs.h"
#include "brandlistmodel.h"
#include "countrylistmodel.h"
#include "taglistmodel.h"
#include <QMessageBox>
#include <publist.h>
#include "QsKineticScroller.h"
#include "viewutils.h"
#include <QDebug>
BrandTabs::BrandTabs(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::BrandTabs)
{
    ui->setupUi(this);

    pubList = NULL;
    brandList = NULL;

    brandsModel = new BrandListModel();
    brandsModel->setQuery("select icon, title, id from brands");
    ui->brandListView->setModel(brandsModel);

    countryModel = new CountryListModel();
    countryModel->setQuery("select name, code from countries");
    ui->countryListView->setModel(countryModel);

    tagsModel = new TagListModel();
    tagsModel->setQuery("select title, code from tags");
    ui->tagListView->setModel(tagsModel);

    //slots and events
    QListView::connect(ui->countryListView, SIGNAL(pressed(QModelIndex)) , this , SLOT(countryList_itemClicked(QModelIndex)));
    QListView::connect(ui->tagListView, SIGNAL(pressed(QModelIndex)) , this , SLOT(tagList_itemClicked(QModelIndex)));
    QListView::connect(ui->brandListView, SIGNAL(pressed(QModelIndex)) , this , SLOT(brandList_itemClicked(QModelIndex)));



    //kinetic scrolling

    tagsListScroller = new QsKineticScroller(this);
    tagsListScroller->enableKineticScrollFor(ui->tagListView);

    brandListScroller = new QsKineticScroller(this);
    brandListScroller->enableKineticScrollFor(ui->brandListView);

    countryListScroller = new QsKineticScroller(this);
    countryListScroller->enableKineticScrollFor(ui->countryListView);

    //performance background
    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));

    ui->brandListView->setPalette(ViewUtils::GetBackground(ui->brandListView->palette()));
    ui->brandListView->setAutoFillBackground(true);

    ui->tagListView->setPalette(ViewUtils::GetBackground(ui->tagListView->palette()));
    ui->tagListView->setAutoFillBackground(true);

    ui->countryListView->setPalette(ViewUtils::GetBackground(ui->countryListView->palette()));
    ui->countryListView->setAutoFillBackground(true);


}

void BrandTabs::brandList_itemClicked(const QModelIndex &current)
{
    QVariant data = current.data(Qt::EditRole);
    pubList = new PubList(this,BRAND,data.toString());
    pubList->setHeader(current.data().toString());
    pubList->showFullScreen();
    connect(this->pubList,SIGNAL(destroyed()),this,SLOT(publist_destroyed()));

}

void BrandTabs::countryList_itemClicked(const QModelIndex &current)
{
    QVariant data = current.data(Qt::EditRole);
    brandList = new BrandList(this,BRAND_COUNTRY,data.toString());
    brandList->showFullScreen();
    brandList->setHeader(current.data().toString());
}

void BrandTabs::tagList_itemClicked(const QModelIndex &current)
{
    QVariant data = current.data(Qt::EditRole);
    brandList = new BrandList(this,BRAND_TAG,data.toString());
    brandList->setHeader(current.data().toString());
    brandList->showFullScreen();
}



void BrandTabs::on_btnClose_clicked()
{
    this->close();
}

BrandTabs::~BrandTabs()
{
    qDebug() << "BrandTabs destroyed";
    delete ui;
    delete pubList;
    delete brandsModel;
    delete countryModel;
    delete tagsModel;
    delete brandListScroller;
    delete tagsListScroller;
    delete countryListScroller;
    delete brandList;

}
