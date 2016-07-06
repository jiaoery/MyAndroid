package jixiang.com.myandroid.animation;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import jixiang.com.myandroid.R;

public class TestRotate extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_rotate);
		ImageView imageView = (ImageView) findViewById(R.id.image);
		RotateAnimation rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(this, R.anim.rotate);
		imageView.setAnimation(rotateAnimation);
		imageView.startAnimation(rotateAnimation);
	}
}
