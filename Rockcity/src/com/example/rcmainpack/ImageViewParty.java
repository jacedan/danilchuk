package com.example.rcmainpack;

import java.lang.reflect.Field;

import android.content.Context;
import android.widget.ImageView;

public class ImageViewParty extends ImageView
{

	private PartyInfo info;
	
	public ImageViewParty(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	public PartyInfo getPartyInfo() 
	{
		return info;
	}

	public void setPartyInfo(PartyInfo info) 
	{
		this.info = info;
	}
	
	public void setImageCropPadding()
	{
		Field field=null;
		  try 
		  {
			  field = ImageView.class.getDeclaredField("mCropToPadding");
		
		  } 
		  catch (NoSuchFieldException e1) 
			{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			}
		  
		  if(field!=null)
		  {
		  field.setAccessible(true);
		  }
		  try 
		  	{
			field.set(this, true);
		  	} 
		  catch (IllegalArgumentException e) 
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			} 
		 	catch (IllegalAccessException e) 
		 	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		 	}
	}



}
