package jixiang.com.myandroid.animator;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;

import jixiang.com.myandroid.R;

public class XmlAnimatorActivity extends FragmentActivity implements
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xml_for_anim);
		findViewById(R.id.scale_x).setOnClickListener(this);
		findViewById(R.id.scale_xy).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.scale_x:
			scaleX();
			break;
		case R.id.scale_xy:
			scaleXY();
			break;
		}
	}

	public void scaleX() {
		/**
		 * android:propertyName="scaleX"
		 * android:interpolator="@android:anim/linear_interpolator"
		 * android:duration="3000" android:valueType="floatType"
		 * android:valueFrom="1.0" android:valueTo="0.3">
		 */
		ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.id_mv), "scaleX", 1.0f, 0.3f).setDuration(3000);
		animator.setInterpolator(new LinearInterpolator());
		animator.start();
		/*
		 * Animator animator = AnimatorInflater.loadAnimator(this,
		 * R.animator.scalex); animator.setTarget(findViewById(R.id.id_mv));
		 * animator.start();
		 */
	}

	public void scaleXY() {
		Animator animator = AnimatorInflater.loadAnimator(this,
				R.animator.scale_xy);
		animator.setTarget(findViewById(R.id.id_mv));
		animator.start();
	}
}
