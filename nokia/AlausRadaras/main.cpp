#include <QtGui/QApplication>
#include "alausradaras.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    AlausRadaras w;

    w.setWindowFlags( w.windowFlags() ^ Qt::WindowSoftkeysVisibleHint );
    w.showFullScreen();

    return a.exec();
}
