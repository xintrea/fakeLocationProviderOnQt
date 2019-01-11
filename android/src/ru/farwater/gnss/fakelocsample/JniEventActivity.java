//
// JniEventActivity.java
//
// For this java-class do not generate C++ header file

package ru.farwater.gnss.fakelocsample;

import android.util.Log;
import android.os.Bundle;
import android.os.Vibrator;
import android.content.Context;
import android.media.AudioManager;
import android.widget.Toast;

import android.location.LocationManager;


public class JniEventActivity extends org.qtproject.qt5.android.bindings.QtActivity {
    public static Vibrator m_vibrator;
    public static JniEventActivity m_instance;

    static String TAG = "JniEventActivity";

    MockLocationProvider mock;
    double locationShift=0;

    public JniEventActivity() {
        m_instance = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        super.onCreate(savedInstanceState);
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
    public void setupFakeLocationProvider() {
        Log.w(TAG, "Java: setupFakeLocationProvider start");

        // Создание экземпляра вышеописаного класса MockLocationProvider
        // в контексте данной Активности
        mock = new MockLocationProvider(LocationManager.GPS_PROVIDER, this);
        Log.w(TAG, "After new MockLocationProvider");

        //Set test location
        mock.pushLocation(45.0, 45.0);
        Log.w(TAG, "After mock.pushLocation");

        Log.w(TAG, "Java: setupFakeLocationProvider stop");
    }


    // Обновление значений координат в фиктивном провайдере
    public void updateFakeLocationProvider() {
        Log.w(TAG, "Java: updateFakeLocationProvider");

        /*
        locationShift=locationShift+0.1;

        double lat=45.0+locationShift;
        double lon=45.0+locationShift;

        //Set test location
        mock.pushLocation(lat, lon);

        Log.w(TAG, "New coordinate: "+Double.toString(lat)+" "+Double.toString(lon));
        */
    }
}
