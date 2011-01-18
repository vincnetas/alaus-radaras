#include "viewutils.h"

ViewUtils::ViewUtils(QObject *parent) :
    QObject(parent)
{
}

QPalette ViewUtils::GetBackground(const QPalette &palette)
{

    QPalette p(palette);
    p.setBrush(QPalette::Background,  QPixmap(":/images/background.png"));
    return p;

}
