#ifndef BRANDTABS_H
#define BRANDTABS_H

#include <QMainWindow>
#include <QModelIndex>
#include "publist.h"
#include "brandlistmodel.h"
#include "countrylistmodel.h"
#include "taglistmodel.h"
#include "brandlist.h"

namespace Ui {
    class BrandTabs;
}

class BrandTabs : public QMainWindow
{
    Q_OBJECT

public:
    explicit BrandTabs(QWidget *parent = 0);
    ~BrandTabs();
private slots:
    void brandList_itemClicked(const QModelIndex &current);
    void countryList_itemClicked(const QModelIndex &current);
    void tagList_itemClicked(const QModelIndex &current);

    void on_btnClose_clicked();
    void publist_destroyed();
    void brandList_destroyed();
private:
    Ui::BrandTabs *ui;
    PubList *pubList;
    BrandListModel* brandsModel;
    CountryListModel* countryModel;
    TagListModel* tagsModel;
    BrandList *brandList;
};

#endif // BRANDTABS_H
