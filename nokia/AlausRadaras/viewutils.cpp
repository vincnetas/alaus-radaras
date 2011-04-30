#include "viewutils.h"
#include <QString>

QString ViewUtils::IconRes = "64";

bool ViewUtils::HighRes = true;

QLocale::Language ViewUtils::ActiveLanguage = QLocale::Lithuanian;

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

QSize ViewUtils::GetMugSize()
{
    return (ViewUtils::HighRes) ? QSize(178,178) : QSize(64,64);
}

QString ViewUtils::WrapText(QString text, int wrapLen)
{
    if(text.length() > wrapLen)
    {
        text=text.left(wrapLen)+"\n"+text.mid(wrapLen,text.length()-wrapLen+1);
    }
    return text;
}
QString ViewUtils::GetStringFromLanguage(QLocale::Language lang)
{
    if(lang == QLocale::Lithuanian) {
        return "lt";
    } else if(lang == QLocale::English) {
        return "en";
    } else if (lang == QLocale::Russian) {
        return "ru";
    }
}

QLocale::Language ViewUtils::GetLanguageFromString(QString lang)
{
    if(lang == "lt") {
        return QLocale::Lithuanian;
    } else if(lang == "en") {
        return QLocale::English;
    } else if (lang == "ru") {
        return QLocale::Russian;
    }
}

QLocale::Language ViewUtils::GetLanguage(int index)
{
    switch(index) {
        case 0:
            return QLocale::Lithuanian;
        case 1:
            return QLocale::English;
        case 2:
            return QLocale::Russian;
    }
}

int ViewUtils::GetLanguageIndex (QLocale::Language language)
{
    switch(language) {
        case QLocale::Lithuanian:
            return 0;
        case QLocale::English:
            return 1;
        case QLocale::Russian:
            return 2;
    }
}
