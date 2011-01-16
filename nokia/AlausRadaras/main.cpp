#include <QtGui/QApplication>
#include "alausradaras.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    QCoreApplication::setOrganizationName("X-Medikai");
    QCoreApplication::setOrganizationDomain("alausradaras.lt");
    QCoreApplication::setApplicationName("Alaus radaras");

    AlausRadaras w;

    w.showFullScreen();

    return a.exec();
}
