//
// JniEventActivity.java
//
// For this java-class do not generate C++ header file

package ru.farwater.gnss.fakelocsample;

import android.util.Log;
import android.os.Bundle;
import android.os.Vibrator;
import android.os.SystemClock;
import android.content.Context;
import android.media.AudioManager;
import android.widget.Toast;

import android.location.LocationManager;


public class JniEventActivity extends org.qtproject.qt5.android.bindings.QtActivity {
    public static Vibrator m_vibrator;
    public static JniEventActivity m_instance;

    static String TAG = "JniEventActivity";

    static MockLocationProvider mock=null;
    static double locationShift=0;

    static float bearing=0F;
    static float bearingDelta=0.5F;

    static float speed=0F;
    static float speedDelta=0.1F;

    public JniEventActivity() {
        m_instance = this;
    }


    public static void log(String message) {
        Log.w(TAG, message);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        log("onCreate() called!");

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        super.onCreate(savedInstanceState);

        // Создание экземпляра вышеописаного класса MockLocationProvider
        // в контексте данной Активности
        log("Before MockLocationProvider create");
        mock = new MockLocationProvider(LocationManager.GPS_PROVIDER, this);
        log("Mock address: "+mock);
        log("After MockLocationProvider create");

        Log.w(TAG, "onCreate() success finished");
    }

    @Override
    public void onStart() {
        Log.w(TAG, "onStart() called!");
        // NativeHelper.invokeVoidMethod(100); // Данный вызов не работает в qt 5.11.2, так как еще не зарегистирован *.so с C++ кодом
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.w(TAG, "onStop() called!");
        NativeHelper.invokeVoidMethod(101);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.w(TAG, "onDestroy() called!");
        super.onDestroy();
    }

    public static void invoke(int x) {
        final int z = x;
        m_instance.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(m_instance, "Invoke from C++ => Java: " + String.valueOf(z) + " (Button)",
                                           Toast.LENGTH_SHORT).show();
            }
        });
        NativeHelper.invokeVoidMethod(x);
    }

    public static void vibrate(int x) {
        if (m_vibrator == null) {
            if (m_instance != null) {
              m_vibrator = (Vibrator) m_instance.getSystemService(Context.VIBRATOR_SERVICE);
              m_vibrator.vibrate(x);
            }
        } else {
            m_vibrator.vibrate(x);
        }
        Log.w(TAG, "Vibro: Java");
    }


    // Инициализация фиктивного провайдера
    public static void setupFakeLocationProvider() {
        log("Java: setupFakeLocationProvider start");
        log("Mock address: "+mock);

        //Set start test location
        mock.pushLocation(45.0, 45.0, 0, 0);
        log("After mock.pushLocation");
    }


    // Обновление значений координат в фиктивном провайдере
    public static void updateFakeLocationProvider() {
        log("Java: updateFakeLocationProvider() start");

        // for(int i=0; i<10000; i++) {

            // Задержка в миллисекундах (1 мс = 1/1000 секунды)
            long delay=500;
            long startTime=System.currentTimeMillis();
            long currentTime=startTime;
            do {
                currentTime=System.currentTimeMillis();
            } while(currentTime-startTime < delay);

            // Координаты
            locationShift=locationShift+0.00001;
            double lat=45.0+locationShift;
            double lon=45.0+locationShift;

            // Поворот
            if(bearing<0 || bearing>360) {
                bearingDelta=-bearingDelta;
            }
            bearing=bearing+bearingDelta;

            // Скорость
            if(speed<0 || speed>50) {
                speedDelta=-speedDelta;
            }
            speed=speed+speedDelta;


            //Set test location
            mock.pushLocation(lat, lon, bearing, speed);

            log("New coordinate: "+Double.toString(lat)+" "+Double.toString(lon));
        // }

        log("Java: updateFakeLocationProvider() stop");
    }
}
