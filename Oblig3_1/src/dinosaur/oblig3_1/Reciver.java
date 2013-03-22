package dinosaur.oblig3_1;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.text.method.DateTimeKeyListener;
import android.widget.Toast;

public class Reciver extends BroadcastReceiver {


	Date d = new Date();
	String s = d.toGMTString();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		/**
		 * Nettwork monitor state
		 */
		if(intent.getAction().equals("android.net.wifi.STATE_CHANGE")){
			Toast.makeText(context, "Wifi state changed", Toast.LENGTH_SHORT).show();
			add("Wifi State changed", s, "Nettverk", context);
		}
		else if(intent.getAction().equals("android.net.wifi.NETWORK_IDS_CHANGED")){
			Toast.makeText(context, "Wifi network ids changed", Toast.LENGTH_SHORT).show();
			add("Wifi Netword IDS changed", s, "Nettverk", context);
		}
		else if(intent.getAction().equals("android.net.wifi.supplicant.CONNECTION_CHANGE")){
			Toast.makeText(context, "Connection changed", Toast.LENGTH_SHORT).show();
			add("Wifi Connection Changed", s, "Nettverk", context);
		}
		else if(intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")){
			Toast.makeText(context, "Bluetooth state changed", Toast.LENGTH_SHORT).show();
			add("Bluethooth state changed", s, "Nettverk", context);
		}
		else if(intent.getAction().equals("android.bluetooth.device.action.FOUND")){
			Toast.makeText(context, "Bluetooth found", Toast.LENGTH_SHORT).show();
			add("Bluethooth device found", s, "Nettverk", context);
		}
		
		/**
		 * Telefoni monitor state
		 */
		if(intent.getAction().equals("android.provider.Telephony.SIM_FULL")){
			Toast.makeText(context, "Sim card full", Toast.LENGTH_SHORT).show();
			add("Simcard full", s, "Telefoni", context);
		}
		else if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){		
			Toast.makeText(context, "SMS Recived capture", Toast.LENGTH_SHORT).show();
			add("SMS Recived", s, "Telefoni", context);
			
		}
		else if(intent.getAction().equals("android.provider.Telephony.SMS_REJECTED")){
			Toast.makeText(context, "SMS Rejected", Toast.LENGTH_SHORT).show();
			add("SMS Rejected", s, "Telefoni", context);
		}
		else if(intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")){
			Toast.makeText(context, "New outgoing call", Toast.LENGTH_SHORT).show();
			add("New Outgoing call", s, "Telefoni", context);
		}
		else if(intent.getAction().equals("android.intent.action.NEW_VOICEMAIL")){
			Toast.makeText(context, "New Voicemail", Toast.LENGTH_SHORT).show();
			add("New Voicemail", s, "Telefoni", context);
		}
		
		/**
		 * Div instillinger
		 */
		if(intent.getAction().equals("android.intent.action.HEADSET_PLUG")){
			Toast.makeText(context, "Headset pluged", Toast.LENGTH_SHORT).show();
			add("Headset plug", s, "Diverse", context);
		}
		else if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
			Toast.makeText(context, "Boot Completed", Toast.LENGTH_SHORT).show();
			add("Boot completed", s, "Diverse", context);
		}
		else if(intent.getAction().equals("android.intent.action.AIRPLANE_MODE")){
			Toast.makeText(context, "Airplane mode changed", Toast.LENGTH_SHORT).show();
			add("Airplane mode changed", s, "Diverse", context);
		}
		else if(intent.getAction().equals("android.intent.action.WALLPAPER_CHANGED")){
			Toast.makeText(context, "Wallpaper changed", Toast.LENGTH_SHORT).show();
			add("Wallpaper changed", s, "Diverse", context);
		}
		else if(intent.getAction().equals("android.intent.action.BATTERY_OKAY")){
			Toast.makeText(context, "Battery ok", Toast.LENGTH_SHORT).show();
			add("Battery  OKEY", s, "Diverse", context);
		}
	}//end onRecive
	
	public void add(String msg, String date, String catagory, Context context ){
		
		 ContentValues values = new ContentValues();
		 values.put("content", msg );
		 values.put("datetime", date);
		 values.put("category", catagory);
		  
		 context.getContentResolver().insert(DatabaseProvider.CONTENT_URI, values);
	}

}//end Reciver.class
