#ifndef BEERCOUNTER_H
#define BEERCOUNTER_H

#include <QMainWindow>
#include <QSettings>
#include "dataprovider.h"
namespace Ui {
    class BeerCounter;
}

class BeerCounter : public QMainWindow
{
    Q_OBJECT

public:
    explicit BeerCounter(QWidget *parent = 0);
    ~BeerCounter();

private slots:
    void on_btnBack_clicked();
    void on_btnClear_clicked();
    void on_btnCount_clicked();
private:
    Ui::BeerCounter *ui;
    QSettings settings;
    DataProvider *dataProvider;
    void showQoute(int count);
};

#endif // BEERCOUNTER_H
