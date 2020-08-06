package jixiang.com.myandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;

public abstract class BaseActivity extends AppCompatActivity{
	//自定义的退出程序的广播
	public static final String EXIT_ACTION = "com.android.learning.EXIT_APP";
	public static final String NETWORK_CHANGE = ConnectivityManager.CONNECTIVITY_ACTION;
	MyReceiver exitReceiver;
	public OnNetworkStatusChange networkStatusChange;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setTranslucentStatus();
		exitReceiver = new MyReceiver();
		networkStatusChange = new OnNetworkStatusChange() {
			
			@Override
			public void onNetWorkStatusChanged(boolean isAviable, int type) {
				onNetWorkStatusChanged1(isAviable, type);
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(EXIT_ACTION);
		filter.addAction(NETWORK_CHANGE);
		registerReceiver(exitReceiver, filter); //注册广播接收器
		System.out.println("进入页面："+this.toString());
	}

	// 需要setContentView之前调用
	private void setTranslucentStatus() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//			 透明导航栏
//			getWindow().addFlags(
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			SystemStatusManager tintManager = new SystemStatusManager(this);
			tintManager.setStatusBarTintEnabled(true);
			// 设置状态栏的颜色
			tintManager.setStatusBarTintResource(R.color.red);
			getWindow().getDecorView().setFitsSystemWindows(true);

		}
	}
	
	
	class MyReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(EXIT_ACTION)) {
				//接收到退出程序的广播
				finish();
			} else if(intent.getAction().equals(NETWORK_CHANGE)) {
				//网络改变
				System.out.println("NETWORK_CHANGE");
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo info = connectivityManager.getActiveNetworkInfo();
				if(info != null && info.isConnected()) {
					int type = info.getType();
					networkStatusChange.onNetWorkStatusChanged(true, type);
				} else {
					networkStatusChange.onNetWorkStatusChanged(false, -1);
				}
			}
		}
	}
	
	public void onNetWorkStatusChanged1(boolean isAvailable, int type){
		
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(exitReceiver);
		System.out.println(BaseActivity.class.getName() + " is finishing");
	}
	
}

