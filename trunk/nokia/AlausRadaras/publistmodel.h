#ifndef PUBLISTMODEL_H
#define PUBLISTMODEL_H

#include <QObject>
#include <QList>
#include <beerpub.h>
#include <QModelIndex>

class PubListModel : public QAbstractListModel
{
    Q_OBJECT
public:
    explicit PubListModel(QObject *parent = 0, QList<BeerPub*> *pubs = 0);
    int rowCount( const QModelIndex & parent ) const;
    QVariant data( const QModelIndex & index, int role /* = Qt::DisplayRole*/ ) const;
    void refresh();
signals:

public slots:
private:
    QList<BeerPub*> *pubs;
};

#endif // PUBLISTMODEL_H
