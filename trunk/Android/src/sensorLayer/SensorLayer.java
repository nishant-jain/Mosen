package sensorLayer;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import sensorLayer.sensors.AccMeter;
import sensorLayer.sensors.GPS;
import sensorLayer.sensors.GyroSensor;
import sensorLayer.sensors.LightSensor;
import sensorLayer.sensors.ProximitySensor;

public class SensorLayer {

	public static final int TYPE_ACCELEROMETER = 1;
	public static final int TYPE_GPS = 2;
	public static final int TYPE_GYRO =3;
	public static final int TYPE_PROXIMITY = 4;
	
	private Context AppContext;
	
	//Arraylist to store all the sensors currently in use. To be used in mass-deRegistrations
	private ArrayList<SensorObject> SensorsReturned;
	
	public SensorLayer(Context App)
	{
		SensorsReturned = new ArrayList<SensorObject>();
		AppContext = App;
	}
	
	public AccMeter getAccMeter()
	{
		AccMeter A = new AccMeter(AppContext);
		SensorsReturned.add(A);
		return A;
	}
	
	public GPS getGPS(){
		GPS A = new GPS(AppContext);
		SensorsReturned.add(A);
		return A;
	}
	
	public LightSensor getLightSensor()
	{
		LightSensor L = new LightSensor(AppContext);
		SensorsReturned.add(L);
		return L;
	}
	
	public ProximitySensor getProximitySensor(){
		ProximitySensor L = new ProximitySensor(AppContext);
		SensorsReturned.add(L);
		return L;
	}
	
	public GyroSensor getGyroSensor(){
		GyroSensor L = new GyroSensor(AppContext);
		SensorsReturned.add(L);
		return L;
	}
	
	
	public void closeAll()
	{
		Iterator<SensorObject> I = SensorsReturned.iterator();
		while(I.hasNext())
		{
			SensorObject A = I.next();
			if(A.fileInitialized)
				A.deRegister();
			if(A.Listener!=null)
				A.unRegisterListener();
			I.remove();
		}
	}
}
