package jixiang.com.myandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service{
	
	public static interface OnGetSumListener{
		void onGetSum(long sum);
	}

	OnGetSumListener listener;
	public void setOnGetSumListener(OnGetSumListener listener) {
		this.listener = listener;
		new MyThread(n).start(); //设置了监听之后，才开始计算
	}
	
	long sum = 0;
	int n;
	class MyBinder extends Binder{
		public MyService getService(){
			return MyService.this;
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		System.out.println("onBind");
		if(intent != null && intent.hasExtra("n")) {
			n = intent.getIntExtra("n", 0);
		}
		return new MyBinder();
	}
	
	class MyThread extends Thread{
		int n;
		public MyThread(int n) {
			this.n = n;
		}
		@Override
		public void run() {
			for(int i=1; i<= n; i++) {
				sum+= i;
				if(i %100000 == 0) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			//通知计算结束, 返回结果值
			if(MyService.this.listener != null) {
				listener.onGetSum(sum);
			}
		}
	}
	@Override
	public boolean onUnbind(Intent intent) {
		System.out.println("onUnbind");
		return super.onUnbind(intent);
	}
	public long getSum(){
		return sum;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("onCreate");
	}
	
	/**
	 * intent 启动service的Intent, 可以使用它来将参数传递到Service
	 * flags  Additional data about this start request. Currently either 0, START_FLAG_REDELIVERY(1), or START_FLAG_RETRY(2)
	 * startId 启动ID
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("onStartCommand flags=" + flags + ", startId=" + startId);
		/*if(intent != null && intent.hasExtra("age")) {
			int age = intent.getIntExtra("age", 0);
			System.out.println("age=" + age);
		}*/
		int flag = START_STICKY;
		return super.onStartCommand(intent, flag, startId);
	}
	
	
	@Override
	public void onDestroy() {
		System.out.println("onDestroy");
		super.onDestroy();
	}
}
