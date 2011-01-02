#include "alausradaras.h"
#include "ui_alausradaras.h"
#include <QSqlQueryModel>
#include <QTableView>
#include<QMessageBox>

AlausRadaras::AlausRadaras(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::AlausRadaras)
{
    ui->setupUi(this);
    try {
    dbManager = new DbManager();
    //this must be run in another thread
    dbManager->init();
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
    catch( char * str ) {
        QMessageBox::critical(0, qApp->tr("Cannot open database"),
                              str, QMessageBox::Cancel);
    }
}

AlausRadaras::~AlausRadaras()
{

    delete ui;
    delete dbManager;
}
