#include <QtGui/QApplication>
#include "maincontroller.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    QCoreApplication::setOrganizationName("X-Medikai");
    QCoreApplication::setOrganizationDomain("alausradaras.lt");
    QCoreApplication::setApplicationName("Alaus radaras");

    MainController w;

    w.showFullScreen();

    return a.exec();
}
