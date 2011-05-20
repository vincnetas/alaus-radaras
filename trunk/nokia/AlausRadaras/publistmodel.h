#ifndef PUBLISTMODEL_H
#define PUBLISTMODEL_H

#include <QObject>
#include <QVector>
#include <beerpub.h>
#include <QModelIndex>

class PubListModel : public QAbstractListModel
{
    Q_OBJECT
public:
    PubListModel(QObject *parent, const QVector<BeerPub> &pubs);
    int rowCount( const QModelIndex & parent ) const;
    QVariant data( const QModelIndex & index, int role /* = Qt::DisplayRole*/ ) const;
    void setPubs(const QVector<BeerPub> &pubs);
    void refresh();
signals:

public slots:
private:
    QVector<BeerPub> pubs;
};

#endif // PUBLISTMODEL_H
