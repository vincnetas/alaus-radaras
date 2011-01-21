#ifndef PUBVIEW_H
#define PUBVIEW_H

#include <QDialog>
#include <brandlistmodel.h>
#include <qskineticscroller.h>
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

private:
    Ui::PubView *ui;
    BrandListModel* brandsModel;
    QsKineticScroller* brandListScroller;
private slots:
    void on_closeButton_clicked();
signals:
    void Back();
};

#endif // PUBVIEW_H
