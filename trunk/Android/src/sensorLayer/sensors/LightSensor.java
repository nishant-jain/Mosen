package sensorLayer.sensors;

import java.util.Date;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import sensorLayer.SensorObject;
import sensorLayer.SensorReadingHandler;

public class LightSensor extends SensorObject{

	private SensorManager Manager;
	private Sensor SystemSensor;
	private SensorHandler Handler;

	
	protected void deRegister()
	{	
		Manager.unregisterListener(Handler, SystemSensor);
		closeFile();
	}
	
	public LightSensor(Context App)
	{
		this.app = App;
		Manager=(SensorManager)this.app.getSystemService(Context.SENSOR_SERVICE); 
		SystemSensor = Manager.getDefaultSensor(Sensor.TYPE_LIGHT);
		
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
		return false;
	}
	
	public void unRegisterListener()
	{
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
				writeToFile("\nTime: " + arg0.timestamp + " ; Value: " + arg0.values[0]);
			}
			
			generateReading(arg0.timestamp, arg0.values[0]);
		}
		
	}

}
