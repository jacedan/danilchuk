package com.example.rcmainpack;


//import java.lang.reflect.Field;
//import java.text.Format.Field;
//import java.lang.reflect.Field;
import java.util.ArrayList;

import com.examples.MyCalendarActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
//import android.app.DialogFragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;


public class MainActivity extends Activity 
{
	public static PartyInfo buffer=null;
	
	RelativeLayout mainLayout;
	SmoothScrollView imageViewMain;
	LinearLayout imageViewMainLinearLayout;
	TextView editTextMain;
	TextView editTextData;
	TextView editTextDescr;
	DatePickerDialog datePicker;
	ArrayList<PartyInfo> partyArray=new ArrayList<PartyInfo>();
	int count=0;
	int imageViewWidth=-1;
	boolean firstStart=true;
	int paddingmargin;
	//int imageViewMainWidth;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		mainLayout = (RelativeLayout)this.findViewById(R.id.mainRelative);
		imageViewMain= (SmoothScrollView)this.findViewById(R.id.imageViewMain);
		imageViewMainLinearLayout=(LinearLayout)this.findViewById(R.id.imageViewMainGallery);
		editTextMain=(TextView) this.findViewById(R.id.editTextMain);
		editTextData=(TextView) this.findViewById(R.id.textViewDate);
		editTextDescr=(TextView) this.findViewById(R.id.textView1);
		
		//mainLayout.setVisibility(View.INVISIBLE);
		//datePicker =(DatePicker) this.findViewById(R.id.datePicker);
		
		//datePicker.set
		
		addId();
		imageViewMain.setSmoothScrollingEnabled(true);
		imageViewMain.setOnTouchListener(setListerner());
		//imageViewMain.s
			
		editTextMain.setSelected(true);
		editTextData.setOnClickListener(this.setClick());
		//imageViewMainLinearLayout.setBackgroundResource(R.drawable.border);
		
		//onCenterImageView(0);
	}

	 @Override
	 public void onWindowFocusChanged(boolean hasFocus) 
	 {
			super.onWindowFocusChanged(hasFocus);
			MarginLayoutParams lp;
			//MarginLayoutParams linearLp;
			
			if(hasFocus) 
		        //Toast.makeText(this, "Focus", Toast.LENGTH_LONG).show();
			{
				if(this.firstStart)
				{
					firstStart=false;
					paddingmargin= mainLayout.getHeight();
					
					lp =(MarginLayoutParams)imageViewMain.getLayoutParams();
					lp.bottomMargin=(int) (mainLayout.getHeight()*0.30);
					imageViewWidth=(int)((imageViewMainLinearLayout.getHeight()-(mainLayout.getHeight()*0.30))*0.70);
					
					this.addImagesToScroolView();
					imageViewMainLinearLayout.setPadding(paddingmargin, 0, paddingmargin, 0);
					this.onCenterImageView(0);
					
					
				}
				
			}	
	}
	 
	 
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	
	
	
	
	public void insertImage(PartyInfo info)
	{
	  
	  ImageViewParty imageView = new ImageViewParty(getApplicationContext());
	  
	  
	  imageView.setPartyInfo(info);
	  imageView.setImageResource(imageView.getPartyInfo().getImgResourceId());
	  
	  Log.i("xxx", "xxx="+imageViewWidth+" "+imageViewMain.getHeight());
	  imageView.setAdjustViewBounds(true);
	  imageView.setImageCropPadding();
	  imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	  imageView.setBackgroundResource(R.drawable.border);
	  
	  imageView.setLayoutParams(new LayoutParams(imageViewWidth,LayoutParams.MATCH_PARENT));
	  imageViewMainLinearLayout.addView(imageView);
	}
	
	/*public void insertNullImage()
	{
	  ImageView imageView = new ImageView(getApplicationContext());
	  //imageView.setImageResource(id);
	  //imageView.setLayoutParams(new LayoutParams(120, 200));
	  //imageView.setLayoutParams(new LayoutParams((int)((LayoutParams.MATCH_PARENT)*0.75),LayoutParams.MATCH_PARENT));
	  imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	  imageViewMainLinearLayout.addView(imageView);
	  
	
	}*/
	
	public void onCenterImageView(int index)
	{
		int coordXcenter;
		
		
		int imv05=(int)(imageViewMain.getWidth()*0.05);
		
		View viewCenter=imageViewMainLinearLayout.getChildAt(index);
		
		coordXcenter=((imageViewWidth-imageViewMain.getWidth())/2)+index*imageViewWidth+paddingmargin;
		Log.d("coordXcenter", "coordXcenter="+coordXcenter+" "+index);
		
		//imageViewMainLinearLayout.scrollTo(coordXcenter, 0);
		//imageViewMainLinearLayout.
		//imageViewMain.scrollTo(coordXcenter, 0);
		//imageViewMain.smoothScrollTo(coordXcenter, 0);
		imageViewMain.customSmoothScrollTo(coordXcenter, 0);
		
		//Log.i("xx1", "xx1="+viewCenter.getClass().getName());
		if(viewCenter.getClass().getName().indexOf("ImageViewParty")>0)
		{
		
			setMargins(imv05,index);
			editTextMain.setText(((ImageViewParty) viewCenter).getPartyInfo().getPartyName());
			editTextData.setText(((ImageViewParty) viewCenter).getPartyInfo().getPartyDate());
			editTextDescr.setText(((ImageViewParty) viewCenter).getPartyInfo().getPartyTime());
			//Log.i("hw", "w= "+viewCenter.getWidth()+"h="+viewCenter.getHeight()+" h="+imageViewMain.getHeight()+" h3="+imageViewMainLinearLayout.getHeight());
		}
	
	}
	
	public void setMargins(int m,int index)
	{
		int count=imageViewMainLinearLayout.getChildCount();
		LinearLayout.LayoutParams lp;
		
		for(int i=0;i<count;i++)
		{
			 
			 lp =	(LinearLayout.LayoutParams)imageViewMainLinearLayout.getChildAt(i).getLayoutParams();
			 if(i!=index)
			 {
			 	lp.setMargins(0, m, 0, m);
			 }
			 else
			 {
				 lp.setMargins(0, 0, 0, 0);
			 }
			 
			 //imageViewMainLinearLayout.getChildAt(i).setLayoutParams(lp);
		}
		
	}
	
	
	
	
	
	
	public void addImagesToScroolView()
	{
	PartyInfo party;
	//insertNullImage();
	for(int i=0;i<partyArray.size();i++)
	  {
		party=partyArray.get(i);
		 insertImage(party);
	  }
	//insertNullImage();  
	
	}
	
	
	
	public OnClickListener setClick()
	{
		OnClickListener l = new OnClickListener()
		{

			/*@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				//showDialog(DATE_DIALOG_ID);
				DialogFragment newFragment = new DatePickerFragment();
			    newFragment.show(getFragmentManager(), "Выбор Даты");

			}*/
			
			@Override
			public void onClick(View v) 
			{
			Intent intent = new Intent(MainActivity.this, MyCalendarActivity.class);
			startActivity(intent);
			}
			
			
		};
		
	return l;	
	}
	
	


	
	
	
	public OnTouchListener setListerner()
	{
		OnTouchListener l = new OnTouchListener()
		{

			float posX;
			boolean touch=false;
			float delta=0;
						
			@Override
            public boolean onTouch(View arg0, MotionEvent arg1) 
            {
                    //img.setImageURI(uri[pos]);
                    //pos++;
				//boolean r=false;
				//Log.i("action", "action is"+arg1.getAction());
				
				//boolean defaultResult = arg0.onTouchEvent(arg1);
				
								
				
				switch(arg1.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					this.touch=true;
					this.posX=arg1.getX();
					break;
				
								
				
				case MotionEvent.ACTION_UP:
					if(touch)
					{
						
						delta=arg1.getX()-this.posX;
						
						if(delta>8)
						{
							
							prevParty();
						}
						if(delta<-8)
						{
							nextParty();
						}
						if((delta>-2)&&(delta<2))
						{
							getPartyInfoActivity();
						}
						
					touch=false;					
					}
					
				
				
				
				default:
					break;

				}
				
					
				
				
				
               return true;
            }
        }; 
        
	return l;	
	}
	
	
	
	
	public void nextParty()
	{
		buffer=null;
		if(count<(partyArray.size()-1))
		{
			count++;
		}
		else
		{
			//count=0;
			return;
		}
		
		//imageViewMain.setImageResource(partyArray.get(count).getImgResourceId());
		//imageViewMain.scrollBy(120, 0);
		//insertImage(partyArray.get(0).getImgResourceId());
		onCenterImageView(count);
		 
		
	}
	
	public void prevParty()
	{
		buffer=null;
		if(count>0)
		{
			count--;
		}
		else
		{
			//count=(partyArray.size()-1);
			return;
		}
		
		//imageViewMain.setImageResource(partyArray.get(count).getImgResourceId());
		//imageViewMain.scrollBy(-120, 0);
		onCenterImageView(count);
		//editTextMain.setText(partyArray.get(count).getPartyName());
		//editTextData.setText(partyArray.get(count).getPartyDate());
		
	}
	
	public void getPartyInfoActivity()
	{
		
		//Bundle b=new Bundle();
		Intent intent = new Intent(MainActivity.this, PartyInfoActivity.class);
		buffer=partyArray.get(count);
		//b.putParcelable("currentParty", partyArray.get(count));
		startActivity(intent);
		
	}
	
	
	
	public void addId()
	{
		PartyInfo temp; //Crivo nah
		int[] ids = new int[4];
		ids[1]=(R.drawable.i5642);
		ids[3]=(R.drawable.i5653);
		ids[2]=(R.drawable.i5654);
		ids[0]=(R.drawable.i7560);
		
		String names[]=new String[4];
		names[1]=this.getString(R.string.djfeel);
		names[3]=this.getString(R.string.Dancehall);
		names[2]=this.getString(R.string.DPS);
		names[0]=this.getString(R.string.GOGO);
		
		int[] maydays = {3,3,4,4};
		int[] hours = {18,21,17,20};
		int[] mins = {0,0,0,30};
		
		String[] weekDays=new String[]{"Пятница","Пятница","Суббота","Суббота"};
		
		String descr[][]=new String[4][];
		descr[1]=this.getResources().getStringArray(R.array.DJ_FEEL);
		descr[3]=this.getResources().getStringArray(R.array.SIBERIAN_DANCEHALL_CONTEST);
		descr[2]=this.getResources().getStringArray(R.array.Lotos);
		descr[0]=this.getResources().getStringArray(R.array.Kesting_Go_go);
		
		
		
		for(int i=0;i<ids.length;i++)
		{
			temp=new PartyInfo();
			temp.addImgResourceId(ids[i]);
			temp.addPartyName(names[i]);
			temp.addPartyDate(maydays[i],5 , 2012);
			temp.addPartyDescr(descr[i]);
			temp.addPartyDay(weekDays[i]);
			temp.addPartyTime(hours[i], mins[i]);
			partyArray.add(temp);
			//this.insertImage(partyArray)
			temp=null;
		}
		
		
	
	
	}
	

}
