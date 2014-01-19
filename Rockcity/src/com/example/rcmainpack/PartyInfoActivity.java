package com.example.rcmainpack;

import android.app.Activity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
//import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PartyInfoActivity extends Activity
{
	
	PartyInfo currParty;
	ImageView currImage;
	TextView currText;
	LinearLayout layout;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        currParty=MainActivity.buffer;
        setContentView(R.layout.party_info_activity);
        layout=(LinearLayout)this.findViewById(R.id.partyLayout);
        currImage = (ImageView)this.findViewById(R.id.pinfoimage);
        currText = (TextView)this.findViewById(R.id.pinfotext);
        ImageButton buttonBack = (ImageButton)this.findViewById(R.id.imageButtonBack);
        String toAdd=new String();
        
        
        currText.setTextSize((float) 12.0);
        //currText.setTextAlignment(textAlignment.)
        
        for(int i=0;i<currParty.getPartyDescrStringSize();i++)
    	{
        	toAdd+=currParty.getPartyDescrOneString(i);
    		toAdd+="\n";
    		toAdd+="  ";
    		toAdd+="\n";
    		//Log.i("txt", "txt"+currParty.getPartyDescrOneString(i));
			
    		//currText.setText(" ");
    	}
        
        try
        {
        	currImage.setImageResource(currParty.getImgResourceId());
        	currText.setText(toAdd);
        	
        }
        catch(Exception e)
        {
        	
        }
        
        buttonBack.setOnClickListener(backEventListener());
        
        
    }
	
	@Override
	 public void onWindowFocusChanged(boolean hasFocus) 
	 {
			super.onWindowFocusChanged(hasFocus);
			
			
			if(hasFocus) 
		       //Toast.makeText(this, "Focus", Toast.LENGTH_LONG).show();
			{
				LayoutParams lp;
				lp=currImage.getLayoutParams();
				lp.height=(int)(0.5*layout.getHeight());
				
			}	
	}
	
	public OnClickListener backEventListener()
	{
		OnClickListener backEvent = new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				finish();
			}
		};
	return backEvent;	
	}

	
}
