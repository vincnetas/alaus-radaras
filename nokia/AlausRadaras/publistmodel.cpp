#include "publistmodel.h"
#include <QAbstractListModel>
#include <QPixmap>
#include <QStringBuilder>
#include <viewutils.h>
#include <QDebug>
PubListModel::PubListModel(QObject *parent, const QVector<BeerPub> &pubs ) :
    QAbstractListModel(parent)
{
     this->pubs = pubs;
}

void PubListModel::setPubs(const QVector<BeerPub> &pubs)
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
    return pubs.size();
}

QVariant PubListModel::data( const QModelIndex & index, int role /* = Qt::DisplayRole*/ ) const
{
    switch( role )
    {
        case Qt::DecorationRole:
        {
            return QVariant(QPixmap (":/images" %ViewUtils::IconRes %"/map_01.png"));
        }
        case Qt::EditRole:
        {
            return pubs.at(index.row()).id;
        }
        case Qt::DisplayRole:
        {
            QString data;
            QString distance;
            data.append(pubs.at(index.row()).title);
            data.append("\n ");
            if(pubs.at(index.row()).distance == -1) {
                data.append(tr("? m"));
            }
            else if(pubs.at(index.row()).distance > 1000)  {
                data.append(distance.setNum(pubs.at(index.row()).distance / 1000, 'g', 3));
                data.append(tr(" km"));
            } else {
                data.append(distance.setNum(pubs.at(index.row()).distance, 'f', 0));
                data.append(tr(" metru"));
            }
            data.append(" (");
            data.append(pubs.at(index.row()).city);
            data.append(")");
                return  data;
           // return pubs[index.row()]->title();
        }
        default:
            return QVariant();
        }
}
