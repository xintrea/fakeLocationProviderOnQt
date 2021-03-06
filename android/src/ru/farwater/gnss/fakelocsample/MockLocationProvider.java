package ru.farwater.gnss.fakelocsample;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;

public class MockLocationProvider {
  String providerName;
  Context ctx;

  // Конструктор фиктивного провайдера
  public MockLocationProvider(String name, Context ctx) {
    this.providerName = name;
    this.ctx = ctx;

    LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

    lm.addTestProvider(providerName,
                       false, // requiresNetwork
                       false, // requiresSatellite
                       false, // requiresCell
                       false, // hasMonetaryCost
                       false, // supportsAltitude
                       true,  // supportsSpeed
                       true,  // supportsBearing
                       0,     // powerRequirement
                       5      // accuracy
    );

    lm.setTestProviderEnabled(providerName, true);
  }

  // Установка координат в фиктивном провайдере
  public void pushLocation(double lat, double lon, float bearing, float speed) {
    JniEventActivity.log("Java: pushLocation, before getSystemService");
    LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
    JniEventActivity.log("Java: pushLocation, after getSystemService");

    Location mockLocation = new Location(providerName);
    mockLocation.setLatitude(lat);
    mockLocation.setLongitude(lon);
    mockLocation.setAltitude(0);
    mockLocation.setAccuracy(1);
    mockLocation.setTime(System.currentTimeMillis());
    mockLocation.setBearing(bearing);
    mockLocation.setSpeed(speed);

    // Обязательно нужно выставить свойство et для определенных версий Android
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
        mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
    }

    JniEventActivity.log("Java: pushLocation, before setTestProviderLocation");
    lm.setTestProviderLocation(providerName, mockLocation);
    JniEventActivity.log("Java: pushLocation, after setTestProviderLocation");
  }

  // Выключение фиктивного провайдера
  public void shutdown() {
    LocationManager lm = (LocationManager) ctx.getSystemService(
      Context.LOCATION_SERVICE);
    lm.removeTestProvider(providerName);
  }
}

