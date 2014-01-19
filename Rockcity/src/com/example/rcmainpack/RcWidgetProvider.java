package com.example.rcmainpack;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.database.Cursor;
//import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class RcWidgetProvider extends AppWidgetProvider 
{
   //public static final String DEBUG_TAG = "RcWidgetProvider";
   @Override
   public void onUpdate(Context context, AppWidgetManager appWidgetManager,
      int[] appWidgetIds) 
   {
      try {
         updateWidgetContent(context, appWidgetManager);
      } catch (Exception e) {
         //Log.e(DEBUG_TAG, "Failed", e);
      }
      //Log.d("onEUpdate", "onUpdate");
   }
   
   @Override
   public void onEnabled(Context context) 
   {
     super.onEnabled(context);
     
     
     
     
     Log.d("onEnabled", "onEnabled");
   }
   
   
   
   
   public static void updateWidgetContent(Context context,
      AppWidgetManager appWidgetManager) 
   {
      //String strLatestTitle = context.getString(R.string.appwidget_no_latest);
      //String [] projection = {TutListDatabase.COL_TITLE};
      //Uri content = TutListProvider.CONTENT_URI;
      /*Cursor cursor = context.getContentResolver().query(content, projection,
            null, null, TutListDatabase.COL_DATE + " desc LIMIT 1");
      if (cursor.moveToFirst()) {
            strLatestTitle = cursor.getString(0);
      }
      cursor.close();*/
      RemoteViews remoteView = new RemoteViews(context.getPackageName(),
            R.layout.appwidget_layout);
      
      
      
      Intent launchAppIntent = new Intent(context, MainActivity.class);
      PendingIntent launchAppPendingIntent = PendingIntent.getActivity(context,
            0, launchAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
      remoteView.setOnClickPendingIntent(R.id.full_widget, launchAppPendingIntent);
      
      ComponentName tutListWidget = new ComponentName(context,
            RcWidgetProvider.class);
      appWidgetManager.updateAppWidget(tutListWidget, remoteView);
   }
}