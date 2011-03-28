#ifndef BRANDTABS_H
#define BRANDTABS_H

#include <QModelIndex>
#include <QShowEvent>
#include <QWidget>
#include "brandlistmodel.h"
#include "countrylistmodel.h"
#include "taglistmodel.h"
#include "enums.h"
namespace Ui {
    class BrandTabs;
}

class BrandTabs : public QWidget
{
    Q_OBJECT

public:
    explicit BrandTabs(QWidget *parent = 0);
    void keyPressEvent(QKeyEvent* event);
    ~BrandTabs();
private slots:
    void brandList_itemClicked(const QModelIndex &current);
    void countryList_itemClicked(const QModelIndex &current);
    void tagList_itemClicked(const QModelIndex &current);
    void loadTabData(int index);
    void on_btnClose_clicked();
private:
    Ui::BrandTabs *ui;
    BrandListModel* brandsModel;
    CountryListModel* countryModel;
    TagListModel* tagsModel;
protected:
    void showEvent( QShowEvent * event );
signals:
    void PubListSelected(PubListType type, QString id, QString header);
    void BrandListSelected(BrandListType type, QString id, QString header);
    void Back();
};

#endif // BRANDTABS_H
