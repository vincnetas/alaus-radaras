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
    qskineticscroller.cpp \
    calculationhelper.cpp \
    slippymap.cpp \
    lightmaps.cpp \
    dataprovider.cpp \
    feelinglucky.cpp \
    brandlist.cpp \
    feelingluckyinfo.cpp \
    beermap.cpp \
    dbpopulator.cpp \
    waitdialog.cpp \
    beercounter.cpp \
    viewutils.cpp \
    maincontroller.cpp \
    makecall.cpp

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
    qskineticscroller.h \
    calculationhelper.h \
    slippymap.h \
    lightmaps.h \
    dataprovider.h \
    feelinglucky.h \
    brandlist.h \
    feelingluckyinfo.h \
    beermap.h \
    dbpopulator.h \
    waitdialog.h \
    beercounter.h \
    viewutils.h \
    maincontroller.h \
    makecall.h

FORMS    += alausradaras.ui \
    brandtabs.ui \
    brandtabs.ui \
    pubview.ui \
    publist.ui \
    feelinglucky.ui \
    brandlist.ui \
    beermap.ui \
    waitdialog.ui \
    beercounter.ui \
    maincontroller.ui

CONFIG += mobility
MOBILITY += location

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

ICON = alus.svg

symbian {
    TARGET.UID3 = 0xece1c5e8
    TARGET.CAPABILITY += NetworkServices Location
    TARGET.EPOCSTACKSIZE = 0x14000
    TARGET.EPOCHEAPSIZE = 0x020000 0x800000
    ICON = alus.svg
vendorinfo = \
"%{\"alausradaras.lt\"}" \
":\"alausradaras.lt\""

my_deployment.pkg_prerules = packageheader vendorinfo

packageheader = "$${LITERAL_HASH}{\"Alaus Radaras\"}, ($${TARGET.UID3}), 1, 0, 0, TYPE=SA"
my_deployment.pkg_prerules = packageheader vendorinfo
DEPLOYMENT += my_deployment
}

RESOURCES += \
    Assets.qrc \
    Brands.qrc \
    Images.qrc
