package jixiang.com.myandroid.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MiddleLLayout extends LinearLayout {
	String TAG = "MiddleLLayout";

	public MiddleLLayout(Context context) {
		this(context, null);
	}

	public MiddleLLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MiddleLLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// 事件分发
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG, "dispatchTouchEvent");
		return super.dispatchTouchEvent(ev);
	}

	// 事件拦截
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(TAG, "onInterceptTouchEvent");
		return super.onInterceptTouchEvent(ev);
	}

	// 事件处理
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "onTouchEvent");
		return super.onTouchEvent(event);
	}
}
