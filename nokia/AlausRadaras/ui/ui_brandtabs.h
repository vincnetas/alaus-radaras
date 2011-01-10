/********************************************************************************
** Form generated from reading UI file 'brandtabs.ui'
**
** Created: Mon Jan 10 21:53:59 2011
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
#include <QtGui/QMainWindow>
#include <QtGui/QTabWidget>
#include <QtGui/QVBoxLayout>
#include <QtGui/QWidget>

QT_BEGIN_NAMESPACE

class Ui_BrandTabs
{
public:
    QWidget *centralwidget;
    QVBoxLayout *verticalLayout;
    QTabWidget *tabWidget;
    QWidget *tabBrands;
    QVBoxLayout *verticalLayout_2;
    QListView *brandListView;
    QWidget *tabCountries;
    QVBoxLayout *verticalLayout_3;
    QListView *countryListView;
    QWidget *tabTags;
    QVBoxLayout *verticalLayout_4;
    QListView *tagListView;

    void setupUi(QMainWindow *BrandTabs)
    {
        if (BrandTabs->objectName().isEmpty())
            BrandTabs->setObjectName(QString::fromUtf8("BrandTabs"));
        BrandTabs->resize(800, 600);
        centralwidget = new QWidget(BrandTabs);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        verticalLayout = new QVBoxLayout(centralwidget);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        tabWidget = new QTabWidget(centralwidget);
        tabWidget->setObjectName(QString::fromUtf8("tabWidget"));
        tabBrands = new QWidget();
        tabBrands->setObjectName(QString::fromUtf8("tabBrands"));
        verticalLayout_2 = new QVBoxLayout(tabBrands);
        verticalLayout_2->setObjectName(QString::fromUtf8("verticalLayout_2"));
        brandListView = new QListView(tabBrands);
        brandListView->setObjectName(QString::fromUtf8("brandListView"));

        verticalLayout_2->addWidget(brandListView);

        tabWidget->addTab(tabBrands, QString());
        tabCountries = new QWidget();
        tabCountries->setObjectName(QString::fromUtf8("tabCountries"));
        verticalLayout_3 = new QVBoxLayout(tabCountries);
        verticalLayout_3->setObjectName(QString::fromUtf8("verticalLayout_3"));
        countryListView = new QListView(tabCountries);
        countryListView->setObjectName(QString::fromUtf8("countryListView"));

        verticalLayout_3->addWidget(countryListView);

        tabWidget->addTab(tabCountries, QString());
        tabTags = new QWidget();
        tabTags->setObjectName(QString::fromUtf8("tabTags"));
        verticalLayout_4 = new QVBoxLayout(tabTags);
        verticalLayout_4->setObjectName(QString::fromUtf8("verticalLayout_4"));
        tagListView = new QListView(tabTags);
        tagListView->setObjectName(QString::fromUtf8("tagListView"));

        verticalLayout_4->addWidget(tagListView);

        tabWidget->addTab(tabTags, QString());

        verticalLayout->addWidget(tabWidget);

        BrandTabs->setCentralWidget(centralwidget);

        retranslateUi(BrandTabs);

        tabWidget->setCurrentIndex(2);


        QMetaObject::connectSlotsByName(BrandTabs);
    } // setupUi

    void retranslateUi(QMainWindow *BrandTabs)
    {
        BrandTabs->setWindowTitle(QApplication::translate("BrandTabs", "MainWindow", 0, QApplication::UnicodeUTF8));
        tabWidget->setTabText(tabWidget->indexOf(tabBrands), QApplication::translate("BrandTabs", "R\305\253\305\241ys", 0, QApplication::UnicodeUTF8));
        tabWidget->setTabText(tabWidget->indexOf(tabCountries), QApplication::translate("BrandTabs", "\305\240alys", 0, QApplication::UnicodeUTF8));
        tabWidget->setTabText(tabWidget->indexOf(tabTags), QApplication::translate("BrandTabs", "Tipai", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class BrandTabs: public Ui_BrandTabs {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_BRANDTABS_H
