#include "brandtabs.h"
#include "ui_brandtabs.h"
#include "brandlistmodel.h"
#include "countrylistmodel.h"
#include "taglistmodel.h"

BrandTabs::BrandTabs(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::BrandTabs)
{
    ui->setupUi(this);

    BrandListModel* brandsModel = new BrandListModel();
    brandsModel->setQuery("select icon, title from brands");
    ui->brandListView->setModel(brandsModel);

    CountryListModel* countryModel = new CountryListModel();
    countryModel->setQuery("select name from countries");
    ui->countryListView->setModel(countryModel);

    TagListModel* tagsModel = new TagListModel();
    tagsModel->setQuery("select title from tags");
    ui->tagListView->setModel(tagsModel);

    QAction* backAction = new QAction(this);
    backAction->setText("Back");
    backAction->setSoftKeyRole(QAction::NegativeSoftKey);
    this->addAction(backAction);
    connect(backAction, SIGNAL(triggered()), this, SLOT(close()));
}

BrandTabs::~BrandTabs()
{
    delete ui;
}
