#ifndef TAGLISTMODEL_H
#define TAGLISTMODEL_H

#include <QObject>
#include <QSqlQueryModel>

class TagListModel :  public QSqlQueryModel
{
    Q_OBJECT
public:
    explicit TagListModel(QObject *parent = 0);
    QVariant data(const QModelIndex &item, int role) const;
signals:

public slots:

};

#endif // TAGLISTMODEL_H
