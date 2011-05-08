#include "beerlist.h"
#include "ui_beerlist.h"
#include "beerlistmodel.h"
#include "publist.h"
#include "viewutils.h"
#include "qtscroller.h"
#include <QKeyEvent>
#include "enums.h"
BeerList::BeerList(QWidget *parent) :
    QWidget(parent),
    beerListModel(0),
    ui(new Ui::BeerList)
{
    ui->setupUi(this);

    QListView::connect(ui->beerListView, SIGNAL(activated(QModelIndex)) , this , SLOT(beerList_itemClicked(QModelIndex)));

    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));

    ui->beerListView->setAutoFillBackground(true);
    ui->beerListView->setPalette(ViewUtils::GetBackground(ui->beerListView->palette()));
    QtScroller::grabGesture(ui->beerListView->viewport(), QtScroller::QtScroller::LeftMouseButtonGesture);


}

void BeerList::showBeers(BeerListType type, QString id, QString header)
{
    if(!beerListModel) {
        beerListModel = new BeerListModel(this);
    }

    switch(type)
    {
        case BEER_COUNTRY:
           beerListModel->setQuery(QString("SELECT b.icon, b.title, b.id FROM beers b INNER JOIN brands br ON b.brand_id = br.id  WHERE br.country = '%1' ORDER BY b.title").arg(id));
           break;
       case BEER_TAG:
           beerListModel->setQuery(QString("SELECT icon, title, id FROM beers b INNER JOIN beer_tags bt ON b.id = bt.beer_id AND bt.tag = '%1' ORDER BY b.title").arg(id));
           break;
    }
    ui->beerListView->setModel(beerListModel);
    setHeader(header);
}

void BeerList::beerList_itemClicked(const QModelIndex &current)
{
    emit PubListSelected(BEER,current.data(Qt::EditRole).toString(), current.data().toString());
}

void BeerList::setHeader(QString text)
{
    this->ui->txtHeader->setText(text);
}
void BeerList::on_btnBack_clicked()
{
    emit Back();
}

void BeerList::keyPressEvent(QKeyEvent* event)
{
    if(event->nativeVirtualKey() == CancelKey) {
        on_btnBack_clicked();
    }
    QWidget::keyPressEvent(event);
}

void BeerList::changeEvent(QEvent* event)
{
    if (event->type() == QEvent::LanguageChange)
    {
        ui->retranslateUi(this);
    }
    QWidget::changeEvent(event);
}

BeerList::~BeerList()
{
    delete ui;
}
