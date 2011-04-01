/****************************************************************************
** Meta object code from reading C++ file 'maincontroller.h'
**
** Created: Thu Mar 31 07:34:58 2011
**      by: The Qt Meta Object Compiler version 62 (Qt 4.6.3)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "maincontroller.h"
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'maincontroller.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 62
#error "This file was generated using the moc from 4.6.3. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
static const uint qt_meta_data_MainController[] = {

 // content:
       4,       // revision
       0,       // classname
       0,    0, // classinfo
      17,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

 // slots: signature, parameters, type, tag, flags
      16,   15,   15,   15, 0x08,
      39,   33,   15,   15, 0x08,
      55,   15,   15,   15, 0x08,
      71,   15,   15,   15, 0x08,
     100,   85,   15,   15, 0x08,
     141,   85,   15,   15, 0x08,
     191,  186,   15,   15, 0x08,
     222,  216,   15,   15, 0x08,
     239,   15,   15,   15, 0x08,
     250,   15,   15,   15, 0x08,
     271,   15,   15,   15, 0x08,
     280,  216,   15,   15, 0x08,
     316,  300,   15,   15, 0x08,
     350,   15,   15,   15, 0x08,
     373,   15,   15,   15, 0x08,
     403,  395,   15,   15, 0x08,
     430,   15,   15,   15, 0x08,

       0        // eod
};

static const char qt_meta_stringdata_MainController[] = {
    "MainController\0\0dbInitFinished()\0index\0"
    "showWidget(int)\0showBrandTabs()\0"
    "showCounter()\0type,id,header\0"
    "showPubList(PubListType,QString,QString)\0"
    "showBrandList(BrandListType,QString,QString)\0"
    "pubs\0showMap(QList<BeerPub*>)\0pubId\0"
    "showPub(QString)\0showMain()\0"
    "showFeelingThirsty()\0goBack()\0"
    "showPubMap(QString)\0geoPositionInfo\0"
    "positionUpdated(QGeoPositionInfo)\0"
    "startLocationUpdates()\0stopLocationUpdates()\0"
    "version\0onUpdateAvailable(QString)\0"
    "initDbUpdate()\0"
};

const QMetaObject MainController::staticMetaObject = {
    { &QMainWindow::staticMetaObject, qt_meta_stringdata_MainController,
      qt_meta_data_MainController, 0 }
};

#ifdef Q_NO_DATA_RELOCATION
const QMetaObject &MainController::getStaticMetaObject() { return staticMetaObject; }
#endif //Q_NO_DATA_RELOCATION

const QMetaObject *MainController::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->metaObject : &staticMetaObject;
}

void *MainController::qt_metacast(const char *_clname)
{
    if (!_clname) return 0;
    if (!strcmp(_clname, qt_meta_stringdata_MainController))
        return static_cast<void*>(const_cast< MainController*>(this));
    return QMainWindow::qt_metacast(_clname);
}

int MainController::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QMainWindow::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        switch (_id) {
        case 0: dbInitFinished(); break;
        case 1: showWidget((*reinterpret_cast< int(*)>(_a[1]))); break;
        case 2: showBrandTabs(); break;
        case 3: showCounter(); break;
        case 4: showPubList((*reinterpret_cast< PubListType(*)>(_a[1])),(*reinterpret_cast< QString(*)>(_a[2])),(*reinterpret_cast< QString(*)>(_a[3]))); break;
        case 5: showBrandList((*reinterpret_cast< BrandListType(*)>(_a[1])),(*reinterpret_cast< QString(*)>(_a[2])),(*reinterpret_cast< QString(*)>(_a[3]))); break;
        case 6: showMap((*reinterpret_cast< QList<BeerPub*>(*)>(_a[1]))); break;
        case 7: showPub((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 8: showMain(); break;
        case 9: showFeelingThirsty(); break;
        case 10: goBack(); break;
        case 11: showPubMap((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 12: positionUpdated((*reinterpret_cast< QGeoPositionInfo(*)>(_a[1]))); break;
        case 13: startLocationUpdates(); break;
        case 14: stopLocationUpdates(); break;
        case 15: onUpdateAvailable((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 16: initDbUpdate(); break;
        default: ;
        }
        _id -= 17;
    }
    return _id;
}
QT_END_MOC_NAMESPACE
