#include <QtGui/QApplication>
#include "maincontroller.h"
#include "qtscrollerproperties.h"
#include "viewutils.h"
#include <QApplication>
#include <QDesktopWidget>
#include <QRect>
int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    QCoreApplication::setOrganizationName("X-Medikai");
    QCoreApplication::setOrganizationDomain("alausradaras.lt");
    QCoreApplication::setApplicationName("Alaus radaras");

    QRect res = QApplication::desktop()->screenGeometry();
    if(res.width() < 360 && res.height() < 360 ) {
        ViewUtils::IconRes = "32";
        ViewUtils::HighRes = false;
    }
    else {
        ViewUtils::IconRes = "";
        ViewUtils::HighRes = true;
    }

    QtScrollerProperties sp;
    sp.setScrollMetric(QtScrollerProperties::DragVelocitySmoothingFactor, qreal(0.25));

    QtScrollerProperties::setDefaultScrollerProperties(sp);

    QTextCodec::setCodecForCStrings(QTextCodec::codecForName("utf-8"));
    MainController w;

    w.showFullScreen();

    return a.exec();
}
