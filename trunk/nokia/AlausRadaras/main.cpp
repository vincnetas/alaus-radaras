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
    sp.setScrollMetric(QtScrollerProperties::AcceleratingFlickMaximumTime, 5.00);
    sp.setScrollMetric(QtScrollerProperties::MaximumVelocity, 900.0 / 1000);

    QtScrollerProperties::setDefaultScrollerProperties(sp);

    QTextCodec::setCodecForCStrings(QTextCodec::codecForName("utf-8"));
    MainController w;

    w.showFullScreen();

    return a.exec();
}
