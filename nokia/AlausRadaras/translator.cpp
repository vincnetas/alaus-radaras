#include "translator.h"

Translator::Translator(QObject *parent) :
    QObject(parent)
{
    //Countries
    Translator::tr("lt","country");
    Translator::tr("cz","country");
    Translator::tr("de","country");
    Translator::tr("be","country");
    Translator::tr("nl","country");
    Translator::tr("ie","country");
    Translator::tr("uk","country");

    //Tags
    Translator::tr("dark","tag");
    Translator::tr("lager","tag");
    Translator::tr("white","tag");
    Translator::tr("foreign","tag");
    Translator::tr("rural","tag");
    Translator::tr("strong","tag");
    Translator::tr("light","tag");
}
