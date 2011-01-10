#ifndef ALAUSRADARAS_H
#define ALAUSRADARAS_H

#include <QMainWindow>
#include <dbmanager.h>
#include <QProgressDialog>
#include "brandtabs.h"
namespace Ui {
    class AlausRadaras;
}

class AlausRadaras : public QMainWindow
{
    Q_OBJECT

public:
    explicit AlausRadaras(QWidget *parent = 0);
    DbManager *dbManager;
    QProgressDialog *dialog;
    ~AlausRadaras();
public slots:
    void dbInitFinished();
private slots:
    void on_btnBrands_clicked();
    void on_btnNear_clicked();
private:
    Ui::AlausRadaras *ui;
    BrandTabs *brandTabs;
};

#endif // ALAUSRADARAS_H
