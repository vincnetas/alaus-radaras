/********************************************************************************
** Form generated from reading UI file 'feelingthirsty.ui'
**
** Created: Sun Apr 3 22:23:41 2011
**      by: Qt User Interface Compiler version 4.6.3
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_FEELINGTHIRSTY_H
#define UI_FEELINGTHIRSTY_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtGui/QApplication>
#include <QtGui/QButtonGroup>
#include <QtGui/QHBoxLayout>
#include <QtGui/QHeaderView>
#include <QtGui/QPushButton>
#include <QtGui/QSpacerItem>
#include <QtGui/QVBoxLayout>
#include <QtGui/QWidget>

QT_BEGIN_NAMESPACE

class Ui_FeelingThirsty
{
public:
    QVBoxLayout *verticalLayout;
    QSpacerItem *verticalSpacer_3;
    QPushButton *btnBeer;
    QSpacerItem *verticalSpacer_2;
    QPushButton *btnPub;
    QSpacerItem *verticalSpacer;
    QHBoxLayout *horizontalLayout;
    QSpacerItem *horizontalSpacer;
    QPushButton *btnBack;

    void setupUi(QWidget *FeelingThirsty)
    {
        if (FeelingThirsty->objectName().isEmpty())
            FeelingThirsty->setObjectName(QString::fromUtf8("FeelingThirsty"));
        FeelingThirsty->resize(240, 240);
        QSizePolicy sizePolicy(QSizePolicy::Expanding, QSizePolicy::Expanding);
        sizePolicy.setHorizontalStretch(0);
        sizePolicy.setVerticalStretch(0);
        sizePolicy.setHeightForWidth(FeelingThirsty->sizePolicy().hasHeightForWidth());
        FeelingThirsty->setSizePolicy(sizePolicy);
        verticalLayout = new QVBoxLayout(FeelingThirsty);
        verticalLayout->setContentsMargins(0, 0, 0, 0);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        verticalSpacer_3 = new QSpacerItem(20, 40, QSizePolicy::Minimum, QSizePolicy::Expanding);

        verticalLayout->addItem(verticalSpacer_3);

        btnBeer = new QPushButton(FeelingThirsty);
        btnBeer->setObjectName(QString::fromUtf8("btnBeer"));
        QSizePolicy sizePolicy1(QSizePolicy::Preferred, QSizePolicy::Preferred);
        sizePolicy1.setHorizontalStretch(0);
        sizePolicy1.setVerticalStretch(0);
        sizePolicy1.setHeightForWidth(btnBeer->sizePolicy().hasHeightForWidth());
        btnBeer->setSizePolicy(sizePolicy1);
        QPalette palette;
        btnBeer->setPalette(palette);
        QFont font;
        font.setPointSize(12);
        btnBeer->setFont(font);
        btnBeer->setStyleSheet(QString::fromUtf8("#btnBeer {\n"
"border-image:url(:/images/lucky.png)\n"
"}"));
        QIcon icon;
        icon.addFile(QString::fromUtf8(":/brands/brand_default.png"), QSize(), QIcon::Normal, QIcon::Off);
        btnBeer->setIcon(icon);

        verticalLayout->addWidget(btnBeer);

        verticalSpacer_2 = new QSpacerItem(20, 40, QSizePolicy::Minimum, QSizePolicy::Expanding);

        verticalLayout->addItem(verticalSpacer_2);

        btnPub = new QPushButton(FeelingThirsty);
        btnPub->setObjectName(QString::fromUtf8("btnPub"));
        sizePolicy.setHeightForWidth(btnPub->sizePolicy().hasHeightForWidth());
        btnPub->setSizePolicy(sizePolicy);
        QPalette palette1;
        btnPub->setPalette(palette1);
        btnPub->setFont(font);
        btnPub->setStyleSheet(QString::fromUtf8("#btnPub {\n"
"border-image:url(:/images/lucky.png)\n"
"}"));

        verticalLayout->addWidget(btnPub);

        verticalSpacer = new QSpacerItem(20, 40, QSizePolicy::Minimum, QSizePolicy::Expanding);

        verticalLayout->addItem(verticalSpacer);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setObjectName(QString::fromUtf8("horizontalLayout"));
        horizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer);

        btnBack = new QPushButton(FeelingThirsty);
        btnBack->setObjectName(QString::fromUtf8("btnBack"));
        QSizePolicy sizePolicy2(QSizePolicy::Minimum, QSizePolicy::Minimum);
        sizePolicy2.setHorizontalStretch(0);
        sizePolicy2.setVerticalStretch(10);
        sizePolicy2.setHeightForWidth(btnBack->sizePolicy().hasHeightForWidth());
        btnBack->setSizePolicy(sizePolicy2);
        btnBack->setStyleSheet(QString::fromUtf8("\n"
"#btnBack{\n"
"border-image:url(:/images/batonas.png);\n"
"}"));

        horizontalLayout->addWidget(btnBack);

        horizontalLayout->setStretch(0, 8);
        horizontalLayout->setStretch(1, 4);

        verticalLayout->addLayout(horizontalLayout);

        verticalLayout->setStretch(0, 5);
        verticalLayout->setStretch(1, 8);
        verticalLayout->setStretch(2, 5);
        verticalLayout->setStretch(3, 8);
        verticalLayout->setStretch(4, 5);
        verticalLayout->setStretch(5, 2);

        retranslateUi(FeelingThirsty);

        QMetaObject::connectSlotsByName(FeelingThirsty);
    } // setupUi

    void retranslateUi(QWidget *FeelingThirsty)
    {
        FeelingThirsty->setWindowTitle(QApplication::translate("FeelingThirsty", "I\305\241tro\305\241kau", 0, QApplication::UnicodeUTF8));
        btnBeer->setText(QApplication::translate("FeelingThirsty", "Alus", 0, QApplication::UnicodeUTF8));
        btnPub->setText(QApplication::translate("FeelingThirsty", "Baras", 0, QApplication::UnicodeUTF8));
        btnBack->setText(QApplication::translate("FeelingThirsty", "Atgal", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class FeelingThirsty: public Ui_FeelingThirsty {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_FEELINGTHIRSTY_H
