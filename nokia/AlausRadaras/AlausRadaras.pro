#-------------------------------------------------
#
# Project created by QtCreator 2011-01-02T21:53:54
#
#-------------------------------------------------

QT       += core gui sql network
TARGET = AlausRadaras
TEMPLATE = app
DEPENDPATH += . DbManager QtScroller
INCLUDEPATH += . DbManager QtScroller

include(DbManager/DbManager.pri)
include(QtScroller/QtScroller.pri)

SOURCES += main.cpp\
        alausradaras.cpp \
    brandlistmodel.cpp \
    countrylistmodel.cpp \
    brandtabs.cpp \
    taglistmodel.cpp \
    pubview.cpp \
    beerpub.cpp \
    publist.cpp \
    publistmodel.cpp \
    calculationhelper.cpp \
    slippymap.cpp \
    lightmaps.cpp \
    brandlist.cpp \
    feelingluckyinfo.cpp \
    beermap.cpp \
    dbpopulator.cpp \
    waitdialog.cpp \
    beercounter.cpp \
    viewutils.cpp \
    maincontroller.cpp \
    makecall.cpp \
    baseupdatedownloader.cpp \
    settings.cpp \
    feelingthirsty.cpp \
    appupdatechecker.cpp \
    dbupdatedownloader.cpp \
    json.cpp

HEADERS  += alausradaras.h \
    brandlistmodel.h \
    countrylistmodel.h \
    brandtabs.h \
    taglistmodel.h \
    pubview.h \
    beerpub.h \
    publist.h \
    publistmodel.h \
    calculationhelper.h \
    slippymap.h \
    lightmaps.h \
    brandlist.h \
    feelingluckyinfo.h \
    beermap.h \
    dbpopulator.h \
    waitdialog.h \
    beercounter.h \
    viewutils.h \
    maincontroller.h \
    makecall.h \
    baseupdatedownloader.h \
    settings.h \
    feelingthirsty.h \
    appupdatechecker.h \
    dbupdatedownloader.h \
    json.h 

FORMS    += alausradaras.ui \
    brandtabs.ui \
    brandtabs.ui \
    pubview.ui \
    publist.ui \
    brandlist.ui \
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
    Brands.qrc \
    Images.qrc

debug {
    DEFINES += DEBUG
}
else {
    DEFINES += NDEBUG QT_NO_DEBUG_OUTPUT QT_NO_WARNING_OUTPUT
}
