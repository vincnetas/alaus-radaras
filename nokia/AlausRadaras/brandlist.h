#ifndef BRANDLIST_H
#define BRANDLIST_H

#include <QWidget>
#include "brandlistmodel.h"
#include "enums.h"

namespace Ui {
    class BrandList;
}

class BrandList : public QWidget
{
    Q_OBJECT

public:
    explicit BrandList(QWidget *parent = 0);
    ~BrandList();
    void setHeader(QString text);
    void showBrands(BrandListType type, QString id, QString header);
    void keyPressEvent(QKeyEvent* event);
    void changeEvent(QEvent* event);

private:
    Ui::BrandList *ui;

    BrandListModel *brandListModel;

    QString id;
    BrandListType type;

private slots:
    void brandList_itemClicked(const QModelIndex &current);
    void on_btnBack_clicked();

signals:
    void PubListSelected(PubListType type, QString id, QString header);
    void Back();
};

#endif // BRANDLIST_H
