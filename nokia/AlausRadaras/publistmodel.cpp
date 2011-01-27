#include "publistmodel.h"
#include <QAbstractListModel>
#include <QPixmap>
PubListModel::PubListModel(QObject *parent, QList<BeerPub*> *pubs ) :
    QAbstractListModel(parent)
{
     this->pubs = pubs;
}

void PubListModel::refresh()
{
    this->reset();
}

int PubListModel::rowCount( const QModelIndex & parent ) const
{
    if (parent.isValid())
        return 0;
    return pubs->length();
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
            return pubs->at(index.row())->id();
        }
        case Qt::DisplayRole:
        {
            QString data;
            QString distance;
            data.append(pubs->at(index.row())->title());
            data.append("\n ");
            if(pubs->at(index.row())->distance() == -1) {
                data.append("? m");
            }
            else if(pubs->at(index.row())->distance() > 1000)  {
                data.append(distance.setNum(pubs->at(index.row())->distance() / 1000, 'g', 3));
                data.append(" km");
            } else {
                data.append(distance.setNum(pubs->at(index.row())->distance(), 'f', 0));
                data.append(QString::fromUtf8(" metru"));
            }
            data.append(" (");
            data.append(pubs->at(index.row())->city());
            data.append(")");
                return  data;
           // return pubs[index.row()]->title();
        }
        default:
            return QVariant();
        }
}
