package sensorLayer.sensors;

import java.util.Date;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import sensorLayer.SensorObject;
import sensorLayer.SensorReadingHandler;
import sensorLayer.SensorLayer;

public class AccMeter extends SensorObject{
	
private SensorManager Manager;
private Sensor SystemSensor;
private SensorHandler Handler;
	
/*
 * (non-Javadoc)
 * Notes:
 * handler is the parameter passed in requestUpdates, where Handler is the object wide SensorReadingHandler object.
 */

	protected void deRegister()
	{
		Manager.unregisterListener(Handler, SystemSensor);
		closeFile();
	}
	
	public AccMeter(Context app){

		this.app=app;
		Manager=(SensorManager)this.app.getSystemService(Context.SENSOR_SERVICE); 
		SystemSensor = Manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		this.SensorType = SensorLayer.TYPE_ACCELEROMETER;
		
		this.MinDelay = SystemSensor.getMinDelay();
		this.MaximumRange = SystemSensor.getMaximumRange();
		this.Power = SystemSensor.getPower();
		this.Resolution = SystemSensor.getResolution();
		this.Version = SystemSensor.getVersion();
		this.Name = SystemSensor.getName();
		this.Vendor = SystemSensor.getVendor();
		
		Handler = new SensorHandler();
	}
	
	public boolean registerListener(SensorReadingHandler handler, int DelayInMilliseconds)
	{
		if(isBusy())
			return false;
		setHandler(handler);
		Manager.registerListener(Handler, SystemSensor, DelayInMilliseconds);
		return true;
	}
	
	public void unRegisterListener()
	{
		if(!fileInitialized)
			Manager.unregisterListener(Handler);
		resetHandler();
	}
	
	public String get(int TimeInSeconds, int DelayInMilliseconds)
	{
		if(isBusy())
			return null;
		String FileName = new Date().toString().replace(":", "-") +  ".txt";
		
		
		if(WritingToFile)
			return null;
		
		
		if(!InitializeFile(FileName))
			return null;
		
		//Delay in Milliseconds is actually microseconds
		Manager.registerListener(Handler, SystemSensor, DelayInMilliseconds);
		ScheduleDisabling(TimeInSeconds);
		return FileName;
	}
	
	class SensorHandler implements SensorEventListener
	{
		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent arg0) {
			// TODO Auto-generated method stub
			if(fileInitialized)
			{
				writeToFile("\nX: " + arg0.values[0] + ",Y: " + arg0.values[1] + ",Z: " + arg0.values[2]);
			}
			
			generateReading(arg0.timestamp, arg0.values[0], arg0.values[1], arg0.values[2]);
			
		}
		
	}
	
}
