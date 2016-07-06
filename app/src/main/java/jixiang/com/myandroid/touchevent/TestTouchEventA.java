package jixiang.com.myandroid.touchevent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import jixiang.com.myandroid.R;

public class TestTouchEventA extends FragmentActivity{
	
	String TAG = "TestTouchEventA";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_touch_event);
		MyButton button = (MyButton) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG, "button click");
			}
		});
		
		button.setMyButtonClickListener(new MyButton.MyButtonClickListener() {
			
			@Override
			public void onMyClick(View v) {
				Log.d(TAG, "button MyButton Click");
			}
		});
		
		/*button.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.d(TAG, "button onTouch");
				return false;
			}
		});*/
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG, "dispatchTouchEvent");
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "onTouchEvent");
		return super.onTouchEvent(event);
	}
}
