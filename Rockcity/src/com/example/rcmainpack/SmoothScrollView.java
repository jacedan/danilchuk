package com.example.rcmainpack;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
//import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.OverScroller;
//import android.widget.Scroller;

public class SmoothScrollView extends HorizontalScrollView 
{
	
	private OverScroller myScroller; 
	//private int ownOverscrollDistance;
	
	public SmoothScrollView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}

	public SmoothScrollView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}

	public SmoothScrollView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	
	
	private void init()
	{
	    try
	    {
	        Class<?> parent = this.getClass();
	        do
	        {
	            parent = parent.getSuperclass();
	        } while (!parent.getName().equals("android.widget.HorizontalScrollView"));

	        //Log.i("Scroller", "class: " + parent.getName());
	        Field field = parent.getDeclaredField("mScroller");
	        //Field field_a = parent.getDeclaredField("mOverscrollDistance");
	        field.setAccessible(true);
	        //field_a.setAccessible(true);
	        myScroller = (OverScroller) field.get(this);
	        //super.mOverscrollDistance = 0;
	        //super.mOverscrollDistance=0;

	    } catch (NoSuchFieldException e)
	    {
	        e.printStackTrace();
	    } catch (IllegalArgumentException e)
	    {
	        e.printStackTrace();
	    } catch (IllegalAccessException e)
	    {
	        e.printStackTrace();
	    }
	}

	public void customSmoothScrollBy(int dx, int dy)
	{
	    if (myScroller == null)
	    {
	        smoothScrollBy(dx, dy);
	        Log.e("Scroller", "!!!!!!!!!!!!");
	        return;
	    }

	    if (getChildCount() == 0)
	    {
	    	Log.e("Scroller", "000!!!!!!!!!");
	    	return;
	        
	    }

	    //final int width = getWidth() - getPaddingRight() - getPaddingLeft();
	    //final int right = getChildAt(0).getWidth();
	    //final int maxX = Math.max(0, right - width);
	    final int scrollX = getScrollX();
	    //dx = Math.max(0, Math.min(scrollX + dx, maxX)) - scrollX;
	    Log.d("dx", "dx="+dx);
	    
	    myScroller.startScroll(scrollX, getScrollY(), dx, 0, 1000);
	    invalidate();
	}

	public void customSmoothScrollTo(int x, int y)
	{
		//Log.d("customSmoothScrollTo", "customSmoothScrollTo");
		customSmoothScrollBy(x - getScrollX(), y - getScrollY());
	}
	
	/*@Override
	protected void onOverScrolled(int scrollX, int scrollY,
	             boolean clampedX, boolean clampedY) 
	{
		//customSmoothScrollTo(scrollX, scrollY); 
    }*/
	
	 /*@Override
     protected void onOverScrolled(int scrollX, int scrollY,
            boolean clampedX, boolean clampedY) {
        // Treat animating scrolls differently; see #computeScroll() for why.
         if (!myScroller.isFinished()) {
             mScrollX = scrollX;
             mScrollY = scrollY;
             invalidateParentIfNeeded();
             if (clampedX) {
                 mScroller.springBack(mScrollX, mScrollY, 0, getScrollRange(), 0, 0);
             }
         } else {
            super.scrollTo(scrollX, scrollY);
         }
         
         awakenScrollBars();
     }*/



}
