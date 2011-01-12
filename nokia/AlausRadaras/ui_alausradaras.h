/********************************************************************************
** Form generated from reading UI file 'alausradaras.ui'
**
** Created: Tue Jan 11 19:26:29 2011
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
    QPushButton *btnLucky;
    QPushButton *pushButton;

    void setupUi(QMainWindow *AlausRadaras)
    {
        if (AlausRadaras->objectName().isEmpty())
            AlausRadaras->setObjectName(QString::fromUtf8("AlausRadaras"));
        AlausRadaras->resize(320, 640);
        AlausRadaras->setStyleSheet(QString::fromUtf8("QMainWindow, QWidget, QDialog { border-image: url(:/images/background.png); }"));
        centralWidget = new QWidget(AlausRadaras);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        btnNear = new QPushButton(centralWidget);
        btnNear->setObjectName(QString::fromUtf8("btnNear"));
        btnNear->setGeometry(QRect(20, 230, 281, 111));
        btnNear->setStyleSheet(QString::fromUtf8("border-image:url(:/images/batonas.png);"));
        btnBrands = new QPushButton(centralWidget);
        btnBrands->setObjectName(QString::fromUtf8("btnBrands"));
        btnBrands->setGeometry(QRect(20, 360, 281, 111));
        btnBrands->setStyleSheet(QString::fromUtf8("border-image:url(:/images/batonas.png)"));
        btnLucky = new QPushButton(centralWidget);
        btnLucky->setObjectName(QString::fromUtf8("btnLucky"));
        btnLucky->setGeometry(QRect(20, 490, 281, 131));
        btnLucky->setStyleSheet(QString::fromUtf8("border-image:url(:/images/batonas.png)"));
        pushButton = new QPushButton(centralWidget);
        pushButton->setObjectName(QString::fromUtf8("pushButton"));
        pushButton->setGeometry(QRect(40, 10, 251, 191));
        pushButton->setStyleSheet(QString::fromUtf8("border-image:url(:/images/bok_title.png);"));
        AlausRadaras->setCentralWidget(centralWidget);

        retranslateUi(AlausRadaras);

        QMetaObject::connectSlotsByName(AlausRadaras);
    } // setupUi

    void retranslateUi(QMainWindow *AlausRadaras)
    {
        AlausRadaras->setWindowTitle(QApplication::translate("AlausRadaras", "AlausRadaras", 0, QApplication::UnicodeUTF8));
        btnNear->setText(QApplication::translate("AlausRadaras", "Arti", 0, QApplication::UnicodeUTF8));
        btnBrands->setText(QApplication::translate("AlausRadaras", "R\305\253\305\241ys", 0, QApplication::UnicodeUTF8));
        btnLucky->setText(QApplication::translate("AlausRadaras", "I\305\241tror\305\241kau", 0, QApplication::UnicodeUTF8));
        pushButton->setText(QApplication::translate("AlausRadaras", "PushButton", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class AlausRadaras: public Ui_AlausRadaras {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_ALAUSRADARAS_H
