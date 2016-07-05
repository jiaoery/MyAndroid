package jixiang.com.myandroid.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import jixiang.com.myandroid.R;


public class TestProgressBarActivity extends Activity {
	/**
	 * 更新进度条的消息
	 */
	public static final int UPDATE_PROGRESS = 0X123;
	ProgressBar bar1;
	ProgressBar bar2;

	/**
	 * 消息处理器,用来发送、接收，处理消息
	 * handleMessage 是主线程UI线程里面运行的
	 */
	public Handler handler = new Handler(new Handler.Callback() {
		/**
		 * 消息处理方法
		 */
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_PROGRESS:
				//设置进度值
				bar1.setProgress((bar1.getProgress() + 2) % (bar1.getMax()+1));
				bar2.setProgress((bar2.getProgress() + 2) % (bar2.getMax() +1));
				
				//设置次要进度值
				bar2.setSecondaryProgress((bar2.getSecondaryProgress() + 2) % (bar2.getMax() +1));
				break;
			}
			return true;
		}
	});
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_progressbar);
		bar1 = (ProgressBar) findViewById(R.id.progress1);
		bar2 = (ProgressBar) findViewById(R.id.progress2);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Message message = new Message();
					//what表明此消息是属于什么类别
					//这个消息究竟是要干嘛的
					message.what = UPDATE_PROGRESS;
					//发送message给handler处理
					handler.sendMessage(message);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
