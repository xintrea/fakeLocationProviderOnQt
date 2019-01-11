#include <QDebug>
#include "KeepAwakeHelper.h"


KeepAwakeHelper::KeepAwakeHelper()
{
    QAndroidJniObject activity = QAndroidJniObject::callStaticObjectMethod("org/qtproject/qt5/android/QtNative", "activity", "()Landroid/app/Activity;");
    // QAndroidJniObject activity = QAndroidJniObject::callStaticObjectMethod("ru/farwater/gnss/fakelocsample/JniEventActivity", "activity", "()Landroid/app/Activity;");

    if ( activity.isValid() )
    {
        QAndroidJniObject serviceName = QAndroidJniObject::getStaticObjectField<jstring>("android/content/Context","POWER_SERVICE");
        if ( serviceName.isValid() )
        {
            QAndroidJniObject powerMgr = activity.callObjectMethod("getSystemService", "(Ljava/lang/String;)Ljava/lang/Object;",serviceName.object<jobject>());
            if ( powerMgr.isValid() )
            {
                jint levelAndFlags = QAndroidJniObject::getStaticField<jint>("android/os/PowerManager","SCREEN_BRIGHT_WAKE_LOCK");

                QAndroidJniObject tag = QAndroidJniObject::fromString( "My Tag" );

                // Запоминается Jni-объект, управляющий подсветкой
                mWakeLock = powerMgr.callObjectMethod("newWakeLock", "(ILjava/lang/String;)Landroid/os/PowerManager$WakeLock;", levelAndFlags,tag.object<jstring>());
            }
        }
    }

    mWakeLockState=false;
    on();
}


KeepAwakeHelper::~KeepAwakeHelper()
{

}


void KeepAwakeHelper::on()
{
    if(mWakeLockState==true) {
        return;
    }

    if ( mWakeLock.isValid() )
    {
        mWakeLock.callMethod<void>("acquire", "()V");
        qWarning() << "Locked device, can't go to standby anymore";
    }
    else
    {
        Q_ASSERT(false);
    }

    mWakeLockState=true;
}


void KeepAwakeHelper::off()
{
    if(mWakeLockState==false) {
        return;
    }

    if ( mWakeLock.isValid() )
    {
        mWakeLock.callMethod<void>("release", "()V");
        qWarning() << "Unlocked device, can now go to standby";
    }

    mWakeLockState=false;
}
