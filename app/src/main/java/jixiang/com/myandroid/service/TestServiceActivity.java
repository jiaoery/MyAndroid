package jixiang.com.myandroid.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import jixiang.com.myandroid.BaseActivity;
import jixiang.com.myandroid.R;


public class TestServiceActivity extends BaseActivity implements OnClickListener{
 
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_service);
		intent = new Intent(this, MyService.class);
		intent.putExtra("age", 20);
		intent.putExtra("n", 1000000);
		findViewById(R.id.start_service).setOnClickListener(this);
		findViewById(R.id.stop_service).setOnClickListener(this);
		findViewById(R.id.bind_service).setOnClickListener(this);
		findViewById(R.id.unbind_service).setOnClickListener(this);
		findViewById(R.id.get_sum).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.start_service:
			startService(intent);
			break;
		case R.id.stop_service:
			stopService(intent);
			break;
		case R.id.bind_service:
			bind();
			break;
		case R.id.unbind_service:
			unbind();
			break;
		case R.id.get_sum:
			getSum();
			break;
		}
	}
	MyService myService;
	MyConnection connection  =new MyConnection();
	class MyConnection implements ServiceConnection{
		/**
		 * 当service跟activity关联上的时候回调这个方法
		 * ComponentName  启动这个服务的组件的名称
		 * IBinder 就是service 里面的onBind方法返回的Binder对象
		 */
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			myService = ((MyService.MyBinder)service).getService();
			// 设置结果返回监听
			myService.setOnGetSumListener(new MyService.OnGetSumListener() {
				//当结果值计算出来之后，就会回调此方法
				@Override
				public void onGetSum(long sum) {
					System.out.println("the final sum is =" + sum);
					unbindService(connection); //解除绑定
				}
			});
			//System.out.println("sum=" + myService.getSum());
		}
		//断开连接的时候，回调这个方法
		//正常情况下这个方法不会回调，仅当在service因为内存不足被杀掉的时候调用
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
		
	}
	public void getSum(){
		if(myService != null) {
			System.out.println("sum=========" + myService.getSum());
		}
	}
	
	public void unbind(){
		//处理可能会出现的异常
		try{
			unbindService(connection);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public void bind(){
		bindService(intent, connection, Context.BIND_AUTO_CREATE);
	}
}
