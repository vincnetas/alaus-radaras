#ifndef MAINCONTROLLER_H
#define MAINCONTROLLER_H

#include <QMainWindow>
#include "brandtabs.h"
#include "alausradaras.h"
#include "beercounter.h"
#include "publist.h"
#include "brandlist.h"

namespace Ui {
    class MainController;
}

class MainController : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainController(QWidget *parent = 0);
    ~MainController();

private:
    Ui::MainController *ui;

    BrandTabs *brandTabs;
    AlausRadaras *mainWidget;

    WaitDialog *progress;
    DbManager *dbManager;
    DbPopulator *populator;
    BeerCounter *counter;
    PubList *pubList;
    PubView *pubView;
    FeelingLucky *feelingLucky;
    BrandList *brandList;
    QStack<int> history;

    void clearHistory();


private slots:
    void dbInitFinished();
    void showWidget(int index);
    void showBrandTabs();
    void showCounter();
    void showPubList(PubListType type, QString id, QString header);
    void showBrandList(BrandListType type, QString id, QString header);
    void showMap(QList<BeerPub*> pubs);
    void showPub(QString pubId);
    void showMain();
    void showFeelingLucky();
    void goBack();

};

#endif // MAINCONTROLLER_H
