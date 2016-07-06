package jixiang.com.myandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService1 extends Service{

	public OnGetSumListener listener;
	
	int n;
	long sum;
	public MyServiceAIDL.Stub mybinder =  new MyServiceAIDL.Stub() {

		@Override
		public long onGetSum() throws RemoteException {
			return sum;
		}

		@Override
		public void setOnGetSumListener(OnGetSumListener listener1) throws RemoteException {
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA");
			listener = listener1;
			new MyThread(n).start(); //仅当监听设置了之后，才执行计算操作
		}
	};
	@Override
	public void onCreate() {
		System.out.println("MyService1 onCreate");
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		System.out.println("MyService1 onBind");
		if(intent.hasExtra("n")) {
			n = intent.getIntExtra("n", 0);
		}
		return mybinder;
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
			System.out.println("count over");
			if(listener != null) {
				System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBB");
				try {
					System.out.println("CCCCCCCCCCCCCCCCCCC");
					listener.onGetSum1(sum);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("listener is null");
			}
		}
	}

}
