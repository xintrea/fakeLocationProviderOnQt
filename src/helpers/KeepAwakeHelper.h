#ifndef KEEPAWAKEHELPER_H
#define KEEPAWAKEHELPER_H

#include <QAndroidJniObject>

// Решение взято со StackOwerflow
// https://stackoverflow.com/questions/34179653/how-do-i-prevent-an-android-device-from-going-to-sleep-from-qt-application


class KeepAwakeHelper
{
public:
    KeepAwakeHelper();
    virtual ~KeepAwakeHelper();

    void on();
    void off();

private:
    QAndroidJniObject mWakeLock;

    bool mWakeLockState;
};

#endif // KEEPAWAKEHELPER_H
