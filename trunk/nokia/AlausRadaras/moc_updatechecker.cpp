/****************************************************************************
** Meta object code from reading C++ file 'updatechecker.h'
**
** Created: Thu Jan 27 22:38:28 2011
**      by: The Qt Meta Object Compiler version 62 (Qt 4.6.3)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "updatechecker.h"
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'updatechecker.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 62
#error "This file was generated using the moc from 4.6.3. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
static const uint qt_meta_data_UpdateChecker[] = {

 // content:
       4,       // revision
       0,       // classname
       0,    0, // classinfo
       3,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       1,       // signalCount

 // signals: signature, parameters, type, tag, flags
      20,   15,   14,   14, 0x05,

 // slots: signature, parameters, type, tag, flags
      44,   14,   14,   14, 0x0a,
      68,   62,   14,   14, 0x08,

       0        // eod
};

static const char qt_meta_stringdata_UpdateChecker[] = {
    "UpdateChecker\0\0text\0updateAvalable(QString)\0"
    "checkForUpdates()\0reply\0"
    "replyFinished(QNetworkReply*)\0"
};

const QMetaObject UpdateChecker::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_UpdateChecker,
      qt_meta_data_UpdateChecker, 0 }
};

#ifdef Q_NO_DATA_RELOCATION
const QMetaObject &UpdateChecker::getStaticMetaObject() { return staticMetaObject; }
#endif //Q_NO_DATA_RELOCATION

const QMetaObject *UpdateChecker::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->metaObject : &staticMetaObject;
}

void *UpdateChecker::qt_metacast(const char *_clname)
{
    if (!_clname) return 0;
    if (!strcmp(_clname, qt_meta_stringdata_UpdateChecker))
        return static_cast<void*>(const_cast< UpdateChecker*>(this));
    return QObject::qt_metacast(_clname);
}

int UpdateChecker::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        switch (_id) {
        case 0: updateAvalable((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 1: checkForUpdates(); break;
        case 2: replyFinished((*reinterpret_cast< QNetworkReply*(*)>(_a[1]))); break;
        default: ;
        }
        _id -= 3;
    }
    return _id;
}

// SIGNAL 0
void UpdateChecker::updateAvalable(QString _t1)
{
    void *_a[] = { 0, const_cast<void*>(reinterpret_cast<const void*>(&_t1)) };
    QMetaObject::activate(this, &staticMetaObject, 0, _a);
}
QT_END_MOC_NAMESPACE
