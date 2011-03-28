#include "taglistmodel.h"
#include <QSqlQueryModel>
#include <QPixmap>
#include <QStringBuilder>
#include <viewutils.h>
TagListModel::TagListModel(QObject *parent) :
        QSqlQueryModel(parent)
    {
    }

QVariant TagListModel::data(const QModelIndex &index, int role) const
{
    if (role == Qt::DecorationRole) {
        return QVariant(QPixmap (":/images" %ViewUtils::IconRes %"/alus.png"));
    } else if (role == Qt::DisplayRole) {
        QVariant displayValue = QSqlQueryModel::data(index, Qt::DisplayRole);
        return displayValue.toString();
    } else if (role == Qt::EditRole) {
        QModelIndex textIndex = QSqlQueryModel::index(index.row(), 1);
        QVariant displayValue = QSqlQueryModel::data(textIndex, Qt::DisplayRole);
        return displayValue.toString();
    } else {
        return QSqlQueryModel::data(index, role);
    }
}
