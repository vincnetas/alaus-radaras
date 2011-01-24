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
#include "enums.h"
namespace Ui {
    class AlausRadaras;
}

class AlausRadaras : public QWidget
{
    Q_OBJECT

public:
    explicit AlausRadaras(QWidget *parent = 0);
    ~AlausRadaras();
    void setUpdateVersion(QString version);
private slots:
    void on_btnBrands_clicked();
    void on_btnNear_clicked();
    void on_btnLucky_clicked();
    void on_btnExit_clicked();
    void on_btnCounter_clicked();
    void loadUpdate(QString string);
private:
    Ui::AlausRadaras *ui;
signals:
    void BrandsSelected();
    void PubListSelected(PubListType type, QString id, QString header);
    void LetsCount();
    void ExitApp();
    void FeelingLucky();
};

#endif // ALAUSRADARAS_H
