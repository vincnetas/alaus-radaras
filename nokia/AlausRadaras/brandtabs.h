#ifndef BRANDTABS_H
#define BRANDTABS_H

#include <QMainWindow>

namespace Ui {
    class BrandTabs;
}

class BrandTabs : public QMainWindow
{
    Q_OBJECT

public:
    explicit BrandTabs(QWidget *parent = 0);
    ~BrandTabs();

private:
    Ui::BrandTabs *ui;
};

#endif // BRANDTABS_H
