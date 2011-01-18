#ifndef VIEWUTILS_H
#define VIEWUTILS_H

#include <QObject>
#include <QPixmap>
#include <QPalette>

class ViewUtils : public QObject
{
    Q_OBJECT
public:
    explicit ViewUtils(QObject *parent = 0);
    static QPalette GetBackground(const QPalette &);
signals:

public slots:

};

#endif // VIEWUTILS_H
