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
    DbManager *dbManager;
    QProgressDialog *dialog;
    DbPopulator *populator;
    ~AlausRadaras();
public slots:
    void dbInitFinished();
private slots:
    void on_btnBrands_clicked();
    void on_btnNear_clicked();
    void on_btnLucky_clicked();
    void brandTabs_destroyed();
    void pubList_destroyed();
    void feelingLucky_destroyed();
    void on_btnExit_clicked();
    void on_btnCounter_clicked();
    void beerCounter_destroyed();
private:
    Ui::AlausRadaras *ui;
    BrandTabs *brandTabs;
    PubList *pubList;
    FeelingLucky *feelingLucky;
    WaitDialog *progress;
    BeerCounter *counter;
};

#endif // ALAUSRADARAS_H
