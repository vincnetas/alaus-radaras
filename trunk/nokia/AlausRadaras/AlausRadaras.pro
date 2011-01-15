#-------------------------------------------------
#
# Project created by QtCreator 2011-01-02T21:53:54
#
#-------------------------------------------------

QT       += core gui
QT       += sql
QT       += network
TARGET = AlausRadaras
TEMPLATE = app


SOURCES += main.cpp\
        alausradaras.cpp \
    dbmanager.cpp \
    brandlistmodel.cpp \
    countrylistmodel.cpp \
    brandtabs.cpp \
    taglistmodel.cpp \
    pubview.cpp \
    beerpub.cpp \
    publist.cpp \
    publistmodel.cpp \
    flickcharm.cpp \
    qskineticscroller.cpp \
    calculationhelper.cpp \
    slippymap.cpp \
    lightmaps.cpp \
    dataprovider.cpp \
    feelinglucky.cpp \
    brandlist.cpp \
    feelingluckyinfo.cpp \
    beermap.cpp

HEADERS  += alausradaras.h \
    dbmanager.h \
    brandlistmodel.h \
    countrylistmodel.h \
    brandtabs.h \
    taglistmodel.h \
    pubview.h \
    beerpub.h \
    publist.h \
    publistmodel.h \
    flickcharm.h \
    qskineticscroller.h \
    calculationhelper.h \
    slippymap.h \
    lightmaps.h \
    dataprovider.h \
    feelinglucky.h \
    brandlist.h \
    feelingluckyinfo.h \
    beermap.h

FORMS    += alausradaras.ui \
    brandtabs.ui \
    brandtabs.ui \
    pubview.ui \
    publist.ui \
    feelinglucky.ui \
    brandlist.ui \
    beermap.ui

CONFIG += mobility debug
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
    Images.qrc
