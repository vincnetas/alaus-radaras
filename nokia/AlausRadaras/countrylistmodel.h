#ifndef COUNTRYLISTMODEL_H
#define COUNTRYLISTMODEL_H

#include <QObject>
#include <QSqlQueryModel>
class CountryListModel : public QSqlQueryModel
{
    Q_OBJECT
public:
    explicit CountryListModel(QObject *parent = 0);
    QVariant data(const QModelIndex &item, int role) const;

signals:

public slots:

};

#endif // COUNTRYLISTMODEL_H
