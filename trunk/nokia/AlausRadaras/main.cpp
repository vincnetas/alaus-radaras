#include <QtGui/QApplication>
#include "maincontroller.h"
#include "qtscrollerproperties.h"
int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    QCoreApplication::setOrganizationName("X-Medikai");
    QCoreApplication::setOrganizationDomain("alausradaras.lt");
    QCoreApplication::setApplicationName("Alaus radaras");


    QtScrollerProperties sp;
    sp.setScrollMetric(QtScrollerProperties::DragVelocitySmoothingFactor, qreal(0.25));

    QtScrollerProperties::setDefaultScrollerProperties(sp);

    QTextCodec::setCodecForCStrings(QTextCodec::codecForName("utf-8"));
    MainController w;

    w.showFullScreen();

    return a.exec();
}
