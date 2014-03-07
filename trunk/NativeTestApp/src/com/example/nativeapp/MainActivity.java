package com.example.nativeapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity implements SensorEventListener,LocationListener{

    private SensorManager mSensorManager;
    private Sensor mAcc,mGyro,mProx;
    private LocationManager manager; 
    File gyro,acc,prox,gps;
    //protected File fileToWrite;
	protected BufferedWriter gyrowriter,gpswriter,proxwriter,accwriter;
	FileWriter fstream1,fstream2,fstream3,fstream4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Debug.startMethodTracing("abc");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE); 
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mProx = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
       
    	File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SensorLayer/");
		path.mkdirs();
		gyro = new File(path.getAbsolutePath(), "gyro");
		acc = new File(path.getAbsolutePath(), "acc");
		prox = new File(path.getAbsolutePath(), "prox");
		gps = new File(path.getAbsolutePath(), "gps11");
		try {
			 fstream1 = new FileWriter(gyro);
			gyrowriter = new BufferedWriter(fstream1);
			fstream2 = new FileWriter(acc);
			accwriter = new BufferedWriter(fstream2);
			fstream3 = new FileWriter(prox);
			proxwriter = new BufferedWriter(fstream3);
			fstream4 = new FileWriter(gps);
			gpswriter = new BufferedWriter(fstream4);
			
		} catch (IOException e) {
			Log.e("SensorLayer:: " , e.toString());
			
		}
		 mSensorManager.registerListener(this, mAcc, 100);
		 mSensorManager.registerListener(this, mProx, 100);
		 mSensorManager.registerListener(this, mGyro, 100);
	        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100,0, this);
	   	manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,100,0, this);
		
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
   
   

        protected void onResume() {
            super.onResume();
            mSensorManager.registerListener(this, mAcc,100);
   		 mSensorManager.registerListener(this, mProx, 100);
   		 mSensorManager.registerListener(this, mGyro, 100);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, this);
        	manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,100,0, this);
            
        }

        protected void onPause() {
            super.onPause();
            mSensorManager.unregisterListener(this);
            manager.removeUpdates(this);
            try {
				accwriter.close();
			gpswriter.close();
			proxwriter.close();
            gyrowriter.close();
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           // Debug.stopMethodTracing();
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
        	 Sensor sensor = event.sensor;
        	 //Log.e("change", "enter");
             if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                 //TODO: get values
            	 try {
            		 Log.i("ACC", ""+accwriter);
					accwriter.write("\nX:"+event.values[0]+",Y:"+event.values[1]+",Z:"+event.values[2]+"\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             }else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                 //TODO: get values
            	 try {
					gyrowriter.write("\nTime: " + event.timestamp +",Value:X :"+event.values[0]+",Y:"+event.values[1]+",Z:"+event.values[2]+"\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             }else if (sensor.getType()==Sensor.TYPE_PROXIMITY){
            	 try {
 					proxwriter.write("\nTime: " + event.timestamp +",Value:"+event.values[0]+"\n");
 				} catch (IOException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
             }

        }

		@Override
		public void onLocationChanged(Location arg0) {
			// TODO Auto-generated method stub
			try {
				Log.i("GPS", ""+gpswriter);
				gpswriter.write("\nProvider: " + arg0.getProvider() + " ; Latitude:"+arg0.getLatitude()+" ; Longitude: "+arg0.getLongitude() + " ; Accuracy: " + arg0.getAccuracy() + " ; Altitude: " + arg0.getAltitude());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
 }
    

