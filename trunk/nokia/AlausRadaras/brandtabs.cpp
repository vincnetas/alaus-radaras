#include "brandtabs.h"
#include "ui_brandtabs.h"
#include "brandlistmodel.h"
#include "countrylistmodel.h"
#include "taglistmodel.h"
#include <QMessageBox>
#include <publist.h>
#include "QsKineticScroller.h"

BrandTabs::BrandTabs(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::BrandTabs)
{
    ui->setupUi(this);

    pubList = NULL;
    brandList = NULL;


    brandListView = new QListView(ui->tabBrands);
    brandListView->setObjectName(QString::fromUtf8("brandListView"));
    brandListView->setStyleSheet(QString::fromUtf8("#brandListView { border-image:url(:/images/background.png); } "));
    brandListView->setHorizontalScrollBarPolicy(Qt::ScrollBarAlwaysOff);
    brandListView->setVerticalScrollMode(QListView::ScrollPerPixel);
    brandListView->setHorizontalScrollMode(QListView::ScrollPerPixel);

    brandListView->setUniformItemSizes (true);
    QsKineticScroller *listKineticScroller = new QsKineticScroller(this);
    listKineticScroller->enableKineticScrollFor(brandListView);

    ui->verticalLayout_2->addWidget(brandListView);

    QListView::connect(brandListView, SIGNAL(pressed(QModelIndex)) , this , SLOT(brandList_itemClicked(QModelIndex)));

    brandsModel = new BrandListModel();
    brandsModel->setQuery("select icon, title, id from brands");
    brandListView->setModel(brandsModel);



//    QListWidget *brandList = new QListWidget(this);
//    brandList->setUniformItemSizes(true);
//    brandList->setVerticalScrollMode(QListView::ScrollPerPixel);
//    brandList->setHorizontalScrollMode(QListView::ScrollPerPixel);
//    QSqlQuery brands("select icon, title, id from brands");
//    while(brands.next()) {

//        QListWidgetItem *newItem = new QListWidgetItem;
//            newItem->setText(brands.value(1).toString());
//            brandList->addItem(newItem);

//    }

//    QsKineticScroller *listKineticScroller = new QsKineticScroller(this);
//    listKineticScroller->enableKineticScrollFor(brandList);

//    ui->verticalLayout_2->addWidget(brandList);


    countryModel = new CountryListModel();
    countryModel->setQuery("select name, code from countries");
    ui->countryListView->setModel(countryModel);

    QListView::connect(ui->countryListView, SIGNAL(pressed(QModelIndex)) , this , SLOT(countryList_itemClicked(QModelIndex)));

    tagsModel = new TagListModel();
    tagsModel->setQuery("select title, code from tags");
    ui->tagListView->setModel(tagsModel);
    QListView::connect(ui->tagListView, SIGNAL(pressed(QModelIndex)) , this , SLOT(tagList_itemClicked(QModelIndex)));
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

void BrandTabs::publist_destroyed()
{
    delete pubList;
    pubList = NULL;
}

void BrandTabs::brandList_destroyed()
{
    delete brandList;
    brandList = NULL;
}

void BrandTabs::on_btnClose_clicked()
{
    this->close();
}

BrandTabs::~BrandTabs()
{
    delete brandListView;
    delete tagsModel;
    delete countryModel;
    delete brandsModel;
    delete pubList;
    delete brandList;
    delete ui;
}
