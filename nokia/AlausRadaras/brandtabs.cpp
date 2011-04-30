#include <QDebug>
#include "brandtabs.h"
#include "ui_brandtabs.h"
#include "brandlistmodel.h"
#include "countrylistmodel.h"
#include "taglistmodel.h"
#include "viewutils.h"
#include "qtscroller.h"
BrandTabs::BrandTabs(QWidget *parent) :
    QWidget(parent),
    brandsModel(0),
    tagsModel(0),
    countryModel(0),
    ui(new Ui::BrandTabs)
{
    ui->setupUi(this);
    //slots and events
    QListView::connect(ui->countryListView, SIGNAL(activated(QModelIndex)) , this , SLOT(countryList_itemClicked(QModelIndex)));
    QListView::connect(ui->tagListView, SIGNAL(activated(QModelIndex)) , this , SLOT(tagList_itemClicked(QModelIndex)));
    QListView::connect(ui->brandListView, SIGNAL(activated(QModelIndex)) , this , SLOT(brandList_itemClicked(QModelIndex)));

    //kinetic scrolling

    QtScroller::grabGesture(ui->tagListView->viewport(), QtScroller::QtScroller::LeftMouseButtonGesture);
    QtScroller::grabGesture(ui->brandListView->viewport(), QtScroller::QtScroller::LeftMouseButtonGesture);
    QtScroller::grabGesture(ui->countryListView->viewport(), QtScroller::QtScroller::LeftMouseButtonGesture);

    //performance background
    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));

    ui->brandListView->setPalette(ViewUtils::GetBackground(ui->brandListView->palette()));
    ui->brandListView->setAutoFillBackground(true);

    ui->tagListView->setPalette(ViewUtils::GetBackground(ui->tagListView->palette()));
    ui->tagListView->setAutoFillBackground(true);

    ui->countryListView->setPalette(ViewUtils::GetBackground(ui->countryListView->palette()));
    ui->countryListView->setAutoFillBackground(true);

    //delay init
    connect(ui->tabWidget, SIGNAL(currentChanged(int)),SLOT(loadTabData(int)));

    QIcon icon0;
    icon0.addFile(":/images" + ViewUtils::IconRes+ "/tab_beer_02.png", QSize(), QIcon::Normal, QIcon::Off);
    ui->tabWidget->setTabIcon(0,icon0);

    QIcon icon1;
    icon1.addFile(":/images" + ViewUtils::IconRes+ "/tab_map_02.png", QSize(), QIcon::Normal, QIcon::Off);
    ui->tabWidget->setTabIcon(1,icon1);

    QIcon icon2;
    icon2.addFile(":/images" + ViewUtils::IconRes+ "/tab_tag_02.png", QSize(), QIcon::Normal, QIcon::Off);
    ui->tabWidget->setTabIcon(2,icon2);
}

void BrandTabs::showEvent ( QShowEvent * event )
{
    loadTabData(ui->tabWidget->currentIndex());
}

void BrandTabs::loadTabData(int index)
{
    switch(index) {
        case 0:
            if(!brandsModel) {
                brandsModel = new BrandListModel(this);
                brandsModel->setQuery("select icon, title, id from brands");
                ui->brandListView->setModel(brandsModel);
            }
            break;
        case 1:
            if(!countryModel) {
                countryModel = new CountryListModel(this);
                countryModel->setQuery("select name, code from countries");
                ui->countryListView->setModel(countryModel);
            }
            break;
        case 2:
            if(!tagsModel) {
                tagsModel = new TagListModel(this);
                tagsModel->setQuery("select title, code from tags");
                ui->tagListView->setModel(tagsModel);
            }
            break;
    }
}

void BrandTabs::brandList_itemClicked(const QModelIndex &current)
{
    emit PubListSelected(BRAND, current.data(Qt::EditRole).toString(), current.data().toString());
}

void BrandTabs::countryList_itemClicked(const QModelIndex &current)
{
    emit BrandListSelected(BRAND_COUNTRY, current.data(Qt::EditRole).toString(), current.data().toString());
}

void BrandTabs::tagList_itemClicked(const QModelIndex &current)
{
    emit BrandListSelected(BRAND_TAG, current.data(Qt::EditRole).toString(), current.data().toString());
}

void BrandTabs::on_btnClose_clicked()
{
    emit Back();
}

void BrandTabs::keyPressEvent(QKeyEvent* event)
{
    if(event->nativeVirtualKey() == CancelKey) {
        on_btnClose_clicked();
    }
    QWidget::keyPressEvent(event);
}
void BrandTabs::changeEvent(QEvent* event)
{
    if (event->type() == QEvent::LanguageChange)
    {
        ui->retranslateUi(this);
    }
    QWidget::changeEvent(event);
}

BrandTabs::~BrandTabs()
{
    //qDebug() << "BrandTabs destroyed";
    delete ui;
}
