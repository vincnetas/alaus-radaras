#include "brandtabs.h"
#include "ui_brandtabs.h"
#include "brandlistmodel.h"
#include "countrylistmodel.h"
#include "taglistmodel.h"
#include <QMessageBox>

BrandTabs::BrandTabs(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::BrandTabs)
{
    ui->setupUi(this);

    BrandListModel* brandsModel = new BrandListModel();
    brandsModel->setQuery("select icon, title, id from brands");
    ui->brandListView->setModel(brandsModel);
    QListView::connect(ui->brandListView, SIGNAL(pressed(QModelIndex)) , this , SLOT(brandList_itemClicked(QModelIndex)));


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

void BrandTabs::brandList_itemClicked(const QModelIndex &current)
{

    QVariant data = current.data(Qt::EditRole);
    QString dataString = data.toString();

    QMessageBox msgBox;
     msgBox.setText(dataString);
  //   msgBox.exec();
}

BrandTabs::~BrandTabs()
{
    delete ui;
}