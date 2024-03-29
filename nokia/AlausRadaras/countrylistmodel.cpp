#include "countrylistmodel.h"
#include <QSqlQueryModel>
#include <QPixmap>
#include <QStringBuilder>
#include <QStringBuilder>
#include <viewutils.h>
#include "translator.h"

CountryListModel::CountryListModel(QObject *parent) :
    QSqlQueryModel(parent)
{
}

QVariant CountryListModel::data(const QModelIndex &index, int role) const
{

    if (role == Qt::DecorationRole) {
           return QVariant(QPixmap (":/images" %ViewUtils::IconRes %"/map_01.png"));
        } else if (role == Qt::DisplayRole) {
            QVariant displayValue = QSqlQueryModel::data(index, Qt::DisplayRole);
            return Translator::tr(displayValue.toString().toAscii().data(),"country");
        } else if (role == Qt::EditRole) {
            QModelIndex textIndex = QSqlQueryModel::index(index.row(), 0);
            QVariant displayValue = QSqlQueryModel::data(textIndex, Qt::DisplayRole);
            return displayValue.toString();
        } else {
            return QSqlQueryModel::data(index, role);
        }
}
