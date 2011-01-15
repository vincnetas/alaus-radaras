#ifndef WAITDIALOG_H
#define WAITDIALOG_H

#include <QDialog>
#include <QTimer>

namespace Ui {
    class WaitDialog;
}

class WaitDialog : public QDialog
{
    Q_OBJECT

public:
    explicit WaitDialog(QWidget *parent = 0);
    ~WaitDialog();

private:
    Ui::WaitDialog *ui;
    QTimer *timer;
private slots:
    void updateProgress();
};

#endif // WAITDIALOG_H
