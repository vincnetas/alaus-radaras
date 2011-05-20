#ifndef ALAUSRADARAS_H
#define ALAUSRADARAS_H

#include <QMainWindow>
#include <dbmanager.h>
#include <QProgressDialog>
#include "beertabs.h"
#include "publist.h"
#include "waitdialog.h"
#include "beercounter.h"
#include "enums.h"
#include "settings.h"
#include <QMovie>
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
    void showProgress();
    void hideProgress();
private slots:
    void on_btnBeers_clicked();
    void on_btnNear_clicked();
    void on_btnLucky_clicked();
    void on_btnExit_clicked();
    void on_btnCounter_clicked();
    void loadUpdate(QString string);
    void showSettings();
    void settingsAccepted();
    void changeLanguage(QLocale::Language language);
    void requestDbUpdate();
private:
    Ui::AlausRadaras *ui;
    Settings *settingsView;
    QTranslator *qtTranslator;
    QTranslator *myappTranslator;
    QMovie *progressMovie;
    QMenu *settingsMenu;
    void retranslateUi();
signals:
    void BeersSelected();
    void PubListSelected(PubListType type, QString id, QString header);
    void LetsCount();
    void ExitApp();
    void FeelingLucky();
    void RemoteDbUpdateRequested();
};

#endif // ALAUSRADARAS_H
