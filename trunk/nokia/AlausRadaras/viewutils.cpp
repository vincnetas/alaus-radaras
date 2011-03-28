#include "viewutils.h"
#include <QString>
QString ViewUtils::IconRes = "64";

bool ViewUtils::HighRes = true;

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

QString ViewUtils::WrapText(QString text, int wrapLen)
{
    if(text.length() > wrapLen)
    {
        text=text.left(wrapLen)+"\n"+text.mid(wrapLen,text.length()-wrapLen+1);
    }
    return text;
}
