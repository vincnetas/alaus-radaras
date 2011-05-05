#ifndef BeerTabs_H
#define BeerTabs_H

#include <QModelIndex>
#include <QShowEvent>
#include <QWidget>
#include "beerlistmodel.h"
#include "countrylistmodel.h"
#include "taglistmodel.h"
#include "enums.h"
namespace Ui {
    class BeerTabs;
}

class BeerTabs : public QWidget
{
    Q_OBJECT

public:
    explicit BeerTabs(QWidget *parent = 0);
    void keyPressEvent(QKeyEvent* event);
    void changeEvent(QEvent* event);
    ~BeerTabs();
private slots:
    void beerList_itemClicked(const QModelIndex &current);
    void countryList_itemClicked(const QModelIndex &current);
    void tagList_itemClicked(const QModelIndex &current);
    void loadTabData(int index);
    void on_btnClose_clicked();
private:
    Ui::BeerTabs *ui;
    BeerListModel* beersModel;
    CountryListModel* countryModel;
    TagListModel* tagsModel;
protected:
    void showEvent( QShowEvent * event );
signals:
    void PubListSelected(PubListType type, QString id, QString header);
    void BeerListSelected(BeerListType type, QString id, QString header);
    void Back();
};

#endif // BeerTabs_H
