#ifndef BEERLISTMODEL_H
#define BEERLISTMODEL_H
#include <QSqlQueryModel>
#include <QObject>

class BeerListModel : public QSqlQueryModel
{
    Q_OBJECT
public:
    explicit BeerListModel(QObject *parent = 0);
    QVariant data(const QModelIndex &item, int role) const;
    ~BeerListModel();

signals:

public slots:

};

#endif // BEERLISTMODEL_H
