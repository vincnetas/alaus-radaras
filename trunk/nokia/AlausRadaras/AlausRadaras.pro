#-------------------------------------------------
#
# Project created by QtCreator 2011-01-02T21:53:54
#
#-------------------------------------------------

QT       += core gui
QT       += sql
QT       += thread
QT       += webkit
TARGET = AlausRadaras
TEMPLATE = app


SOURCES += main.cpp\
        alausradaras.cpp \
    dbmanager.cpp \
    brandlistmodel.cpp \
    countrylistmodel.cpp \
    brandtabs.cpp \
    taglistmodel.cpp \
    beermap.cpp

HEADERS  += alausradaras.h \
    dbmanager.h \
    test.h \
    brandlistmodel.h \
    countrylistmodel.h \
    brandtabs.h \
    taglistmodel.h \
    beermap.h

FORMS    += alausradaras.ui \
    brandtabs.ui \
    brandtabs.ui \
    beermap.ui

CONFIG += mobility
MOBILITY = 

symbian {
    TARGET.UID3 = 0xece1c5e8
    TARGET.CAPABILITY += NetworkServices
    TARGET.EPOCSTACKSIZE = 0x14000
    TARGET.EPOCHEAPSIZE = 0x020000 0x800000
}

RESOURCES += \
    Assets.qrc \
    Brands.qrc \
    Images.qrc \
    Web.qrc
