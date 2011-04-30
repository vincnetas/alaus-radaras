#ifndef BEERCOUNTER_H
#define BEERCOUNTER_H

#include <QMainWindow>
#include <QSettings>
#include "dataprovider.h"
namespace Ui {
    class BeerCounter;
}

class BeerCounter : public QWidget
{
    Q_OBJECT

public:
    explicit BeerCounter(QWidget *parent = 0);
    ~BeerCounter();
    void keyPressEvent(QKeyEvent* event);
    void changeEvent(QEvent* event);

private slots:
    void on_btnBack_clicked();
    void on_btnClear_clicked();
    void on_btnCount_clicked();
private:
    Ui::BeerCounter *ui;
    QSettings settings;
    DataProvider *dataProvider;
    void showQoute(int count);
    void retranslateUi();
signals:
    void Back();
};

#endif // BEERCOUNTER_H
