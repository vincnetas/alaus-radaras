#include "brandlistmodel.h"
#include <QSqlQueryModel>
#include <QFile>
#include <QPixmap>
#include <QStringBuilder>

BrandListModel::BrandListModel(QObject *parent) :
    QSqlQueryModel(parent)
{
}

QVariant BrandListModel::data(const QModelIndex &index, int role) const
{

    if (role == Qt::DecorationRole) {
            QVariant displayValue = QSqlQueryModel::data(index, Qt::DisplayRole);
            QString iconId = displayValue.toString();
            QFile f( ":/brands/brand_" % iconId % ".png");
            if(f.exists()) {
               return QVariant(QPixmap (f.fileName()));
            } else {
               return QVariant(QPixmap (":/brands/brand_default.png"));
            }
        } else if (role == Qt::DisplayRole) {
            QModelIndex textIndex = QSqlQueryModel::index(index.row(), 1);
            QVariant displayValue = QSqlQueryModel::data(textIndex, Qt::DisplayRole);
            return displayValue.toString();

        } else {
            return QSqlQueryModel::data(index, role);
        }
}
