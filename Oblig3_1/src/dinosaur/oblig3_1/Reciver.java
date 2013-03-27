package dinosaur.oblig3_1;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class Reciver extends BroadcastReceiver {
	private int[] CATEGORIES = new int[] {
			R.array.category_telephony,
			R.array.category_network,
			R.array.category_div
	};

	//Date settings
	private Date d = new Date();
	private String s = d.toLocaleString();
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		boolean[] array = getData(context);
		
		String[] options = context.getResources().getStringArray(CATEGORIES[1]);
		
		/**
		 * Nettwork monitor state
		 */
		if(intent.getAction().equals("android.net.wifi.STATE_CHANGE") && array[5]){
			Toast.makeText(context, options[0], Toast.LENGTH_SHORT).show();
			add(options[0], s, "Nettverk", context);
		}
		else if(intent.getAction().equals("android.net.wifi.NETWORK_IDS_CHANGED")&& array[6]){
			Toast.makeText(context, options[1], Toast.LENGTH_SHORT).show();
			add(options[1], s, "Nettverk", context);
		}
		else if(intent.getAction().equals("android.net.wifi.supplicant.CONNECTION_CHANGE")&& array[7]){
			Toast.makeText(context, options[2], Toast.LENGTH_SHORT).show();
			add(options[2], s, "Nettverk", context);
		}
		else if(intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")&& array[8]){
			Toast.makeText(context, options[3], Toast.LENGTH_SHORT).show();
			add(options[3], s, "Nettverk", context);
		}
		else if(intent.getAction().equals("android.bluetooth.device.action.FOUND")&& array[9]){
			Toast.makeText(context, options[4], Toast.LENGTH_SHORT).show();
			add(options[4], s, "Nettverk", context);
		}
		
		options = context.getResources().getStringArray(CATEGORIES[0]);
		
		/**
		 * Telefoni monitor state
		 */
		if(intent.getAction().equals("android.provider.Telephony.SIM_FULL")&& array[0]){
			Toast.makeText(context, options[0], Toast.LENGTH_SHORT).show();
			add(options[0], s, "Telefoni", context);
		}
		else if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")&& array[1]){		
			Toast.makeText(context, options[1], Toast.LENGTH_SHORT).show();
			
			Object message[] = (Object[])intent.getExtras().get("pdus");
			android.telephony.SmsMessage sms = android.telephony.SmsMessage.createFromPdu((byte[])message[0]);
			String details = "Fra: " + sms.getOriginatingAddress() + " SMS: "  + sms.getDisplayMessageBody().toString();
			
			add(options[1], s, "Telefoni", context, details);
			
		}
		else if(intent.getAction().equals("android.provider.Telephony.SMS_REJECTED")&& array[2]){
			Toast.makeText(context, options[2], Toast.LENGTH_SHORT).show();
			add(options[2], s, "Telefoni", context);
		}
		else if(intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")&& array[3]){
			Toast.makeText(context, options[3], Toast.LENGTH_SHORT).show();
			
			TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			String number = "Fra: " + tm.getLine1Number().toString();
			add(options[3], s, "Telefoni", context, number);
					
		}
		else if(intent.getAction().equals("android.intent.action.NEW_VOICEMAIL")&& array[4]){
			Toast.makeText(context, options[4], Toast.LENGTH_SHORT).show();
			add(options[4], s, "Telefoni", context);
		}
		
		options = context.getResources().getStringArray(CATEGORIES[2]);
		
		/**
		 * Div instillinger
		 */
		if(intent.getAction().equals("android.intent.action.HEADSET_PLUG")&& array[10]){
			Toast.makeText(context, options[0], Toast.LENGTH_SHORT).show();
			add(options[0], s, "Diverse", context);
		}
		else if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")&& array[11]){
			Toast.makeText(context, options[1], Toast.LENGTH_SHORT).show();
			add(options[1], s, "Diverse", context);
		}
		else if(intent.getAction().equals("android.intent.action.AIRPLANE_MODE")&& array[12]){
			Toast.makeText(context, options[2], Toast.LENGTH_SHORT).show();
			add(options[2], s, "Diverse", context);
		}
		else if(intent.getAction().equals("android.intent.action.WALLPAPER_CHANGED")&& array[13]){
			Toast.makeText(context, options[3], Toast.LENGTH_SHORT).show();
			add(options[3], s, "Diverse", context);
		}
		else if(intent.getAction().equals("android.intent.action.BATTERY_OKAY")&& array[14]){
			Toast.makeText(context, options[4], Toast.LENGTH_SHORT).show();
			add(options[4], s, "Diverse", context);
		}
	}//end onRecive
	
	/**
	 * 
	 * @param msg
	 * @param date
	 * @param catagory
	 * @param context
	 */
	public void add(String msg, String date, String catagory, Context context){
		
		/* ContentValues values = new ContentValues();
		 values.put("content", msg );
		 values.put("datetime", date);
		 values.put("category", catagory);
	
		  
		 context.getContentResolver().insert(DatabaseProvider.CONTENT_URI, values);
		 context.getContentResolver().notifyChange(DatabaseProvider.CONTENT_URI, null);*/
		add(msg, date, catagory, context, "");
	}
	
	/**
	 * 
	 * @param msg
	 * @param date
	 * @param catagory
	 * @param context
	 * @param details
	 */
	public void add(String msg, String date, String catagory, Context context,String details){	
		
		 ContentValues values = new ContentValues();
		 values.put("content", msg );
		 values.put("datetime", date);
		 values.put("category", catagory);
		 values.put("details", details);
		  
		 context.getContentResolver().insert(DatabaseProvider.CONTENT_URI, values);
		 context.getContentResolver().notifyChange(DatabaseProvider.CONTENT_URI, null);
	}
	
	private boolean[] getData(Context context) {
		  Cursor result = context.getContentResolver().query(DatabaseProvider.SETTINGS_URI, new String[]{ "setting" }, null, null, null);
		  boolean settings[] = new boolean[15];
		  for (int i = 0; i < 15; i++) {
		   settings[i] = false;
		  }
		  if (result.getCount() > 0) {
		   result.moveToFirst();
		   do {
		    settings[result.getInt(0)] = true;
		   } while (result.moveToNext());
		  }
		  return settings;
		 }
}//end Reciver.class
