/********************************************************************************
** Form generated from reading UI file 'alausradaras.ui'
**
** Created: Sat Jan 8 14:10:31 2011
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
#include <QtGui/QPushButton>
#include <QtGui/QWidget>

QT_BEGIN_NAMESPACE

class Ui_AlausRadaras
{
public:
    QWidget *centralWidget;
    QPushButton *btnNear;
    QPushButton *btnBrands;

    void setupUi(QMainWindow *AlausRadaras)
    {
        if (AlausRadaras->objectName().isEmpty())
            AlausRadaras->setObjectName(QString::fromUtf8("AlausRadaras"));
        AlausRadaras->resize(800, 480);
        centralWidget = new QWidget(AlausRadaras);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        btnNear = new QPushButton(centralWidget);
        btnNear->setObjectName(QString::fromUtf8("btnNear"));
        btnNear->setGeometry(QRect(140, 130, 191, 81));
        btnBrands = new QPushButton(centralWidget);
        btnBrands->setObjectName(QString::fromUtf8("btnBrands"));
        btnBrands->setGeometry(QRect(140, 240, 191, 81));
        AlausRadaras->setCentralWidget(centralWidget);

        retranslateUi(AlausRadaras);

        QMetaObject::connectSlotsByName(AlausRadaras);
    } // setupUi

    void retranslateUi(QMainWindow *AlausRadaras)
    {
        AlausRadaras->setWindowTitle(QApplication::translate("AlausRadaras", "AlausRadaras", 0, QApplication::UnicodeUTF8));
        btnNear->setText(QApplication::translate("AlausRadaras", "Arti", 0, QApplication::UnicodeUTF8));
        btnBrands->setText(QApplication::translate("AlausRadaras", "R\305\253\305\241ys", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class AlausRadaras: public Ui_AlausRadaras {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_ALAUSRADARAS_H
