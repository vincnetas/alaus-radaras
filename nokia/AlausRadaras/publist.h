#ifndef PUBLIST_H
#define PUBLIST_H

#include <QMainWindow>
#include <QList>
#include <beerpub.h>
#include <QModelIndex>
namespace Ui {
    class PubList;
}

enum PubListType { ALL, BRAND };

class PubList : public QMainWindow
{
    Q_OBJECT

public:
    explicit PubList(QWidget *parent = 0);
    ~PubList();
    void showPubList(PubListType type, QString id);
public slots:
    void pubList_itemClicked(const QModelIndex &current);
private:
    Ui::PubList *ui;
    PubListType type;
    QString id;
    void showPub(QString pubId);
    QList<BeerPub*> getPubs();
};

#endif // PUBLIST_H
