/****************************************************************************
** Meta object code from reading C++ file 'feelingthirsty.h'
**
** Created: Thu Mar 31 07:35:06 2011
**      by: The Qt Meta Object Compiler version 62 (Qt 4.6.3)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "feelingthirsty.h"
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'feelingthirsty.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 62
#error "This file was generated using the moc from 4.6.3. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
static const uint qt_meta_data_FeelingThirsty[] = {

 // content:
       4,       // revision
       0,       // classname
       0,    0, // classinfo
       6,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       3,       // signalCount

 // signals: signature, parameters, type, tag, flags
      16,   15,   15,   15, 0x05,
      29,   23,   15,   15, 0x05,
      65,   50,   15,   15, 0x05,

 // slots: signature, parameters, type, tag, flags
     110,   15,   15,   15, 0x08,
     131,   15,   15,   15, 0x08,
     151,   15,   15,   15, 0x08,

       0        // eod
};

static const char qt_meta_stringdata_FeelingThirsty[] = {
    "FeelingThirsty\0\0Back()\0pubId\0"
    "PubSelected(QString)\0type,id,header\0"
    "PubListSelected(PubListType,QString,QString)\0"
    "on_btnBeer_clicked()\0on_btnPub_clicked()\0"
    "on_btnBack_clicked()\0"
};

const QMetaObject FeelingThirsty::staticMetaObject = {
    { &QWidget::staticMetaObject, qt_meta_stringdata_FeelingThirsty,
      qt_meta_data_FeelingThirsty, 0 }
};

#ifdef Q_NO_DATA_RELOCATION
const QMetaObject &FeelingThirsty::getStaticMetaObject() { return staticMetaObject; }
#endif //Q_NO_DATA_RELOCATION

const QMetaObject *FeelingThirsty::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->metaObject : &staticMetaObject;
}

void *FeelingThirsty::qt_metacast(const char *_clname)
{
    if (!_clname) return 0;
    if (!strcmp(_clname, qt_meta_stringdata_FeelingThirsty))
        return static_cast<void*>(const_cast< FeelingThirsty*>(this));
    return QWidget::qt_metacast(_clname);
}

int FeelingThirsty::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QWidget::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        switch (_id) {
        case 0: Back(); break;
        case 1: PubSelected((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 2: PubListSelected((*reinterpret_cast< PubListType(*)>(_a[1])),(*reinterpret_cast< QString(*)>(_a[2])),(*reinterpret_cast< QString(*)>(_a[3]))); break;
        case 3: on_btnBeer_clicked(); break;
        case 4: on_btnPub_clicked(); break;
        case 5: on_btnBack_clicked(); break;
        default: ;
        }
        _id -= 6;
    }
    return _id;
}

// SIGNAL 0
void FeelingThirsty::Back()
{
    QMetaObject::activate(this, &staticMetaObject, 0, 0);
}

// SIGNAL 1
void FeelingThirsty::PubSelected(QString _t1)
{
    void *_a[] = { 0, const_cast<void*>(reinterpret_cast<const void*>(&_t1)) };
    QMetaObject::activate(this, &staticMetaObject, 1, _a);
}

// SIGNAL 2
void FeelingThirsty::PubListSelected(PubListType _t1, QString _t2, QString _t3)
{
    void *_a[] = { 0, const_cast<void*>(reinterpret_cast<const void*>(&_t1)), const_cast<void*>(reinterpret_cast<const void*>(&_t2)), const_cast<void*>(reinterpret_cast<const void*>(&_t3)) };
    QMetaObject::activate(this, &staticMetaObject, 2, _a);
}
QT_END_MOC_NAMESPACE
