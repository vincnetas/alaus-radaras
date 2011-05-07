#include "translator.h"

Translator::Translator(QObject *parent) :
    QObject(parent)
{
    //Countries
    Translator::tr("LT","country");
    Translator::tr("CZ","country");
    Translator::tr("DE","country");
    Translator::tr("BE","country");
    Translator::tr("NL","country");
    Translator::tr("IE","country");
    Translator::tr("UK","country");
    Translator::tr("DK","country");
    Translator::tr("FR","country");
    Translator::tr("PL","country");

    //Tags
    Translator::tr("dark","tag");
    Translator::tr("lager","tag");
    Translator::tr("white","tag");
    Translator::tr("foreign","tag");
    Translator::tr("rural","tag");
    Translator::tr("strong","tag");
    Translator::tr("light","tag");
}
