package com.iiitd.librarytestapp;

import sensorLayer.SensorLayer;
import sensorLayer.SensorObject;
import sensorLayer.sensors.AccMeter;
import sensorLayer.sensors.GPS;
import sensorLayer.sensors.GyroSensor;
import sensorLayer.sensors.ProximitySensor;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LibraryTest extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_test);
        SensorLayer a =new SensorLayer(this);
        AccMeter acc=a.getAccMeter();
        acc.get(600, 100);
        ProximitySensor proc = a.getProximitySensor();
        proc.get(600, 100);
        GyroSensor gyro =a.getGyroSensor();
        gyro.get(600, 100);
        GPS loc = a.getGPS();
        loc.get(600, 100, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_library_test, menu);
        return true;
    }
}
