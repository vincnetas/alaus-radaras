#ifndef VIEWUTILS_H
#define VIEWUTILS_H

#include <QObject>
#include <QPixmap>
#include <QPalette>
#include <QLocale>

class ViewUtils : public QObject
{
    Q_OBJECT
public:
    explicit ViewUtils(QObject *parent = 0);
    static QPalette GetBackground(const QPalette &);
    static QString WrapText(QString text, int length);
    static QString IconRes;
    static QSize GetMugSize();
    static bool HighRes;
    static QLocale::Language ActiveLanguage;
    static QString GetStringFromLanguage(QLocale::Language lang);
    static QLocale::Language GetLanguageFromString(QString lang);
    static QLocale::Language GetLanguage(int index);
    static int GetLanguageIndex (QLocale::Language language);
signals:

public slots:

};
#endif // VIEWUTILS_H
