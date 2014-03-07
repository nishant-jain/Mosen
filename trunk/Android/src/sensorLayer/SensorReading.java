package sensorLayer;

import java.util.ArrayList;

public class SensorReading{
	
	protected int SensorType;
	protected long TimeStamp;
	protected ArrayList<Double> Readings;
	
	public SensorReading(int SensorType, long TimeStamp, double ...values)
	{
		this.SensorType = SensorType;
		this.TimeStamp = TimeStamp;
		Readings = new ArrayList<Double>();
		
		for(int i=0; i<values.length; i++)
		{
			Readings.add(new Double(values[i]));
		}
	}
	
	public int getSensorType()
	{
		return SensorType;
	}
	
	public long TimeStamp()
	{
		return TimeStamp;
	}
	
	public ArrayList<Double> getReadings()
	{
		return Readings;
	}
	
	

}
