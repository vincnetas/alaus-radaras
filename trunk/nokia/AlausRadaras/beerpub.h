#ifndef BEERPUB_H
#define BEERPUB_H

#include <QObject>

class BeerPub : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString id READ id WRITE setId)
    Q_PROPERTY(QString title READ title WRITE setTitle)
    Q_PROPERTY(qreal latitude READ latitude WRITE setLatitude)
    Q_PROPERTY(qreal longitude READ longitude WRITE setLongitude)
    Q_PROPERTY(qreal distance READ distance WRITE setDistance)
public:
    explicit BeerPub(QObject *parent = 0);

    QString id() const;
    void setId(const QString &);

    QString title() const;
    void setTitle(const QString &);

    qreal latitude() const;
    void setLatitude(const qreal &);

    qreal longitude() const;
    void setLongitude(const qreal &);

    qreal distance() const;
    void setDistance(const qreal &);

signals:

public slots:
private:
     QString m_id;
    QString m_title;
    qreal m_longitude;
    qreal m_latitude;
    qreal m_distance;

};

#endif // BEERPUB_H
