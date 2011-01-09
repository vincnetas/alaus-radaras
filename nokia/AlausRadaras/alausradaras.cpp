#include "alausradaras.h"
#include "ui_alausradaras.h"
#include "brandtabs.h"
#include <QThread>
#include <QDebug>
#include <QMenuBar>
AlausRadaras::AlausRadaras(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::AlausRadaras)
{
    ui->setupUi(this);
    dbManager = new DbManager();
    dbManager->init();

    QAction* exitAction = new QAction(this);
    exitAction->setText("Remove");
    exitAction->setSoftKeyRole(QAction::NegativeSoftKey);
    this->addAction(exitAction);
}
void AlausRadaras::dbInitFinished()
{
}

void AlausRadaras::on_btnBrands_clicked()
{
    BrandTabs* tabs = new BrandTabs(this, this->dbManager);
    setCentralWidget(tabs);

}

AlausRadaras::~AlausRadaras()
{
    delete dialog;
    delete ui;
    delete dbManager;
}
