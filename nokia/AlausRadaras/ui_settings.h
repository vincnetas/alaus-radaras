/********************************************************************************
** Form generated from reading UI file 'settings.ui'
**
** Created: Thu Mar 31 07:35:09 2011
**      by: Qt User Interface Compiler version 4.6.3
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_SETTINGS_H
#define UI_SETTINGS_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtGui/QApplication>
#include <QtGui/QButtonGroup>
#include <QtGui/QCheckBox>
#include <QtGui/QComboBox>
#include <QtGui/QDialog>
#include <QtGui/QHBoxLayout>
#include <QtGui/QHeaderView>
#include <QtGui/QLabel>
#include <QtGui/QPushButton>
#include <QtGui/QSpacerItem>
#include <QtGui/QVBoxLayout>

QT_BEGIN_NAMESPACE

class Ui_Settings
{
public:
    QVBoxLayout *verticalLayout;
    QLabel *header;
    QHBoxLayout *horizontalLayout_2;
    QLabel *label;
    QComboBox *comboUpdateFrequency;
    QSpacerItem *horizontalSpacer_2;
    QSpacerItem *verticalSpacer_2;
    QCheckBox *cbEnableInternet;
    QSpacerItem *verticalSpacer;
    QHBoxLayout *horizontalLayout;
    QSpacerItem *horizontalSpacer;
    QPushButton *btnBack;

    void setupUi(QDialog *Settings)
    {
        if (Settings->objectName().isEmpty())
            Settings->setObjectName(QString::fromUtf8("Settings"));
        Settings->setWindowModality(Qt::ApplicationModal);
        Settings->resize(240, 240);
        QSizePolicy sizePolicy(QSizePolicy::Expanding, QSizePolicy::Expanding);
        sizePolicy.setHorizontalStretch(0);
        sizePolicy.setVerticalStretch(0);
        sizePolicy.setHeightForWidth(Settings->sizePolicy().hasHeightForWidth());
        Settings->setSizePolicy(sizePolicy);
        QIcon icon;
        icon.addFile(QString::fromUtf8(":/images/bok_title.png"), QSize(), QIcon::Normal, QIcon::Off);
        Settings->setWindowIcon(icon);
        verticalLayout = new QVBoxLayout(Settings);
        verticalLayout->setContentsMargins(0, 0, 0, 0);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        header = new QLabel(Settings);
        header->setObjectName(QString::fromUtf8("header"));
        QPalette palette;
        QBrush brush(QColor(255, 255, 255, 255));
        brush.setStyle(Qt::SolidPattern);
        palette.setBrush(QPalette::Active, QPalette::Text, brush);
        palette.setBrush(QPalette::Inactive, QPalette::Text, brush);
        QBrush brush1(QColor(106, 104, 100, 255));
        brush1.setStyle(Qt::SolidPattern);
        palette.setBrush(QPalette::Disabled, QPalette::Text, brush1);
        header->setPalette(palette);
        QFont font;
        font.setPointSize(10);
        header->setFont(font);
        header->setStyleSheet(QString::fromUtf8("#header{\n"
"border-image:url(:/images/lucky.png);\n"
"padding-left:10px;\n"
"}"));

        verticalLayout->addWidget(header);

        horizontalLayout_2 = new QHBoxLayout();
        horizontalLayout_2->setObjectName(QString::fromUtf8("horizontalLayout_2"));
        label = new QLabel(Settings);
        label->setObjectName(QString::fromUtf8("label"));
        QPalette palette1;
        palette1.setBrush(QPalette::Active, QPalette::Text, brush);
        palette1.setBrush(QPalette::Inactive, QPalette::Text, brush);
        palette1.setBrush(QPalette::Disabled, QPalette::Text, brush1);
        label->setPalette(palette1);

        horizontalLayout_2->addWidget(label);

        comboUpdateFrequency = new QComboBox(Settings);
        comboUpdateFrequency->setObjectName(QString::fromUtf8("comboUpdateFrequency"));

        horizontalLayout_2->addWidget(comboUpdateFrequency);

        horizontalSpacer_2 = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout_2->addItem(horizontalSpacer_2);

        horizontalLayout_2->setStretch(1, 5);
        horizontalLayout_2->setStretch(2, 5);

        verticalLayout->addLayout(horizontalLayout_2);

        verticalSpacer_2 = new QSpacerItem(20, 40, QSizePolicy::Minimum, QSizePolicy::Expanding);

        verticalLayout->addItem(verticalSpacer_2);

        cbEnableInternet = new QCheckBox(Settings);
        cbEnableInternet->setObjectName(QString::fromUtf8("cbEnableInternet"));
        QPalette palette2;
        palette2.setBrush(QPalette::Active, QPalette::WindowText, brush);
        palette2.setBrush(QPalette::Active, QPalette::Text, brush);
        palette2.setBrush(QPalette::Active, QPalette::ButtonText, brush);
        palette2.setBrush(QPalette::Inactive, QPalette::WindowText, brush);
        palette2.setBrush(QPalette::Inactive, QPalette::Text, brush);
        palette2.setBrush(QPalette::Inactive, QPalette::ButtonText, brush);
        palette2.setBrush(QPalette::Disabled, QPalette::WindowText, brush1);
        palette2.setBrush(QPalette::Disabled, QPalette::Text, brush1);
        palette2.setBrush(QPalette::Disabled, QPalette::ButtonText, brush1);
        cbEnableInternet->setPalette(palette2);

        verticalLayout->addWidget(cbEnableInternet);

        verticalSpacer = new QSpacerItem(20, 40, QSizePolicy::Minimum, QSizePolicy::Expanding);

        verticalLayout->addItem(verticalSpacer);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setObjectName(QString::fromUtf8("horizontalLayout"));
        horizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer);

        btnBack = new QPushButton(Settings);
        btnBack->setObjectName(QString::fromUtf8("btnBack"));
        QSizePolicy sizePolicy1(QSizePolicy::Minimum, QSizePolicy::Minimum);
        sizePolicy1.setHorizontalStretch(0);
        sizePolicy1.setVerticalStretch(10);
        sizePolicy1.setHeightForWidth(btnBack->sizePolicy().hasHeightForWidth());
        btnBack->setSizePolicy(sizePolicy1);
        btnBack->setStyleSheet(QString::fromUtf8("#btnBack{\n"
"border-image:url(:/images/batonas.png);\n"
"}"));

        horizontalLayout->addWidget(btnBack);

        horizontalLayout->setStretch(0, 8);
        horizontalLayout->setStretch(1, 4);

        verticalLayout->addLayout(horizontalLayout);

        verticalLayout->setStretch(1, 1);
        verticalLayout->setStretch(2, 1);
        verticalLayout->setStretch(4, 10);
        verticalLayout->setStretch(5, 1);

        retranslateUi(Settings);

        QMetaObject::connectSlotsByName(Settings);
    } // setupUi

    void retranslateUi(QDialog *Settings)
    {
        Settings->setWindowTitle(QApplication::translate("Settings", "Nustatymai", 0, QApplication::UnicodeUTF8));
        header->setText(QApplication::translate("Settings", "Programos nustatymai", 0, QApplication::UnicodeUTF8));
        label->setText(QApplication::translate("Settings", "Atnaujinti", 0, QApplication::UnicodeUTF8));
        cbEnableInternet->setText(QApplication::translate("Settings", "Jungtis prie interneto", 0, QApplication::UnicodeUTF8));
        btnBack->setText(QApplication::translate("Settings", "Atgal", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class Settings: public Ui_Settings {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_SETTINGS_H
