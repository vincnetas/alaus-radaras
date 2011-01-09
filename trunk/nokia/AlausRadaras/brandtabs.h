#ifndef BRANDTABS_H
#define BRANDTABS_H

#include <QWidget>
#include "dbmanager.h"

namespace Ui {
    class BrandTabs;
}

class BrandTabs : public QWidget
{
    Q_OBJECT

public:
     BrandTabs(QWidget *parent = 0, DbManager *db = NULL);
    ~BrandTabs();

private:
    Ui::BrandTabs *ui;
};

#endif // BRANDTABS_H
