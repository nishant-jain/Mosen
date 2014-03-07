package sensorLayer.sensors;

import java.util.Date;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import sensorLayer.SensorObject;
import sensorLayer.SensorReadingHandler;
import sensorLayer.SensorLayer;

public class GPS  extends SensorObject{
	private LocationManager manager; 
	private GPSHandler Handler; 
	
	protected void deRegister()
	{
		manager.removeUpdates(Handler);
		closeFile();
	}
	
	public GPS(Context app){
		this.app=app;
		manager=(LocationManager)this.app.getSystemService(Context.LOCATION_SERVICE); 
		this.SensorType = SensorLayer.TYPE_GPS;
		
		this.MinDelay = 0;
		this.MaximumRange = 0;
		this.Power = 0;
		this.Resolution = 0;
		this.Version = 0;
		this.Name = "GPS";
		this.Vendor = "Android";
		Handler = new GPSHandler();
	}
	
	public boolean registerListener(SensorReadingHandler handler, int DelayInMilliseconds)
	{
		if(isBusy())
			return false;
		setHandler(handler);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, DelayInMilliseconds, 0, Handler);
    	manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,DelayInMilliseconds,0, Handler);
    	return true;
	}
	
	public void unRegisterListener()
	{
		if(!fileInitialized)
			manager.removeUpdates(Handler);
		resetHandler();
	}
	
	//Perhaps use a different get() definition for GPS? We could take minDistance and minDelay rather than DelayInMilliseconds since its not a streaming sensor. 
    //Kshitiz 21-6-12 Adding a boolean value in get(), to decide on using the Network Provider or not. Testing purposes, maybe integrate later.
	  public String get(int TimeInSeconds, int DelayInMilliseconds, boolean useNetworkLocation){
		    
		  	if(isBusy())
				return null;
			String FileName = new Date().toString().replace(":", "-") +  ".txt";
			
	    	//Putting in checks if get is not possible.
	    	if(WritingToFile)
	    		return null;
	    	
	    	//Initializing the file first.
	    	if(!InitializeFile(FileName))
	    		return null;
	    	
	    	
	    	manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,DelayInMilliseconds,0, Handler);
	    	//Use network location also
	    	manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, DelayInMilliseconds, 0, Handler);
	    	
	    	ScheduleDisabling(TimeInSeconds);
	    	return FileName;
	    }
	
	class GPSHandler implements LocationListener{
	
		public void onLocationChanged(Location location) 
		{
			if(fileInitialized)
			{
				//Kshitiz 21-6-12; Added Altitude, Provider and Accuracy, tidied up the string.
				writeToFile("\nProvider: " + location.getProvider() + " ; Latitude:"+location.getLatitude()+" ; Longitude: "+location.getLongitude() + " ; Accuracy: " + location.getAccuracy() + " ; Altitude: " + location.getAltitude());
				
			}
			//Putting only Timestamp, Lattitude, Longitude, Accuracy (in this order) and provider for the generated event.
			//0 for Network Provider, 1 for GPS
			if(location.getProvider().equals(LocationManager.GPS_PROVIDER))
				generateReading(location.getTime(), location.getLatitude(), location.getLongitude(), location.getAccuracy(), 1.0);
			else
				generateReading(location.getTime(), location.getLatitude(), location.getLongitude(), location.getAccuracy(), 0.0);
		}
	
    //Unimplemented Methods.
	    public void onStatusChanged(String provider, int status, Bundle extras) {}

	    public void onProviderEnabled(String provider) {}

	    public void onProviderDisabled(String provider) {}
	 
	    
	}
}
