package com.example.rcmainpack;

import java.util.ArrayList;

public class PartyInfo 
{
	private int imgResourceId;
	private String partyName,dayOfWeek ;
	private ArrayList<String> partyDescription=new ArrayList<String>();
	//private String date,time;
	private int day;
	private int month;
	private int year;
	private int hour,minute;
	
	
	
	public void addImgResourceId(int id)
	{
		imgResourceId=id;
	
	}
	
	public void addPartyName(String name)
	{
		partyName=name;
	}
	
	public void addPartyDate(int d,int m,int y)
	{
		day=d;
		month=m;
		year=y;
	}
	
	public void addPartyTime(int h,int min)
	{
		hour=h;
		minute=min;
		
	}
	
	public void addPartyDay(String d)
	{
		dayOfWeek=d;
		
	}
	
	
	public int getImgResourceId()
	{
		return imgResourceId;
	}
	
	public String getPartyName()
	{
		return partyName;
	}
	
	public String getPartyDate()
	{
		
		
		String partyDate=String.format("%02d.%02d.%4d", day,month,year);// = (day+"."+month+"."+year);
		//partyDate=("%2.i",day);
		
		//partyDate.format("%2i.%2i.%4i", day,month,year);
		return partyDate;
	}
	
	public String getPartyTime()
	{
		
	    String partyTime=String.format(dayOfWeek+" "+"%02d-%02d", hour,minute);// = (day+"."+month+"."+year);
		//partyDate=("%2.i",day);
		
		//partyDate.format("%2i.%2i.%4i", day,month,year);
		return partyTime;
	}
	
	
	public void addPartyDescr(String[] toAdd)
	{
		for( String t: toAdd )
		{
		partyDescription.add(t);
		}
	}
	
	public void addPartyDescrOneString(String t)
	{
		partyDescription.add(t);
	}
	
	public String getPartyDescrOneString(int index)
	{
		return partyDescription.get(index);
	}
	
	public int getPartyDescrStringSize()
	{
		return partyDescription.size();
	}
	
	

}
