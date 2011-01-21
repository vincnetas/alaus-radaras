#include "pubview.h"
#include "ui_pubview.h"
#include <QSqlQuery>
#include "brandlistmodel.h"
#include <QDebug>
#include <QSqlError>
#include "viewutils.h"
#include <qskineticscroller.h>
PubView::PubView(QWidget *parent) :
    QWidget(parent),
    brandsModel(0),
    ui(new Ui::PubView)
{
    ui->setupUi(this);

    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));

     ui->brandListView->setAutoFillBackground(true);
     ui->brandListView->setPalette(ViewUtils::GetBackground(ui->brandListView->palette()));

     brandListScroller = new QsKineticScroller(this);
     brandListScroller->enableKineticScrollFor(ui->brandListView);
}

void PubView::showPub(QString pubId)
{

    QSqlQuery query(QString("SELECT id, title, address, phone from pubs where id='%1'").arg(pubId));
    while (query.next()) {
        ui->pubPhoneLabel->setText(query.value(3).toString());
        ui->pubAddressLabel->setText(query.value(2).toString());
        ui->pubNameLabel->setText(query.value(1).toString());
    }
    if(!brandsModel) {
        brandsModel = new BrandListModel();
    }

    brandsModel->setQuery(QString("SELECT b.icon, b.title, b.id FROM brands b INNER JOIN pubs_brands pb ON b.id = pb.brand_id AND pb.pub_id = '%1'").arg(pubId));

    ui->brandListView->setModel(brandsModel);


}

void PubView::on_closeButton_clicked()
{
    emit Back();
}

PubView::~PubView()
{
    qDebug() << "pubview destructor called";
    delete ui;
}
