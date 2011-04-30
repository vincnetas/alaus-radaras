#ifndef ALAUSRADARAS_H
#define ALAUSRADARAS_H

#include <QMainWindow>
#include <dbmanager.h>
#include <QProgressDialog>
#include "brandtabs.h"
#include "publist.h"
#include "dbpopulator.h"
#include "waitdialog.h"
#include "beercounter.h"
#include "enums.h"
#include "settings.h"
namespace Ui {
    class AlausRadaras;
}

class AlausRadaras : public QWidget
{
    Q_OBJECT

public:
    explicit AlausRadaras(QWidget *parent = 0);
    ~AlausRadaras();
    void setUpdateVersion(const QString &version);
    void keyPressEvent(QKeyEvent *);
    void changeEvent(QEvent* event);
private slots:
    void on_btnBrands_clicked();
    void on_btnNear_clicked();
    void on_btnLucky_clicked();
    void on_btnExit_clicked();
    void on_btnCounter_clicked();
    void loadUpdate(QString string);
    void on_btnSettings_clicked();
    void settingsAccepted();
    void changeLanguage(QLocale::Language language);
private:
    Ui::AlausRadaras *ui;
    Settings *settingsView;
    QTranslator *qtTranslator;
    QTranslator *myappTranslator;
    void retranslateUi();
signals:
    void BrandsSelected();
    void PubListSelected(PubListType type, QString id, QString header);
    void LetsCount();
    void ExitApp();
    void FeelingLucky();
};

#endif // ALAUSRADARAS_H
