#include "HelperJni.h"


HelperJni::HelperJni(QObject* parent) : QObject(parent)
{

}


int HelperJni::vibro()
{
    qWarning() << "Vibro from C++";

#ifdef Q_OS_ANDROID
    QAndroidJniObject::callStaticMethod<void>("ru/farwater/gnss/fakelocsample/JniEventActivity",
                                              "vibrate",
                                              "(I)V",
                                              100);
#endif

    return 1;
}


void HelperJni::sendMail(QString to, QString subj, QString text)
{
    qWarning() << "Call send mail from C++ to Java";

#ifdef Q_OS_ANDROID
    QAndroidJniObject javaTo = QAndroidJniObject::fromString(to);
    QAndroidJniObject javaSubj = QAndroidJniObject::fromString(subj);
    QAndroidJniObject javaText = QAndroidJniObject::fromString(text);

    QAndroidJniObject androidObj;
    QAndroidJniObject::callStaticMethod<void>("ru/farwater/gnss/fakelocsample/JniEventActivity",
                                "sendMail",
                                "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
                                javaTo.object<jstring>(),
                                javaSubj.object<jstring>(),
                                javaText.object<jstring>());
#endif
}
