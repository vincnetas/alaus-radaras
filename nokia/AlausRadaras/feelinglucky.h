#ifndef FEELINGLUCKY_H
#define FEELINGLUCKY_H

#include "pubview.h"
#include "publist.h"
#include "enums.h"
#include "dataprovider.h"
#include "feelingluckyinfo.h"

namespace Ui {
    class FeelingLucky;
}

class FeelingLucky : public QWidget
{
    Q_OBJECT

public:
    explicit FeelingLucky(QWidget *parent = 0);
    ~FeelingLucky();
    void chooseNext();
private slots:
    void on_btnBeer_clicked();
    void on_btnPub_clicked();
    void on_btnBack_clicked();
private:
    Ui::FeelingLucky *ui;
    FeelingLuckyInfo luckyInfo;
signals:
    void Back();
    void PubSelected(QString pubId);
    void PubListSelected(PubListType type, QString id, QString header);
};

#endif // FEELINGLUCKY_H
