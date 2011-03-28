#ifndef SETTINGS_H
#define SETTINGS_H

#include <QDialog>

namespace Ui {
    class Settings;
}

class Settings : public QDialog
{
    Q_OBJECT

public:
    explicit Settings(QWidget *parent = 0);
    ~Settings();
    void keyPressEvent(QKeyEvent* event);

private:
    Ui::Settings *ui;

private slots:
    void on_btnBack_clicked();
};

#endif // SETTINGS_H
