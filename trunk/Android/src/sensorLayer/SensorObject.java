package sensorLayer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public abstract class SensorObject implements SensorInterface {

	protected int MinDelay, Version;
	protected int SensorType;
	protected float Power, MaximumRange, Resolution;
	protected String Name, Vendor;
	protected Context app;
	
	protected File fileToWrite;
	protected BufferedWriter fileWriter;
	protected boolean fileInitialized = false;
	protected boolean WritingToFile = false;
	
	private Timer DisableTimer;
	
	//Event Code start
	protected SensorReadingHandler Listener;
	
	public abstract boolean registerListener(SensorReadingHandler Handler, int DelayInMilliseconds);
	public abstract void unRegisterListener();
	
	public boolean isBusy()
	{
		return (fileInitialized | Listener!=null);
	}
	

	protected void setHandler(SensorReadingHandler handler)
	{
		Listener = handler;
	}
	protected void resetHandler()
	{
		Listener = null;
	}
	
	protected void generateReading(long TimeStamp, double ...values)
	{
		SensorReading R = new SensorReading(SensorType, TimeStamp, values);
		
		if(Listener!=null)
			Listener.SensorReadingChanged(R);
	}
	//Event Code end
	 

	protected boolean Writable = false;
	
	protected abstract void deRegister();
	
	protected class DisableTask extends TimerTask{

		public DisableTask()
		{
			super(); 
		}
		
		@Override
		public void run() {
			deRegister();
			DisableTimer.cancel();
		}
	}
	
	
	protected void ScheduleDisabling(int TimeInSeconds)
	{
		DisableTimer.schedule(new DisableTask(), TimeInSeconds*1000);
	}
	
	
	public  SensorObject()
	{
		String state = Environment.getExternalStorageState();
		
		if (Environment.MEDIA_MOUNTED.equals(state)) 
		{
			Writable = true;
		}
		else
		{
			Writable = false;
		}
		DisableTimer = new Timer();
		
		//Listener list for event listeners
		Listener = null;
		SensorType = 0;
	}
	
	
	protected boolean InitializeFile(String Filename)
	{
		if(!Writable)
		{
			return false;
		}
	
		//Create Folder if not existing already.
		File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SensorLayer/" + this.Name + "/");
		path.mkdirs();
		//Create Folder complete.
		
		fileToWrite = new File(path.getAbsolutePath(), Filename);
		
		
		try {
			FileWriter fstream = new FileWriter(fileToWrite);
			fileWriter = new BufferedWriter(fstream);
		} catch (IOException e) {
			Log.e("SensorLayer:: " , e.toString());
			return false;
		}
		fileInitialized = WritingToFile = true;
		return true;
	}
	
	protected boolean writeToFile(String toWrite)
	{
		try {
			fileWriter.write(toWrite);
			return true;
		} catch (IOException e) {
			Log.e("SensorLayer:: ", e.toString());
			return false;
		}
	}
	
	protected void closeFile()
	{
		try {
			fileWriter.close();
		} catch (IOException e) {
			Log.e("SensorLayer:: " , e.toString());
		}
		finally
		{
			WritingToFile = false;
			fileInitialized = false;
		}
	}
	
	public int getMinDelay()
	{
		return MinDelay;
	}
	
	public int getType()
	{
		return SensorType;
	}
	
	public int getVersion()
	{
		return Version;
	}
	
	public float getPower()
	{
		return Power;
	}
	
	public float getMaximumRange()
	{
		return MaximumRange;
	}
	
	public float getResolution()
	{
		return Resolution;
	}
	
	public String getName()
	{
		return Name;
	}
	
	public String getVendor()
	{
		return Vendor;
	}
	
}
