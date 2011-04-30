#-------------------------------------------------
#
# Project created by QtCreator 2011-01-02T21:53:54
#
#-------------------------------------------------

QT       += core gui sql network
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
    calculationhelper.cpp \
    slippymap.cpp \
    lightmaps.cpp \
    dataprovider.cpp \
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
qtflickgesture.cpp \
           qtscroller.cpp \
           qtscrollerfilter.cpp \
           qtscrollerproperties.cpp \
           qtscrollevent.cpp \
    settings.cpp \
    feelingthirsty.cpp \
    appupdatechecker.cpp \
    dbupdatedownloader.cpp \
    json.cpp

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
    calculationhelper.h \
    slippymap.h \
    lightmaps.h \
    dataprovider.h \
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
    qtflickgesture_p.h \
           qtscroller.h \
           qtscroller_p.h \
           qtscrollerfilter_p.h \
           qtscrollerproperties.h \
           qtscrollerproperties_p.h \
           qtscrollevent.h \
           qtscrollevent_p.h \
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


#LIBS += -lsendas2 \
#-lmsgs \
#-letext \
#-lefsrv \
#-lcharconv \
#-lgsmu\
#-etel3rdparty.lib\
#-e32base.lib\
#-euser.lib

#MMP_RULES += "LIBRARY etel3rdparty.lib"


#INCLUDEPATH += "C:/projects/alausradaras/nokia/AlausRadaras/kineticscroller"
ICON = alus.svg
#QTSCROLLER_OUT = $$OUT_PWD/kineticscroller
#include(kineticscroller/qtscroller.pri)
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
