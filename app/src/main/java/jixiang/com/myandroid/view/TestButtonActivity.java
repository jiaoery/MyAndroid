package jixiang.com.myandroid.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import jixiang.com.myandroid.R;

public class TestButtonActivity extends Activity implements OnClickListener{
	
	private OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(TestButtonActivity.this, getString(R.string.netsted_object_action), Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		setContentView(R.layout.test_button);
		Button button3 = (Button) findViewById(R.id.button3);
		//内部类做事件监听
		button3.setOnClickListener(new MyOnClickListener());
		
		Button button4 = (Button) findViewById(R.id.button4);
		//匿名内部类做事件监听
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(TestButtonActivity.this, getString(R.string.amous_action), Toast.LENGTH_SHORT).show();
			}
		});
		//activity做事件监听
		Button button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(this);
		
		Button button6= (Button) findViewById(R.id.button6);
		button6.setOnClickListener(clickListener);
	}
	
	/**
	 * xml做事件监听
	 * @param view
	 */
	public void xmlClick(View view) {
		Toast.makeText(TestButtonActivity.this, getString(R.string.xml_action), Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 内部类做事件监听
	 */
	class MyOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Toast.makeText(TestButtonActivity.this, getString(R.string.netsted_action), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * activity做事件监听
	 */
	@Override
	public void onClick(View v) {
		Toast.makeText(TestButtonActivity.this, getString(R.string.activity_action), Toast.LENGTH_SHORT).show();
	}
}
