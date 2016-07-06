package jixiang.com.myandroid.broadcast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkReceiver extends BroadcastReceiver{
	
	public static final String NETWORK_ACTION = ConnectivityManager.CONNECTIVITY_ACTION;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(NETWORK_ACTION)) {
			System.out.println("Network changed");
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = connectivityManager.getActiveNetworkInfo(); //获取到网络信息
			if (info != null && info.isConnected()) {
				System.out.println("网络已连接");
				int type = info.getType(); //获取到网络的类型
				switch(type) {
				case ConnectivityManager.TYPE_MOBILE:
					System.out.println("type= TYPE_MOBILE" );
					break;
				case ConnectivityManager.TYPE_WIFI:
					System.out.println("type= TYPE_WIFI" );
					break;
				case ConnectivityManager.TYPE_ETHERNET:
					System.out.println("type= TYPE_ETHERNET" );
					break;
				case ConnectivityManager.TYPE_BLUETOOTH:
					System.out.println("type= TYPE_BLUETOOTH" );
					break;
				}
			} else {
				System.out.println("网络不可用");
			}
		}
	}

}
