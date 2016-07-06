package jixiang.com.myandroid.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import jixiang.com.myandroid.R;

public class FirstActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("FirstActivity----->onCreate");
		setContentView(R.layout.first_layout);
		findViewById(R.id.pause).setOnClickListener(this);
		findViewById(R.id.pause_stop).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.pause:
			//跳转到一个非全屏的activity 或者一个全透明的activity
			Intent intent = new Intent(this, TransparentActivity.class);
			startActivity(intent);
			break;
		case R.id.pause_stop:
			Intent intent2 = new Intent(this, SecondActivity.class);
			startActivity(intent2);
			break;
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("FirstActivity----->onStart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("FirstActivity----->onResume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("FirstActivity----->onPause");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("FirstActivity----->onStop");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println("FirstActivity----->onRestart");
	}
}
