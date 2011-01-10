#include "countrylistmodel.h"
#include <QSqlQueryModel>
#include <QPixmap>
#include <QStringBuilder>

CountryListModel::CountryListModel(QObject *parent) :
    QSqlQueryModel(parent)
{
}

QVariant CountryListModel::data(const QModelIndex &index, int role) const
{

    if (role == Qt::DecorationRole) {
           return QVariant(QPixmap (":/images/map_01.png"));
        } else if (role == Qt::DisplayRole) {
            QVariant displayValue = QSqlQueryModel::data(index, Qt::DisplayRole);
            return displayValue.toString();
        } else {
            return QSqlQueryModel::data(index, role);
        }
}
