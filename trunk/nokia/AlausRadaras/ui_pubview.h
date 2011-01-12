/********************************************************************************
** Form generated from reading UI file 'pubview.ui'
**
** Created: Tue Jan 11 19:26:29 2011
**      by: Qt User Interface Compiler version 4.6.3
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_PUBVIEW_H
#define UI_PUBVIEW_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtGui/QApplication>
#include <QtGui/QButtonGroup>
#include <QtGui/QDialog>
#include <QtGui/QHBoxLayout>
#include <QtGui/QHeaderView>
#include <QtGui/QLabel>
#include <QtGui/QListView>
#include <QtGui/QPushButton>
#include <QtGui/QSpacerItem>
#include <QtGui/QVBoxLayout>

QT_BEGIN_NAMESPACE

class Ui_PubView
{
public:
    QVBoxLayout *verticalLayout;
    QLabel *pubNameLabel;
    QHBoxLayout *horizontalLayout_2;
    QLabel *pubPhoneLabel;
    QHBoxLayout *horizontalLayout_3;
    QLabel *pubAddressLabel;
    QListView *brandListView;
    QHBoxLayout *horizontalLayout_4;
    QSpacerItem *horizontalSpacer;
    QPushButton *closeButton;

    void setupUi(QDialog *PubView)
    {
        if (PubView->objectName().isEmpty())
            PubView->setObjectName(QString::fromUtf8("PubView"));
        PubView->resize(400, 300);
        verticalLayout = new QVBoxLayout(PubView);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        pubNameLabel = new QLabel(PubView);
        pubNameLabel->setObjectName(QString::fromUtf8("pubNameLabel"));
        QFont font;
        font.setPointSize(15);
        pubNameLabel->setFont(font);

        verticalLayout->addWidget(pubNameLabel);

        horizontalLayout_2 = new QHBoxLayout();
        horizontalLayout_2->setObjectName(QString::fromUtf8("horizontalLayout_2"));
        pubPhoneLabel = new QLabel(PubView);
        pubPhoneLabel->setObjectName(QString::fromUtf8("pubPhoneLabel"));
        QFont font1;
        font1.setPointSize(11);
        pubPhoneLabel->setFont(font1);

        horizontalLayout_2->addWidget(pubPhoneLabel);


        verticalLayout->addLayout(horizontalLayout_2);

        horizontalLayout_3 = new QHBoxLayout();
        horizontalLayout_3->setObjectName(QString::fromUtf8("horizontalLayout_3"));
        pubAddressLabel = new QLabel(PubView);
        pubAddressLabel->setObjectName(QString::fromUtf8("pubAddressLabel"));
        pubAddressLabel->setFont(font1);

        horizontalLayout_3->addWidget(pubAddressLabel);


        verticalLayout->addLayout(horizontalLayout_3);

        brandListView = new QListView(PubView);
        brandListView->setObjectName(QString::fromUtf8("brandListView"));
        brandListView->setViewMode(QListView::ListMode);

        verticalLayout->addWidget(brandListView);

        horizontalLayout_4 = new QHBoxLayout();
        horizontalLayout_4->setObjectName(QString::fromUtf8("horizontalLayout_4"));
        horizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout_4->addItem(horizontalSpacer);

        closeButton = new QPushButton(PubView);
        closeButton->setObjectName(QString::fromUtf8("closeButton"));

        horizontalLayout_4->addWidget(closeButton);


        verticalLayout->addLayout(horizontalLayout_4);


        retranslateUi(PubView);

        QMetaObject::connectSlotsByName(PubView);
    } // setupUi

    void retranslateUi(QDialog *PubView)
    {
        PubView->setWindowTitle(QApplication::translate("PubView", "Dialog", 0, QApplication::UnicodeUTF8));
        pubNameLabel->setText(QApplication::translate("PubView", "TextLabel", 0, QApplication::UnicodeUTF8));
        pubPhoneLabel->setText(QApplication::translate("PubView", "TextLabel", 0, QApplication::UnicodeUTF8));
        pubAddressLabel->setText(QApplication::translate("PubView", "TextLabel", 0, QApplication::UnicodeUTF8));
        closeButton->setText(QApplication::translate("PubView", "Atgal", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class PubView: public Ui_PubView {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_PUBVIEW_H
