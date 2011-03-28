#include "brandlistmodel.h"
#include <QSqlQueryModel>
#include <QFile>
#include <QPixmap>
#include <QStringBuilder>
#include <QDebug>
#include <QApplication>
#include <QDesktopWidget>
#include "viewutils.h"
BrandListModel::BrandListModel(QObject *parent) :
    QSqlQueryModel(parent)
{

}

QVariant BrandListModel::data(const QModelIndex &index, int role) const
{

    if (role == Qt::DecorationRole) {
            QVariant displayValue = QSqlQueryModel::data(index, Qt::DisplayRole);
            QString iconId = displayValue.toString();
            QFile f( ":/brands" % ViewUtils::IconRes %"/brand_" % iconId % ".png");
            QPixmap p;
            if(f.exists()) {
               p = QPixmap (f.fileName());
            } else {
               p = QPixmap (":/brands" %ViewUtils::IconRes %"/brand_default.png");
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
BrandListModel::~BrandListModel()
{
 //qDebug() << "Destroying BrandListModel ";

}
