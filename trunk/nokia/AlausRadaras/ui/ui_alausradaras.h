/********************************************************************************
** Form generated from reading UI file 'alausradaras.ui'
**
** Created: Mon Jan 3 00:34:17 2011
**      by: Qt User Interface Compiler version 4.6.3
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_ALAUSRADARAS_H
#define UI_ALAUSRADARAS_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtGui/QApplication>
#include <QtGui/QButtonGroup>
#include <QtGui/QHeaderView>
#include <QtGui/QMainWindow>
#include <QtGui/QWidget>

QT_BEGIN_NAMESPACE

class Ui_AlausRadaras
{
public:
    QWidget *centralWidget;

    void setupUi(QMainWindow *AlausRadaras)
    {
        if (AlausRadaras->objectName().isEmpty())
            AlausRadaras->setObjectName(QString::fromUtf8("AlausRadaras"));
        AlausRadaras->resize(800, 480);
        centralWidget = new QWidget(AlausRadaras);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        AlausRadaras->setCentralWidget(centralWidget);

        retranslateUi(AlausRadaras);

        QMetaObject::connectSlotsByName(AlausRadaras);
    } // setupUi

    void retranslateUi(QMainWindow *AlausRadaras)
    {
        AlausRadaras->setWindowTitle(QApplication::translate("AlausRadaras", "AlausRadaras", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class AlausRadaras: public Ui_AlausRadaras {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_ALAUSRADARAS_H
