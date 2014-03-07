package com.kshitiz.testapps;

import java.io.File;
import java.util.List;

import sensorLayer.SensorReading;
import sensorLayer.SensorReadingHandler;
import sensorLayer.SensorsManager;
import sensorLayer.sensors.AccMeter;
import sensorLayer.sensors.GPS;
import sensorLayer.sensors.GyroSensor;
import sensorLayer.sensors.LightSensor;
import sensorLayer.sensors.ProximitySensor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

public class Main extends Activity implements SensorReadingHandler, SensorEventListener {
    /** Called when the activity is first created. */
	
	String AccS ="", GPSS="", GYROS="", PXS="";
	 TextView view ;
	 Sensor Accelerometer;
	 Location CLocation = null;
	  LocationManager L ;
	  SensorsManager M ;
	  SensorManager SystemManager;
	  int LCounter, SCounter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
     /*   Intent i = new Intent(this, MiddleLayer.class);
        startActivity(i);
       */ 
        SCounter = LCounter = 0;
        SystemManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        
        
        view = new TextView(this);
        view.setText("Kshitiz Bakshi!");
        setContentView(view);
        view.setMovementMethod(new ScrollingMovementMethod());
        
        M = new SensorsManager(this);
        
        AccMeter Ac = M.getAccMeter();
        GPS G = M.getGPS();
       GyroSensor GY = M.getGyroSensor();
        ProximitySensor PX = M.getProximitySensor();
        Ac.get(10, 10);
        G.get(10, 10, true);
        PX.get(10, 10);
        GY.get(10, 10);
       Ac.registerListener(this, 1000); 
       G.registerListener(this, 1000);
        GY.registerListener(this, 100000);
        PX.registerListener(this, 1000);
        
  
    }

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {

		SCounter++;
		switch(event.sensor.getType())
		{
		case(Sensor.TYPE_ACCELEROMETER):
			{
				//ToShow = "X: " + event.values[0] + "\nY: " + event.values[1] + "\nZ: " + event.values[2];
		//		view.setText(ToShow);
			}
		}
		
	}
	
	public void onResume()
	{
		super.onResume();
	//	M.registerListener(this, Accelerometer, 1000000);
	}
	
	public void onPause()
	{
		super.onPause();
		M.closeAll();
		//M.unregisterListener(this);
	
       
//		L.removeUpdates(this);
		
	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	String a = "";
		if(CLocation==null)
		{
			CLocation = location;
			a = "Accuracy: " + CLocation.getAccuracy() + "\nProvider: " + CLocation.getProvider() + "\nLatitude: " + CLocation.getLatitude() + "\nLongitude: " + CLocation.getLongitude() + "\nAltitude: " + CLocation.getAltitude();
			
		}
		else if(CLocation.equals(location))
		{
			a = "Same Location agaisadn!";
		}
		else
		{
			if(CLocation.getTime() < location.getTime())
			{
				a = "New Location Detected! \nAccuracy: " + CLocation.getAccuracy() + "\nProvider: " + CLocation.getProvider() + "\nLatitude: " + CLocation.getLatitude() + "\nLongitude: " + CLocation.getLongitude() + "\nAltitude: " + CLocation.getAltitude();
			}
		}
		
	view.setText(a);
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	public void SensorReadingChanged(SensorReading arg0) {
		// TODO Auto-generated method stub
		
		switch(arg0.getSensorType())
		{
		case SensorsManager.TYPE_ACCELEROMETER:
			{
				AccS = arg0.getReadings().get(0) + "," + arg0.getReadings().get(1) + "," + arg0.getReadings().get(2);	
				break;
			}
		case SensorsManager.TYPE_GPS:
		{
			GPSS = arg0.getReadings().get(0) + "," + arg0.getReadings().get(1) + "," + arg0.getReadings().get(2);
			break;
		}
		case SensorsManager.TYPE_GYRO:
		{
			GYROS = arg0.getReadings().get(0) + "," + arg0.getReadings().get(1) + "," + arg0.getReadings().get(2);
			break;
		}
		
		case SensorsManager.TYPE_PROXIMITY:
		{
			PXS = arg0.getReadings().get(1) + " ";
			break;
		}
		}
		
		view.setText("Accelerometer: " + AccS + "\nGPS: " + GPSS + "\nGyro: " + GYROS + "\nProximity: " + PXS);
	
	}


}