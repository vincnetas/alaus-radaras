#-------------------------------------------------
#
# Project created by QtCreator 2011-01-02T21:53:54
#
#-------------------------------------------------

QT       += core gui sql network
TARGET = AlausRadaras
TEMPLATE = app
DEPENDPATH += . DbManager QtScroller Updater
INCLUDEPATH += . DbManager QtScroller Updater

include(DbManager/DbManager.pri)
include(QtScroller/QtScroller.pri)
include(Updater/Updater.pri)

SOURCES += main.cpp\
        alausradaras.cpp \
    beerlistmodel.cpp \
    countrylistmodel.cpp \
    beertabs.cpp \
    taglistmodel.cpp \
    pubview.cpp \
    beerpub.cpp \
    publist.cpp \
    publistmodel.cpp \
    calculationhelper.cpp \
    slippymap.cpp \
    lightmaps.cpp \
    beerlist.cpp \
    feelingluckyinfo.cpp \
    beermap.cpp \
    waitdialog.cpp \
    beercounter.cpp \
    viewutils.cpp \
    maincontroller.cpp \
    makecall.cpp \
    settings.cpp \
    feelingthirsty.cpp

HEADERS  += alausradaras.h \
    beerlistmodel.h \
    countrylistmodel.h \
    beertabs.h \
    taglistmodel.h \
    pubview.h \
    beerpub.h \
    publist.h \
    publistmodel.h \
    calculationhelper.h \
    slippymap.h \
    lightmaps.h \
    beerlist.h \
    feelingluckyinfo.h \
    beermap.h \
    waitdialog.h \
    beercounter.h \
    viewutils.h \
    maincontroller.h \
    makecall.h \
    settings.h \
    feelingthirsty.h 

FORMS    += alausradaras.ui \
    beertabs.ui \
    beertabs.ui \
    pubview.ui \
    publist.ui \
    beerlist.ui \
    beermap.ui \
    waitdialog.ui \
    beercounter.ui \
    maincontroller.ui \
    settings.ui \
    feelingthirsty.ui

CONFIG += mobility
MOBILITY += location

TRANSLATIONS = alausradaras_lt.ts \
    alausradaras_ru.ts \
    alausradaras_en.ts

ICON = alus.svg
 VERSION = 1.0.2
#old uid 0xece1c5e8
#nokia uid 0x2003986A
symbian {
    TARGET.UID3 = 0x2003986A
    TARGET.CAPABILITY += NetworkServices Location
    TARGET.EPOCSTACKSIZE = 0x14000
    TARGET.EPOCHEAPSIZE = 0x020000 0x800000
    ICON = alus.svg
     VERSION = 1.0.2
vendorinfo = \
"%{\"X-Medikai\"}" \
":\"X-Medikai\""

translations.sources = *.qm
DEPLOYMENT += translations

packageheader = "$${LITERAL_HASH}{\"Alaus Radaras\"}, ($${TARGET.UID3}), 1, 0, 2, TYPE=SA"
my_deployment.pkg_prerules = packageheader vendorinfo
DEPLOYMENT += my_deployment
}

RESOURCES += \
    Assets.qrc \
    Beers.qrc \
    Images.qrc

debug {
    DEFINES += DEBUG
}
else {
    DEFINES += NDEBUG QT_NO_DEBUG_OUTPUT QT_NO_WARNING_OUTPUT
}
