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


    brandListView = new QListView(ui->tabBrands);
    brandListView->setHorizontalScrollBarPolicy(Qt::ScrollBarAlwaysOff);
    brandListView->setVerticalScrollMode(QListView::ScrollPerPixel);

    brandListView->setUniformItemSizes (true);

    brandListScroller = new QsKineticScroller(this);
    brandListScroller->enableKineticScrollFor(brandListView);
    ui->verticalLayout_2->addWidget(brandListView);

    QListView::connect(brandListView, SIGNAL(pressed(QModelIndex)) , this , SLOT(brandList_itemClicked(QModelIndex)));

    brandsModel = new BrandListModel();
    brandsModel->setQuery("select icon, title, id from brands");
    brandListView->setModel(brandsModel);

    countryModel = new CountryListModel();
    countryModel->setQuery("select name, code from countries");
    ui->countryListView->setModel(countryModel);

    ui->countryListView->setHorizontalScrollBarPolicy(Qt::ScrollBarAlwaysOff);
    ui->countryListView->setVerticalScrollMode(QListView::ScrollPerPixel);
    countryListScroller = new QsKineticScroller(this);
    countryListScroller->enableKineticScrollFor(ui->countryListView);

    QListView::connect(ui->countryListView, SIGNAL(pressed(QModelIndex)) , this , SLOT(countryList_itemClicked(QModelIndex)));

    tagsModel = new TagListModel();
    tagsModel->setQuery("select title, code from tags");
    ui->tagListView->setModel(tagsModel);
    QListView::connect(ui->tagListView, SIGNAL(pressed(QModelIndex)) , this , SLOT(tagList_itemClicked(QModelIndex)));

    ui->tagListView->setHorizontalScrollBarPolicy(Qt::ScrollBarAlwaysOff);
    ui->tagListView->setVerticalScrollMode(QListView::ScrollPerPixel);
    tagsListScroller = new QsKineticScroller(this);
    tagsListScroller->enableKineticScrollFor(ui->tagListView);

    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));

    brandListView->setPalette(ViewUtils::GetBackground(brandListView->palette()));
    brandListView->setAutoFillBackground(true);

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
    connect(this->brandList,SIGNAL(destroyed()),this,SLOT(brandList_destroyed()));

}

void BrandTabs::tagList_itemClicked(const QModelIndex &current)
{
    QVariant data = current.data(Qt::EditRole);
    brandList = new BrandList(this,BRAND_TAG,data.toString());
    brandList->setHeader(current.data().toString());
    brandList->showFullScreen();
    connect(this->brandList,SIGNAL(destroyed()),this,SLOT(brandList_destroyed()));

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
    delete brandListView;
    delete brandListScroller;
    delete tagsListScroller;
    delete countryListScroller;
    delete brandList;

}
