#ifndef BRANDLIST_H
#define BRANDLIST_H

#include <QMainWindow>
#include "brandlistmodel.h"
#include "publist.h"


namespace Ui {
    class BrandList;
}

enum BrandListType { BRAND_COUNTRY, BRAND_TAG };

class BrandList : public QMainWindow
{
    Q_OBJECT

public:
    explicit BrandList(QWidget *parent = 0, BrandListType tp = BRAND_COUNTRY, QString ids = "");
    ~BrandList();
    void setHeader(QString text);

private:
    Ui::BrandList *ui;
    BrandListType type;
    BrandListModel *brandListModel;
    PubList *pubList;
    QString id;
private slots:
    void brandList_itemClicked(const QModelIndex &current);
    void publist_destroyed();
    void on_btnBack_clicked();
};

#endif // BRANDLIST_H
