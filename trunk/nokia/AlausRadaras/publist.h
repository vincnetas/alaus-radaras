#ifndef PUBLIST_H
#define PUBLIST_H

#include <QMainWindow>
#include <QList>
#include <beerpub.h>
#include <QModelIndex>
#include <pubview.h>
#include "publistmodel.h"
#include "dataprovider.h"
namespace Ui {
    class PubList;
}

enum PubListType { ALL, BRAND, COUNTRY, TAG };

class PubList : public QMainWindow
{
    Q_OBJECT

public:
    explicit PubList(QWidget *parent = 0, PubListType type = ALL, QString id = "");
    ~PubList();
    void setHeader(QString text);
private slots:
    void pubList_itemClicked(const QModelIndex &current);
    void pubview_accepted();
    void on_btnBack_clicked();
private:
    Ui::PubList *ui;
    PubListType type;
    QString id;
    void showPub(QString pubId);
    QList<BeerPub*> pubs;
    PubView *pubView;
    PubListModel* pubListModel;
    DataProvider* dataProvider;
};

#endif // PUBLIST_H
