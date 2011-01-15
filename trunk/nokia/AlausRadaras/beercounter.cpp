#include "beercounter.h"
#include "ui_beercounter.h"

BeerCounter::BeerCounter(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::BeerCounter)
{
    ui->setupUi(this);
}

BeerCounter::~BeerCounter()
{
    delete ui;
}
