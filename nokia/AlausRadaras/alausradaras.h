#ifndef ALAUSRADARAS_H
#define ALAUSRADARAS_H

#include <QMainWindow>
#include <dbmanager.h>
#include <QProgressDialog>
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
private:
    Ui::AlausRadaras *ui;
};

#endif // ALAUSRADARAS_H
