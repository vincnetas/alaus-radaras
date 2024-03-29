#ifndef PUBVIEW_H
#define PUBVIEW_H

#include <QDialog>
#include <beerlistmodel.h>
namespace Ui {
    class PubView;
}

class PubView : public QWidget
{
    Q_OBJECT

public:
    explicit PubView(QWidget *parent = 0);
    ~PubView();
    void showPub(QString pubId);
    void keyPressEvent(QKeyEvent* event);
    void changeEvent(QEvent* event);

private:
    Ui::PubView *ui;
    BeerListModel* beersModel;
    QString id;
    QString lat;
    QString lng;
private slots:
    void on_closeButton_clicked();
    void on_mapButton_clicked();
    void on_directionsButton_clicked();
signals:
    void Back();
    void PubMapSelected(QString pubId);
    void ChangeLanguage(QString language);
};

#endif // PUBVIEW_H
