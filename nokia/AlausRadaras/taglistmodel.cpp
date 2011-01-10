#include "taglistmodel.h"
#include <QSqlQueryModel>
#include <QPixmap>
TagListModel::TagListModel(QObject *parent) :
        QSqlQueryModel(parent)
    {
    }

QVariant TagListModel::data(const QModelIndex &index, int role) const
{
    if (role == Qt::DecorationRole) {
        return QVariant(QPixmap (":/images/alus.png"));
    } else if (role == Qt::DisplayRole) {
        QVariant displayValue = QSqlQueryModel::data(index, Qt::DisplayRole);
        return displayValue.toString();
    } else {
        return QSqlQueryModel::data(index, role);
    }
}
