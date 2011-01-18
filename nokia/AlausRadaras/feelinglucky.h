#ifndef FEELINGLUCKY_H
#define FEELINGLUCKY_H

#include <QMainWindow>
#include "pubview.h"
#include "publist.h"
#include "dataprovider.h"
#include "feelingluckyinfo.h"

namespace Ui {
    class FeelingLucky;
}

class FeelingLucky : public QMainWindow
{
    Q_OBJECT

public:
    explicit FeelingLucky(QWidget *parent = 0);
    ~FeelingLucky();
private slots:
    void on_btnBeer_clicked();
    void on_btnPub_clicked();
    void on_btnBack_clicked();
    void pubview_accepted();
private:
    Ui::FeelingLucky *ui;
    PubView *pubView;
    PubList *pubList;
    FeelingLuckyInfo luckyInfo;
};

#endif // FEELINGLUCKY_H
