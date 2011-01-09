/********************************************************************************
** Form generated from reading UI file 'brandtabs.ui'
**
** Created: Sun Jan 9 22:25:11 2011
**      by: Qt User Interface Compiler version 4.6.3
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_BRANDTABS_H
#define UI_BRANDTABS_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtGui/QApplication>
#include <QtGui/QButtonGroup>
#include <QtGui/QHeaderView>
#include <QtGui/QListView>
#include <QtGui/QTabWidget>
#include <QtGui/QWidget>

QT_BEGIN_NAMESPACE

class Ui_BrandTabs
{
public:
    QTabWidget *tabWidget;
    QWidget *tab;
    QListView *brandListView;
    QWidget *tab_3;
    QListView *listView;
    QWidget *tab_2;

    void setupUi(QWidget *BrandTabs)
    {
        if (BrandTabs->objectName().isEmpty())
            BrandTabs->setObjectName(QString::fromUtf8("BrandTabs"));
        BrandTabs->resize(320, 640);
        tabWidget = new QTabWidget(BrandTabs);
        tabWidget->setObjectName(QString::fromUtf8("tabWidget"));
        tabWidget->setGeometry(QRect(0, 0, 320, 640));
        tab = new QWidget();
        tab->setObjectName(QString::fromUtf8("tab"));
        brandListView = new QListView(tab);
        brandListView->setObjectName(QString::fromUtf8("brandListView"));
        brandListView->setGeometry(QRect(0, 0, 471, 611));
        tabWidget->addTab(tab, QString());
        tab_3 = new QWidget();
        tab_3->setObjectName(QString::fromUtf8("tab_3"));
        listView = new QListView(tab_3);
        listView->setObjectName(QString::fromUtf8("listView"));
        listView->setGeometry(QRect(0, 0, 320, 640));
        tabWidget->addTab(tab_3, QString());
        tab_2 = new QWidget();
        tab_2->setObjectName(QString::fromUtf8("tab_2"));
        tabWidget->addTab(tab_2, QString());

        retranslateUi(BrandTabs);

        tabWidget->setCurrentIndex(0);


        QMetaObject::connectSlotsByName(BrandTabs);
    } // setupUi

    void retranslateUi(QWidget *BrandTabs)
    {
        BrandTabs->setWindowTitle(QApplication::translate("BrandTabs", "Form", 0, QApplication::UnicodeUTF8));
        tabWidget->setTabText(tabWidget->indexOf(tab), QApplication::translate("BrandTabs", "Tab 1", 0, QApplication::UnicodeUTF8));
        tabWidget->setTabText(tabWidget->indexOf(tab_3), QApplication::translate("BrandTabs", "Page", 0, QApplication::UnicodeUTF8));
        tabWidget->setTabText(tabWidget->indexOf(tab_2), QApplication::translate("BrandTabs", "Tab 2", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class BrandTabs: public Ui_BrandTabs {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_BRANDTABS_H
