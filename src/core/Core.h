#ifndef APPCORE_H
#define APPCORE_H

#include <QObject>
#include <QTimer>
#include <QTranslator>
#include <QQmlApplicationEngine>
#include <QScreen>

#ifdef Q_OS_ANDROID
#include "helpers/KeepAwakeHelper.h"
#endif

class Core : public QObject
{
    Q_OBJECT

public:
    explicit Core(QObject *parent = 0);

    Q_INVOKABLE void setupFakeLocationProvider();
    Q_INVOKABLE void updateFakeLocationProvider();

    void init(QQmlApplicationEngine *iEngine);

signals:

    void activation();
    void deactivation();

public slots:

    void appActivation();
    void appDeactivation();

    void updateByTimer();

protected:

    void registryQmlComponents(QQmlApplicationEngine *iEngine);

    QTimer *timer;

    #ifdef Q_OS_ANDROID
    KeepAwakeHelper keepAwakeHelper;
    #endif
};

#endif // APPCORE_H
