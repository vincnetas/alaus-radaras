/****************************************************************************
** Meta object code from reading C++ file 'qtscrollerfilter_p.h'
**
** Created: Thu Mar 31 07:35:03 2011
**      by: The Qt Meta Object Compiler version 62 (Qt 4.6.3)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "qtscrollerfilter_p.h"
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'qtscrollerfilter_p.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 62
#error "This file was generated using the moc from 4.6.3. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
static const uint qt_meta_data_QtScrollerFilter[] = {

 // content:
       4,       // revision
       0,       // classname
       0,    0, // classinfo
       1,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

 // slots: signature, parameters, type, tag, flags
      24,   18,   17,   17, 0x09,

       0        // eod
};

static const char qt_meta_stringdata_QtScrollerFilter[] = {
    "QtScrollerFilter\0\0state\0"
    "stateChanged(QtScroller::State)\0"
};

const QMetaObject QtScrollerFilter::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_QtScrollerFilter,
      qt_meta_data_QtScrollerFilter, 0 }
};

#ifdef Q_NO_DATA_RELOCATION
const QMetaObject &QtScrollerFilter::getStaticMetaObject() { return staticMetaObject; }
#endif //Q_NO_DATA_RELOCATION

const QMetaObject *QtScrollerFilter::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->metaObject : &staticMetaObject;
}

void *QtScrollerFilter::qt_metacast(const char *_clname)
{
    if (!_clname) return 0;
    if (!strcmp(_clname, qt_meta_stringdata_QtScrollerFilter))
        return static_cast<void*>(const_cast< QtScrollerFilter*>(this));
    return QObject::qt_metacast(_clname);
}

int QtScrollerFilter::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        switch (_id) {
        case 0: stateChanged((*reinterpret_cast< QtScroller::State(*)>(_a[1]))); break;
        default: ;
        }
        _id -= 1;
    }
    return _id;
}
QT_END_MOC_NAMESPACE
