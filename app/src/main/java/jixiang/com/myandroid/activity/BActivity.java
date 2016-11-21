package jixiang.com.myandroid.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import jixiang.com.myandroid.R;

public class BActivity extends Activity {
	long value; // 保存从A传递过来的N值
	TextView valueTextView;
	TextView running;
	TextView result;
	long fab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获取到从A传递过来的值
		if (getIntent() != null && getIntent().hasExtra("value")) {
			value = getIntent().getLongExtra("value", 0);
		}
		setContentView(R.layout.b_activity);
		valueTextView = (TextView) findViewById(R.id.value);
		running = (TextView) findViewById(R.id.running);
		result = (TextView) findViewById(R.id.result);

		// 将获取到的value设置到当前的valueTextView控件上去
		valueTextView.setText(value + "");
		running.setVisibility(View.VISIBLE);
		new MyThread(value).start();
	}
	Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 240:
				//已经计算到结果了
				result.setText(fab + "");
				//将值返回给AActivity
				Message message = new Message();
				message.what = 251;
				handler.sendMessageDelayed(message, 1000);
				break;
			case 251:
				//将值返回
				Intent data = new Intent();
				data.putExtra("result", fab);
				setResult(RESULT_OK, data);
				finish();
				break;
			}
			return true;
		}
	});

	/**
	 * 内部类
	 */
	class MyThread extends Thread {
		long n;

		public MyThread(long n) {
			this.n = n;
		}

		@Override
		public void run() {
			fab = fabFor(n);
			Message message = new Message();
			message.what = 240;
			handler.sendMessageDelayed(message, 1000);
		}
	}


	// 递归求斐波拉契数列的值
	public long fab(long n) {
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		return fab(n - 1) + fab(n - 2);
	}
	public long fabFor(long n) {
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		long fn = 0;
		long fn_1 = 1;
		long fn_2 = 0;
		for (long i = 2; i <= n; i++) {
			fn = fn_1 + 2*fn_2;
			fn_2 = fn_1;
			fn_1 = fn;
		}
		return fn;
	}
}
