#ifndef BEERLIST_H
#define BEERLIST_H

#include <QWidget>
#include "beerlistmodel.h"
#include "enums.h"

namespace Ui {
    class BeerList;
}

class BeerList : public QWidget
{
    Q_OBJECT

public:
    explicit BeerList(QWidget *parent = 0);
    ~BeerList();
    void setHeader(QString text);
    void showBeers(BeerListType type, QString id, QString header);
    void keyPressEvent(QKeyEvent* event);
    void changeEvent(QEvent* event);

private:
    Ui::BeerList *ui;

    BeerListModel *beerListModel;

    QString id;
    BeerListType type;

private slots:
    void beerList_itemClicked(const QModelIndex &current);
    void on_btnBack_clicked();

signals:
    void PubListSelected(PubListType type, QString id, QString header);
    void Back();
};

#endif // BEERLIST_H
