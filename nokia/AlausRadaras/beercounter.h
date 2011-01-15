#ifndef BEERCOUNTER_H
#define BEERCOUNTER_H

#include <QMainWindow>

namespace Ui {
    class BeerCounter;
}

class BeerCounter : public QMainWindow
{
    Q_OBJECT

public:
    explicit BeerCounter(QWidget *parent = 0);
    ~BeerCounter();

private:
    Ui::BeerCounter *ui;
};

#endif // BEERCOUNTER_H
