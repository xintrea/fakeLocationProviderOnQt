package ru.farwater.gnss.fakelocsample;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

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
  public void pushLocation(double lat, double lon) {
    LocationManager lm = (LocationManager) ctx.getSystemService(
      Context.LOCATION_SERVICE);

    Location mockLocation = new Location(providerName);
    mockLocation.setLatitude(lat);
    mockLocation.setLongitude(lon);
    mockLocation.setAltitude(0);
    mockLocation.setTime(System.currentTimeMillis());
    lm.setTestProviderLocation(providerName, mockLocation);
  }

  // Выключение фиктивного провайдера
  public void shutdown() {
    LocationManager lm = (LocationManager) ctx.getSystemService(
      Context.LOCATION_SERVICE);
    lm.removeTestProvider(providerName);
  }
}
