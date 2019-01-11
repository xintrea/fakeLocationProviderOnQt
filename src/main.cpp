#include <QGuiApplication>
#include <QQmlApplicationEngine>
#include <QQmlContext>

#include <QtGui>
#include <QtCore>

#include <QDebug>

#include "main.h"
#include "core/App.h"
#include "core/Core.h"


int main(int argc, char *argv[])
{
    App app(argc, argv);

    QQmlApplicationEngine engine;

    // Загрузка QMl-движка
    engine.load(QUrl(QStringLiteral("qrc:/qmlCode/main.qml")));
    if (engine.rootObjects().isEmpty())
        return -1;

    // Инициализация ядра приложения
    // Ядро зарегистрирует все необходимые классы в QML-движке
    // А так же оно зарегистрирует в QML-движке само себя
    app.core.init(&engine);

    return app.exec();
}

