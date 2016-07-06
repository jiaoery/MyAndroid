package jixiang.com.myandroid.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import jixiang.com.myandroid.service.MyService1;

public class BootBroadcastReceiver extends BroadcastReceiver{
	public static final String BOOT_ACTION = Intent.ACTION_BOOT_COMPLETED; //开机完成发出来的广播的action

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(BOOT_ACTION)) {
			System.out.println("received =" + BOOT_ACTION);
			//检测到开机完成的广播
			Intent intent2 = new Intent(context, MyService1.class);
			context.startService(intent2);
		}
		
	}

}
