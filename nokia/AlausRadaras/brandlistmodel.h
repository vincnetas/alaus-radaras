#ifndef BRANDLISTMODEL_H
#define BRANDLISTMODEL_H
#include <QSqlQueryModel>
#include <QObject>

class BrandListModel : public QSqlQueryModel
{
    Q_OBJECT
public:
    explicit BrandListModel(QObject *parent = 0);
    QVariant data(const QModelIndex &item, int role) const;

signals:

public slots:

};

#endif // BRANDLISTMODEL_H
