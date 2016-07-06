package jixiang.com.myandroid.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MyButton extends Button{
	
	public static interface MyButtonClickListener{
		void onMyClick(View v);
	}
	
	GestureDetector detector;
	String TAG = "MyButton";
	public MyButtonClickListener clickListener;
	
	public MyButton(Context context) {
		this(context, null);
	}
	
	public MyButton(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.buttonStyle);
	}
	
	public MyButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		detector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				if(MyButton.this.clickListener != null) {
					MyButton.this.clickListener.onMyClick(MyButton.this);
				}
				return true;
			}
			@Override
			public void onShowPress(MotionEvent e) {
			}
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				return false;
			}
			@Override
			public void onLongPress(MotionEvent e) {
			}
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				return false;
			}
			@Override
			public boolean onDown(MotionEvent e) {
				return false;
			}
		});
	}
	
	public void setMyButtonClickListener(MyButtonClickListener clickListener){
		this.clickListener = clickListener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "onTouchEvent");
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
}
