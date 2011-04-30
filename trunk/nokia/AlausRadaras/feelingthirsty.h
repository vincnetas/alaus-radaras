#ifndef FEELINGTHIRSTY_H
#define FEELINGTHIRSTY_H

#include <QWidget>
#include "pubview.h"
#include "publist.h"
#include "enums.h"
#include "dataprovider.h"
#include "feelingluckyinfo.h"

namespace Ui {
    class FeelingThirsty;
}

class FeelingThirsty : public QWidget
{
    Q_OBJECT

public:
    explicit FeelingThirsty(QWidget *parent = 0);
    ~FeelingThirsty();
    void chooseNext();
    void keyPressEvent(QKeyEvent* event);
    void changeEvent(QEvent* event);
private slots:
    void on_btnBeer_clicked();
    void on_btnPub_clicked();
    void on_btnBack_clicked();
private:
    Ui::FeelingThirsty *ui;
    FeelingLuckyInfo luckyInfo;
signals:
    void Back();
    void PubSelected(QString pubId);
    void PubListSelected(PubListType type, QString id, QString header);
};

#endif // FEELINGTHIRSTY_H
