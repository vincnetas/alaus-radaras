#include "publistmodel.h"
#include <QAbstractListModel>
#include <QPixmap>
PubListModel::PubListModel(QObject *parent, QList<BeerPub*> pubs ) :
    QAbstractListModel(parent)
{
     this->pubs = pubs;
}

int PubListModel::rowCount( const QModelIndex & parent ) const
{
    if (parent.isValid())
        return 0;
    return pubs.length();
}

QVariant PubListModel::data( const QModelIndex & index, int role /* = Qt::DisplayRole*/ ) const
{
    switch( role )
    {
        case Qt::DecorationRole:
        {
            return QVariant(QPixmap (":/images/map_01.png"));
        }
        case Qt::EditRole:
        {
            return pubs[index.row()]->id();
        }
        case Qt::DisplayRole:
        {
//            QString data;
//            QString distance;
//            data.append(pubs[index.row()]->title());
//            data.append("\n ");
//            data.append(distance.setNum(pubs[index.row()]->distance()));
//            data.append(" metru");
//            return  data;
            return pubs[index.row()]->title();
        }
        default:
            return QVariant();
        }
}
