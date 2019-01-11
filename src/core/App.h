#ifndef APP_H
#define APP_H

#include <QApplication>
#include <QObject>
#include "Core.h"

// Переопределенный класс приложения с дополнительным объектом ядра


class App : public QApplication
{
    Q_OBJECT

public:
    App(int &argc, char **argv);

    Core core;

    int exec();

    bool isRunning();

protected:
    bool isRunningFlag;

};

#endif // APP_H
