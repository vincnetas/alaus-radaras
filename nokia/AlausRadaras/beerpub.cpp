#include "beerpub.h"

BeerPub::BeerPub(QObject *parent) :
    QObject(parent)
{
}
QString BeerPub::id() const
{
    return m_id;
}

void BeerPub::setId(const QString &id)
{
    m_id = id;
}

QString BeerPub::title() const
{
    return m_title;
}

void BeerPub::setTitle(const QString &title)
{
    m_title = title;
}

qreal BeerPub::latitude() const
{
    return m_latitude;
}

void BeerPub::setLatitude(const qreal &lat)
{
    m_latitude = lat;
}

qreal BeerPub::longitude() const
{
    return m_longitude;
}

void BeerPub::setLongitude(const qreal &longt)
{
    m_longitude = longt;
}

qreal BeerPub::distance() const
{
    return m_distance;
}

void BeerPub::setDistance(const qreal &distance)
{
    m_distance = distance;
}

