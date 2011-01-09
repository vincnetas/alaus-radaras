#-------------------------------------------------
#
# Project created by QtCreator 2011-01-02T21:53:54
#
#-------------------------------------------------

QT       += core gui
QT       += sql
QT      += thread
TARGET = AlausRadaras
TEMPLATE = app


SOURCES += main.cpp\
        alausradaras.cpp \
    dbmanager.cpp \
    test.cpp \
    brandtabs.cpp \
    brandlistmodel.cpp

HEADERS  += alausradaras.h \
    dbmanager.h \
    test.h \
    brandtabs.h \
    brandlistmodel.h

FORMS    += alausradaras.ui \
    brandtabs.ui

CONFIG += mobility
MOBILITY = 

symbian {
    TARGET.UID3 = 0xece1c5e8
    # TARGET.CAPABILITY += 
    TARGET.EPOCSTACKSIZE = 0x14000
    TARGET.EPOCHEAPSIZE = 0x020000 0x800000
}

RESOURCES += \
    Assets.qrc \
    Brands.qrc
