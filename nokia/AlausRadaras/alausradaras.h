#ifndef ALAUSRADARAS_H
#define ALAUSRADARAS_H

#include <QMainWindow>
#include <dbmanager.h>
#include <QProgressDialog>
#include "brandtabs.h"
#include "publist.h"
#include "feelinglucky.h"
#include "dbpopulator.h"
#include "waitdialog.h"
#include "beercounter.h"
namespace Ui {
    class AlausRadaras;
}

class AlausRadaras : public QMainWindow
{
    Q_OBJECT

public:
    explicit AlausRadaras(QWidget *parent = 0);
    ~AlausRadaras();
public slots:
    void dbInitFinished();
private slots:
    void on_btnBrands_clicked();
    void on_btnNear_clicked();
    void on_btnLucky_clicked();
    void on_btnExit_clicked();
    void on_btnCounter_clicked();
private:
    Ui::AlausRadaras *ui;
    BrandTabs *brandTabs;
    PubList *pubList;
    FeelingLucky *feelingLucky;
    WaitDialog *progress;
    BeerCounter *counter;
    DbManager *dbManager;
    DbPopulator *populator;
};

#endif // ALAUSRADARAS_H
