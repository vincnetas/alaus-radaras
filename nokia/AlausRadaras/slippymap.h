#ifndef SLIPPYMAP_H
#define SLIPPYMAP_H

#include <QObject>
#include <QtGui>
#include <QtNetwork>
class SlippyMap : public QObject
{
    Q_OBJECT
public:
    explicit SlippyMap(QObject *parent = 0);
    int width;
    int height;
    int zoom;
    qreal latitude;
    qreal longitude;
    void render(QPainter *p, const QRect &rect);
    void invalidate();
    void pan(const QPoint &delta);
signals:

public slots:

private:
    QPoint m_offset;
    QRect m_tilesRect;
    QPixmap m_emptyTile;
    QHash<QPoint, QPixmap> m_tilePixmaps;
    QNetworkAccessManager m_manager;
    QUrl m_url;
private slots:
    void handleNetworkData(QNetworkReply *reply);
    void download();
signals:
    void updated(const QRect &rect);
protected:
    QRect tileRect(const QPoint &tp);

};

#endif // SLIPPYMAP_H
