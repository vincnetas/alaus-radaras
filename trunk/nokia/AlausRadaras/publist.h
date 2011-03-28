#ifndef PUBLIST_H
#define PUBLIST_H

#include <QWidget>
#include <QList>
#include <QModelIndex>


#include "publistmodel.h"
#include "dataprovider.h"
#include "enums.h"
#include <beerpub.h>



namespace Ui {
    class PubList;
}



class PubList : public QWidget
{
    Q_OBJECT

public:
    explicit PubList(QWidget *parent = 0);
    ~PubList();
    void setHeader(QString text);
    void showPubList(PubListType type, QString id, QString header);
    void locationChanged(qreal latitude,qreal longitude);
    void keyPressEvent(QKeyEvent* event);
private slots:
    void pubList_itemClicked(const QModelIndex &current);
    void on_btnBack_clicked();
    void on_btnMap_clicked();


private:

    Ui::PubList *ui;
    QList<BeerPub*> pubs;
    PubListModel* pubListModel;
    DataProvider* dataProvider;

signals:
    void MapSelected(QList<BeerPub*> pubs);
    void PubSelected(QString pubId);
    void Back();
};

#endif // PUBLIST_H
