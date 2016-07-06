package jixiang.com.myandroid.notification;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RemoteViews;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.view.TestButtonActivity;

public class TestNotificationActivity extends Activity implements OnClickListener{
	public static final int NOTIFICATION_ID1 = 0X1244345;
	public static  int NOTIFICATION_ID2 = 0X14345;
	NotificationManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_notification);
		findViewById(R.id.show_notification).setOnClickListener(this);
		findViewById(R.id.cancel_notification).setOnClickListener(this);
		findViewById(R.id.show_custom_notification).setOnClickListener(this);
		findViewById(R.id.cancel_custom_notification).setOnClickListener(this);
		findViewById(R.id.showpop).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.show_notification:
			showNotification();
			break;
		case R.id.cancel_notification:
			cancelNotification(NOTIFICATION_ID1);
			break;
		case R.id.show_custom_notification:
			showCustomNotification();
			break;
		case R.id.cancel_custom_notification:
			cancelNotification(NOTIFICATION_ID2);
			break;
		case R.id.showpop:
			showPop();
			break;
		}
	}
	
	private void showPop(){
		PopupWindow popupWindow = new PopupWindow(this);
		popupWindow.setWindowLayoutMode(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);//设置可点击
		popupWindow.setFocusable(true); //可聚焦
		popupWindow.setOutsideTouchable(true);//设置外部可点击
		ColorDrawable colorDrawable = new ColorDrawable(0x66FFFFFF);
		popupWindow.setBackgroundDrawable(colorDrawable); //设置背景
		
		//设置弹窗的内容
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.test_linearlayout, null);
		view.findViewById(R.id.button_1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("Click Button1");
			}
		});
		popupWindow.setContentView(view);
		
		//显示弹窗
		//popupWindow.showAsDropDown(findViewById(R.id.show_notification), 100, 200);
		popupWindow.showAtLocation(findViewById(R.id.parent), Gravity.CENTER, 0, 0);
	}
	
	
	public void showNotification(){
		//先生成一个通知
		Notification notification = new Notification();
		notification.tickerText = "网络连接中断";
		notification.icon = R.drawable.a011;
		notification.when = System.currentTimeMillis();
		//设置属性
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		//notification.flags |= Notification.DEFAULT_SOUND;
		notification.flags |= Notification.DEFAULT_LIGHTS;
		notification.sound = Uri.parse("file:///sdcard/hai.mp3");
		//点击后对应的操作
//		//intent 表明点击之后将要执行的动作
//		Intent intent = new Intent(this, TestButtonActivity.class);
//		//intent.setAction(Settings.ACTION_WIFI_SETTINGS);
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 123, intent,Intent.FLAG_ACTIVITY_NEW_TASK);
//		notification.bu
//		notification.setLatestEventInfo(this, "当前网络连接中断", "网络不可用，请设置网络", pendingIntent);
		//显示通知
		manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Object object = getSystemService(Context.NOTIFICATION_SERVICE);
		if(object instanceof NotificationManager) {
			manager = (NotificationManager) object;
		}
		manager.notify(NOTIFICATION_ID1, notification);
	}
	public void cancelNotification(int id){
		manager.cancel(id);
	}
	
	Notification notification;
	int progress = 0;
	int max = 100;
	public void showCustomNotification(){
		notification = new Notification();
		notification.tickerText = "网络连接中断";
		//设置属性
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		//notification.flags |= Notification.DEFAULT_LIGHTS;
		notification.when = System.currentTimeMillis();
		notification.flags |= Notification.DEFAULT_VIBRATE;
		notification.sound = null;
		notification.icon = R.drawable.a007;
		
		RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_layout);
		remoteViews.setImageViewResource(R.id.notify_icon, R.drawable.a007);
		remoteViews.setTextViewText(R.id.notify_titile, "下载百分比");
		remoteViews.setProgressBar(R.id.notify_progress, max, progress, false);
		//点击后对应的操作
		//intent 表明点击之后将要执行的动作
		Intent intent = new Intent();
		intent.setAction(Settings.ACTION_WIFI_SETTINGS);
		//pendingIntent是一种条件执行，当满足一定条件才会执行，不像intent会马上执行
		//在此处，只有在点击了这个通知的时候，才会执行，执行什么，此处是执行打开某个界面，哪个界面，intent里面决定的界面,这个界面是打开wifi设置界面
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		notification.contentView = remoteViews;
		//contentIntent是一个pendingIntent 也就是点击的内容的动作
		notification.contentIntent = pendingIntent;
		//显示通知
		manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(NOTIFICATION_ID2, notification);
		new MyThread().start();
	}
	
	class MyThread extends Thread{
		@Override
		public void run() {
			while(progress < max) {
				Message message = new Message();
				message.what = 123;
				handler.sendMessage(message);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			progress = 0;
		}
	}
	
	//消息处理器 发送消息和处理消息
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch(msg.what) {
			case 123:
				progress +=2;
				notification.flags |= Notification.FLAG_AUTO_CANCEL;
				notification.contentView.setProgressBar(R.id.notify_progress, max, progress, false);
				//再次通知，实际上刷新这个通知
				//NOTIFICATION_ID2 是用来决定是哪个通知
				//如果此ID的通知不存在，那么notify 是会新创建一个通知
				//如果已经存在，那么就是后新的notification刷新这个通知
				manager.notify(NOTIFICATION_ID2, notification);
				break;
			}
			return true;
		}
		
	});
	
}
