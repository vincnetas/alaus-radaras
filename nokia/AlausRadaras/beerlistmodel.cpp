#include "beerlistmodel.h"
#include <QSqlQueryModel>
#include <QFile>
#include <QPixmap>
#include <QStringBuilder>
#include <QDebug>
#include <QApplication>
#include <QDesktopWidget>
#include "viewutils.h"
BeerListModel::BeerListModel(QObject *parent) :
    QSqlQueryModel(parent)
{

}

QVariant BeerListModel::data(const QModelIndex &index, int role) const
{

    if (role == Qt::DecorationRole) {
            QVariant displayValue = QSqlQueryModel::data(index, Qt::DisplayRole);
            QString iconId = displayValue.toString();
            QFile f( ":/beers" % ViewUtils::IconRes %"/beer_" % iconId % ".png");
            QPixmap p;
            if(f.exists()) {
               p = QPixmap (f.fileName());
            } else {
               p = QPixmap (":/beers" %ViewUtils::IconRes %"/beer_default.png");
            }
            return QVariant(p);
        } else if (role == Qt::DisplayRole) {
            QModelIndex textIndex = QSqlQueryModel::index(index.row(), 1);
            QVariant displayValue = QSqlQueryModel::data(textIndex, Qt::DisplayRole);
            return displayValue.toString();
        } else if (role == Qt::EditRole) {
            //abusing QT ... how cool is that! ;-)
            QModelIndex textIndex = QSqlQueryModel::index(index.row(), 2);
            QVariant displayValue = QSqlQueryModel::data(textIndex, Qt::DisplayRole);
            return displayValue.toString();
        } else {
            return QSqlQueryModel::data(index, role);
        }
}
BeerListModel::~BeerListModel()
{
 //qDebug() << "Destroying BeerListModel ";

}
