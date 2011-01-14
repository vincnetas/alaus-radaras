#ifndef PUBVIEW_H
#define PUBVIEW_H

#include <QDialog>
#include <brandlistmodel.h>
namespace Ui {
    class PubView;
}

class PubView : public QDialog
{
    Q_OBJECT

public:
    explicit PubView(QWidget *parent = 0, QString pubId = "");
    ~PubView();

private:
    Ui::PubView *ui;
    BrandListModel* brandsModel;
private slots:
    void on_closeButton_clicked();
};

#endif // PUBVIEW_H
