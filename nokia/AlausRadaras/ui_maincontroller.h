/********************************************************************************
** Form generated from reading UI file 'maincontroller.ui'
**
** Created: Tue Jan 25 22:57:52 2011
**      by: Qt User Interface Compiler version 4.6.3
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINCONTROLLER_H
#define UI_MAINCONTROLLER_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtGui/QApplication>
#include <QtGui/QButtonGroup>
#include <QtGui/QHeaderView>
#include <QtGui/QMainWindow>
#include <QtGui/QStackedWidget>
#include <QtGui/QVBoxLayout>
#include <QtGui/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MainController
{
public:
    QWidget *centralwidget;
    QVBoxLayout *verticalLayout;
    QStackedWidget *stackedWidget;

    void setupUi(QMainWindow *MainController)
    {
        if (MainController->objectName().isEmpty())
            MainController->setObjectName(QString::fromUtf8("MainController"));
        MainController->resize(800, 600);
        centralwidget = new QWidget(MainController);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        verticalLayout = new QVBoxLayout(centralwidget);
        verticalLayout->setSpacing(0);
        verticalLayout->setContentsMargins(0, 0, 0, 0);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        stackedWidget = new QStackedWidget(centralwidget);
        stackedWidget->setObjectName(QString::fromUtf8("stackedWidget"));

        verticalLayout->addWidget(stackedWidget);

        MainController->setCentralWidget(centralwidget);

        retranslateUi(MainController);

        QMetaObject::connectSlotsByName(MainController);
    } // setupUi

    void retranslateUi(QMainWindow *MainController)
    {
        MainController->setWindowTitle(QApplication::translate("MainController", "MainWindow", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class MainController: public Ui_MainController {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINCONTROLLER_H
