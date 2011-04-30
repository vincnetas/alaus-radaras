#ifndef SETTINGS_H
#define SETTINGS_H

#include <QDialog>
#include <QLocale>

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
    void changeEvent(QEvent* event);
signals:
    void LanguageChanged(QLocale::Language language);

private:
    Ui::Settings *ui;
    void retranslateUi();

private slots:
    void on_btnBack_clicked();
    void languageSelected(int index);


};

#endif // SETTINGS_H
