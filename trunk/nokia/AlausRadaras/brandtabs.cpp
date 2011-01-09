#include "brandtabs.h"
#include "ui_brandtabs.h"
#include "brandlistmodel.h"
#include "dbmanager.h"
#include <QSqlError>
#include <QDebug>
BrandTabs::BrandTabs(QWidget *parent, DbManager *db) :
    QWidget(parent),
    ui(new Ui::BrandTabs)
{
    ui->setupUi(this);

    BrandListModel* model = new BrandListModel();
    model->setQuery("select icon, title from brands");
    if (model->lastError().isValid())
        qDebug() << model->lastError();
    model->setHeaderData(0, Qt::Horizontal, QObject::tr("Paveiksliukas"));
    model->setHeaderData(1, Qt::Horizontal, QObject::tr("Pavadinimas"));
    ui->brandListView->setModel(model);
    ui->brandListView->show();

}

BrandTabs::~BrandTabs()
{
    delete ui;
}
