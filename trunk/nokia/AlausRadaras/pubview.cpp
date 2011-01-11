#include "pubview.h"
#include "ui_pubview.h"
#include <QSqlQuery>
#include "brandlistmodel.h"
#include <QDebug>
#include <QSqlError>
PubView::PubView(QWidget *parent, QString pubId) :
    QDialog(parent),
    ui(new Ui::PubView)
{
    ui->setupUi(this);
    QSqlQuery query(QString("SELECT id, title, address, phone from pubs where id='%1'").arg(pubId));
    while (query.next()) {
        ui->pubPhoneLabel->setText(query.value(3).toString());
        ui->pubAddressLabel->setText(query.value(2).toString());
        ui->pubNameLabel->setText(query.value(1).toString());
    }

    BrandListModel* brandsModel = new BrandListModel();
    brandsModel->setQuery(QString("SELECT b.icon, b.title, b.id FROM brands b INNER JOIN pubs_brands pb ON b.id = pb.brand_id AND pb.pub_id = '%1'").arg(pubId));
    ui->brandListView->setModel(brandsModel);
}
void PubView::on_closeButton_clicked()
{
    this->close();
}

PubView::~PubView()
{
    delete ui;
}
