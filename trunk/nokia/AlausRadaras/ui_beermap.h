/********************************************************************************
** Form generated from reading UI file 'beermap.ui'
**
** Created: Tue Jan 11 19:26:29 2011
**      by: Qt User Interface Compiler version 4.6.3
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_BEERMAP_H
#define UI_BEERMAP_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtGui/QApplication>
#include <QtGui/QButtonGroup>
#include <QtGui/QHeaderView>
#include <QtGui/QMainWindow>
#include <QtGui/QVBoxLayout>
#include <QtGui/QWidget>
#include <QtWebKit/QWebView>

QT_BEGIN_NAMESPACE

class Ui_BeerMap
{
public:
    QWidget *centralwidget;
    QVBoxLayout *verticalLayout;
    QWebView *webView;

    void setupUi(QMainWindow *BeerMap)
    {
        if (BeerMap->objectName().isEmpty())
            BeerMap->setObjectName(QString::fromUtf8("BeerMap"));
        BeerMap->resize(800, 600);
        centralwidget = new QWidget(BeerMap);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        verticalLayout = new QVBoxLayout(centralwidget);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        webView = new QWebView(centralwidget);
        webView->setObjectName(QString::fromUtf8("webView"));
        webView->setUrl(QUrl("about:blank"));

        verticalLayout->addWidget(webView);

        BeerMap->setCentralWidget(centralwidget);

        retranslateUi(BeerMap);

        QMetaObject::connectSlotsByName(BeerMap);
    } // setupUi

    void retranslateUi(QMainWindow *BeerMap)
    {
        BeerMap->setWindowTitle(QApplication::translate("BeerMap", "MainWindow", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class BeerMap: public Ui_BeerMap {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_BEERMAP_H
