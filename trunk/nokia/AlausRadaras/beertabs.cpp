#include <QDebug>
#include "beertabs.h"
#include "ui_BeerTabs.h"
#include "beerlistmodel.h"
#include "countrylistmodel.h"
#include "taglistmodel.h"
#include "viewutils.h"
#include "qtscroller.h"
BeerTabs::BeerTabs(QWidget *parent) :
    QWidget(parent),
    beersModel(0),
    tagsModel(0),
    countryModel(0),
    ui(new Ui::BeerTabs)
{
    ui->setupUi(this);
    //slots and events
    QListView::connect(ui->countryListView, SIGNAL(activated(QModelIndex)) , this , SLOT(countryList_itemClicked(QModelIndex)));
    QListView::connect(ui->tagListView, SIGNAL(activated(QModelIndex)) , this , SLOT(tagList_itemClicked(QModelIndex)));
    QListView::connect(ui->beerListView, SIGNAL(activated(QModelIndex)) , this , SLOT(beerList_itemClicked(QModelIndex)));

    //kinetic scrolling

    QtScroller::grabGesture(ui->tagListView->viewport(), QtScroller::QtScroller::LeftMouseButtonGesture);
    QtScroller::grabGesture(ui->beerListView->viewport(), QtScroller::QtScroller::LeftMouseButtonGesture);
    QtScroller::grabGesture(ui->countryListView->viewport(), QtScroller::QtScroller::LeftMouseButtonGesture);

    //performance background
    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));

    ui->beerListView->setPalette(ViewUtils::GetBackground(ui->beerListView->palette()));
    ui->beerListView->setAutoFillBackground(true);

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

void BeerTabs::showEvent ( QShowEvent * event )
{
    loadTabData(ui->tabWidget->currentIndex());
}

void BeerTabs::loadTabData(int index)
{
    switch(index) {
        case 0:
            if(!beersModel) {
                beersModel = new BeerListModel(this);
                beersModel->setQuery("select icon, title, id from brands");
                ui->beerListView->setModel(beersModel);
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

void BeerTabs::beerList_itemClicked(const QModelIndex &current)
{
    emit PubListSelected(BEER, current.data(Qt::EditRole).toString(), current.data().toString());
}

void BeerTabs::countryList_itemClicked(const QModelIndex &current)
{
    emit BeerListSelected(BEER_COUNTRY, current.data(Qt::EditRole).toString(), current.data().toString());
}

void BeerTabs::tagList_itemClicked(const QModelIndex &current)
{
    emit BeerListSelected(BEER_TAG, current.data(Qt::EditRole).toString(), current.data().toString());
}

void BeerTabs::on_btnClose_clicked()
{
    emit Back();
}

void BeerTabs::keyPressEvent(QKeyEvent* event)
{
    if(event->nativeVirtualKey() == CancelKey) {
        on_btnClose_clicked();
    }
    QWidget::keyPressEvent(event);
}
void BeerTabs::changeEvent(QEvent* event)
{
    if (event->type() == QEvent::LanguageChange)
    {
        ui->retranslateUi(this);
    }
    QWidget::changeEvent(event);
}

BeerTabs::~BeerTabs()
{
    //qDebug() << "BeerTabs destroyed";
    delete ui;
}
