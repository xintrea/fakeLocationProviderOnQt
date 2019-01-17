#include <QtGlobal>
#include <QDateTime>
#include <QGuiApplication>
#include <QQmlContext>
#include <QDebug>

#include "main.h"
#include "Core.h"

#ifdef Q_OS_ANDROID
#include "java/jni/ru_farwater_gnss_fakelocsample_NativeHelper.h" // Вызов C++ метода из Java
#include "java/jni/HelperJni.h" // Вызов Java методов из C++
#endif


bool isApplicationCoreCreate=false;


Core::Core(QObject *parent) : QObject(parent)
{
    // jniEventActivity=new QAndroidJniObject("ru/farwater/gnss/fakelocsample/JniEventActivity");

    isApplicationCoreCreate=true;
}


void Core::init(QQmlApplicationEngine *iEngine)
{
    // Регистрация C++ классов как QML-компонентов
    registryQmlComponents(iEngine);

    // Действия при активации приложения
    appActivation();
}


void Core::registryQmlComponents(QQmlApplicationEngine *iEngine)
{
    // Указатель на корневой QML-контекст
    QQmlContext *context = iEngine->rootContext();

    // Регистрация ядра приложения в QML
    context->setContextProperty("core", this);
}


// Метод, срабатывающий при активации программы
// Для десктопа это момент, когда стартует программа
// Для Android это момент, когда программа на Java уровне проходит состояние onStart()
void Core::appActivation()
{
    qWarning() << "On activation actions";

    #ifdef Q_OS_ANDROID
    keepAwakeHelper.on();
    #endif

    emit activation();
}


// Слот, срабатывающий при деактивации программы
// Для десктопа это момент, когда операционка уведомляет программу что будет произведено закрытие
// Для Android это момент, когда программа помещается в фон (и может быть дальше зарыта,
// если пользователь "смахнет" окно программы в списке окон, или продолжит работу если
// пользователь снова выберет программу в списке или переключится на нее после временного переключения
// на другую программу)
void Core::appDeactivation()
{
    qWarning() << "On deactivation actions";

    #ifdef Q_OS_ANDROID
    keepAwakeHelper.off();
    #endif

    emit deactivation();
}


// Отслеживание активации и деакцивации приложения для ОС Android
#ifdef Q_OS_ANDROID
JNIEXPORT void JNICALL Java_ru_farwater_gnss_fakelocsample_NativeHelper_invokeVoidMethod(JNIEnv *jEnv, jclass jClazz, jint jInt)
{
    Q_UNUSED(jEnv);
    Q_UNUSED(jClazz);

    // Блокировка действий, если еще не сработал конструктор ядра
    if(isApplicationCoreCreate==false) {
        return;
    }

    int arg = static_cast<int>(jInt);
    qWarning() << "Hello from Java => C++! Arg is: " << arg << " (Function)";

    if(arg==100) {
        APPCORE.appActivation();
    }

    if(arg==101) {
        APPCORE.appDeactivation();
    }

}
#endif


void Core::setupFakeLocationProvider()
{
    qWarning() << "setupFakeLocationProvider from C++";

#ifdef Q_OS_ANDROID
    // Вызов обычного, не статического метода
    // Выяснено, что лучше работать со статическими методами, которые работают со
    // статическими свойствами Java класса. Это позволяет просто обращаться
    // к любому инстансу Java-класса, даже неспотря на то в каком треде объект выполняется
    // jniEventActivity->callMethod<void>("setupFakeLocationProvider");

    QAndroidJniObject::callStaticMethod<void>("ru/farwater/gnss/fakelocsample/JniEventActivity",
                                              "setupFakeLocationProvider",
                                              "()V");

#endif
}


void Core::updateFakeLocationProvider()
{
    qWarning() << "updateFakeLocationProvider from C++";

#ifdef Q_OS_ANDROID
    // jniEventActivity->callMethod<void>("updateFakeLocationProvider");

    for(int i=0; i<10000; i++) {
        QAndroidJniObject::callStaticMethod<void>("ru/farwater/gnss/fakelocsample/JniEventActivity",
                                                  "updateFakeLocationProvider",
                                                  "()V");
    }
#endif
}
