#include "brandlist.h"
#include "ui_brandlist.h"
#include "brandlistmodel.h"
#include "publist.h"
#include "viewutils.h"
#include "qtscroller.h"
#include <QKeyEvent>
#include "enums.h"
BrandList::BrandList(QWidget *parent) :
    QWidget(parent),
    brandListModel(0),
    ui(new Ui::BrandList)
{
    ui->setupUi(this);

    QListView::connect(ui->brandListView, SIGNAL(activated(QModelIndex)) , this , SLOT(brandList_itemClicked(QModelIndex)));

    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));

    ui->brandListView->setAutoFillBackground(true);
    ui->brandListView->setPalette(ViewUtils::GetBackground(ui->brandListView->palette()));
    QtScroller::grabGesture(ui->brandListView->viewport(), QtScroller::QtScroller::LeftMouseButtonGesture);


}

void BrandList::showBrands(BrandListType type, QString id, QString header)
{
    if(!brandListModel) {
        brandListModel = new BrandListModel(this);
    }

    switch(type)
    {
        case BRAND_COUNTRY:
           brandListModel->setQuery(QString("SELECT icon, title, id FROM brands b INNER JOIN brands_countries bc ON b.id = bc.brand_id AND bc.country = '%1'").arg(id));
           break;
       case BRAND_TAG:
           brandListModel->setQuery(QString("SELECT icon, title, id FROM brands b INNER JOIN brands_tags bt ON b.id = bt.brand_id AND bt.tag = '%1'").arg(id));
           break;
    }
    ui->brandListView->setModel(brandListModel);
    setHeader(header);
}

void BrandList::brandList_itemClicked(const QModelIndex &current)
{
    emit PubListSelected(BRAND,current.data(Qt::EditRole).toString(), current.data().toString());
}

void BrandList::setHeader(QString text)
{
    this->ui->txtHeader->setText(text);
}
void BrandList::on_btnBack_clicked()
{
    emit Back();
}

void BrandList::keyPressEvent(QKeyEvent* event)
{
    if(event->nativeVirtualKey() == CancelKey) {
        on_btnBack_clicked();
    }
    QWidget::keyPressEvent(event);
}

BrandList::~BrandList()
{
    delete ui;
}
