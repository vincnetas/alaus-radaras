#include "pubview.h"
#include "ui_pubview.h"
#include <QSqlQuery>
#include "beerlistmodel.h"
#include <QDebug>
#include <QSqlError>
#include "viewutils.h"
#include "enums.h"
#include "qtscroller.h"
#include <QKeyEvent>

#include <QDesktopServices>
#include <QUrl>
PubView::PubView(QWidget *parent) :
    QWidget(parent),
    beersModel(0),
    ui(new Ui::PubView)
{
    ui->setupUi(this);

    setAutoFillBackground(true);
    setPalette(ViewUtils::GetBackground(palette()));

     ui->beerListView->setAutoFillBackground(true);
     ui->beerListView->setPalette(ViewUtils::GetBackground(ui->beerListView->palette()));

     QtScroller::grabGesture(ui->beerListView->viewport(), QtScroller::QtScroller::LeftMouseButtonGesture);

     //until we don't have GPS
     ui->directionsButton->setVisible(false);

     ui->mapButton->setStyleSheet("#mapButton{ border-image:url(:/images" + ViewUtils::IconRes+ "/map_01.png); }");

     QFont fontHeader;
     fontHeader.setPointSize(ViewUtils::HighRes ? 15 : 10);
     ui->pubNameLabel->setFont(fontHeader);

     QFont fontLabel;
     fontLabel.setPointSize(ViewUtils::HighRes ? 11 : 8);
     ui->pubPhoneLabel->setFont(fontLabel);
     ui->pubAddressLabel->setFont(fontLabel);

}

void PubView::showPub(QString pubId)
{

    QSqlQuery query(QString("SELECT id, title, address  || ', ' || city, phone, longtitude, latitude, city from pubs where id='%1'").arg(pubId));
    while (query.next()) {
        ui->pubPhoneLabel->setText(query.value(3).toString());
        ui->pubAddressLabel->setText(query.value(2).toString());
        ui->pubNameLabel->setText(query.value(1).toString());
        this->id = query.value(0).toString();
        this->lat = query.value(4).toString();
        this->lng = query.value(5).toString();
    }
    query.clear();
    if(!beersModel) {
        beersModel = new BeerListModel();
    }

    beersModel->setQuery(QString("SELECT b.icon, b.title, b.id FROM beers b INNER JOIN pub_beers pb ON b.id = pb.beer_id AND pb.pub_id = '%1'").arg(pubId));

    ui->beerListView->setModel(beersModel);


}

void PubView::on_directionsButton_clicked()
{
    QDesktopServices::openUrl(QUrl(QString("http://maps.google.com/maps?q=%1,%2").arg(this->lat,this->lng)));
}

void PubView::on_mapButton_clicked()
{
     emit PubMapSelected(this->id);
}

void PubView::on_closeButton_clicked()
{
    emit Back();
}

void PubView::keyPressEvent(QKeyEvent* event)
{
    if(event->nativeVirtualKey() == CancelKey) {
        on_closeButton_clicked();
    }
    QWidget::keyPressEvent(event);
}

void PubView::changeEvent(QEvent* event)
{
    if (event->type() == QEvent::LanguageChange)
    {
        ui->retranslateUi(this);
    }
    QWidget::changeEvent(event);
}

PubView::~PubView()
{
    //qDebug() << "pubview destructor called";
    delete ui;
}
