#include "alausradaras.h"
#include "ui_alausradaras.h"
#include <QSqlQueryModel>
#include <QTableView>
#include<QMessageBox>
#include <QThread>
#include <QProgressDialog>

AlausRadaras::AlausRadaras(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::AlausRadaras)
{
    ui->setupUi(this);

    dbManager = new DbManager();

    dialog = new QProgressDialog();
    dialog->setLabelText("Wait, db initing");
    dialog->setMinimum(0);
    dialog->setMaximum(0);
    dialog->setWindowModality(Qt::WindowModal);
    dialog->showMaximized();
    qApp->processEvents();
    dbManager->init();
    qApp->processEvents();
    dialog->hide();
        this->dbInitFinished();
}
void AlausRadaras::dbInitFinished()
{

    QSqlQueryModel *model = new QSqlQueryModel();
    model->setQuery("select * from brands");
       model->setHeaderData(0, Qt::Horizontal, QObject::tr("id"));
       model->setHeaderData(1, Qt::Horizontal, QObject::tr("title"));
       model->setHeaderData(2, Qt::Horizontal, QObject::tr("icon"));

    QTabWidget *tabWidget = new QTabWidget;
    setCentralWidget(tabWidget);
    QTableView *view = new QTableView;
      view->setModel(model);
      view->resizeColumnsToContents();
      view->show();

       tabWidget->addTab(view, QObject::tr("Test"));
     tabWidget->showMaximized();

}

AlausRadaras::~AlausRadaras()
{
    delete dialog;
    delete ui;
    delete dbManager;
}
