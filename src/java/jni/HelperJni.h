#ifndef HELPERJNI_H
#define HELPERJNI_H

#include <QObject>
#include <QDebug>

#ifdef Q_OS_ANDROID
#include <QtAndroidExtras/QAndroidJniObject>
#endif

class HelperJni: public QObject
{
    Q_OBJECT

public:
    explicit HelperJni(QObject* parent = nullptr);

    // Примеры кода
    int vibro();

    // Вызов отправки email из Java-кода
    void sendMail(QString to, QString subj, QString text);
};

#endif // HELPERJNI_H
