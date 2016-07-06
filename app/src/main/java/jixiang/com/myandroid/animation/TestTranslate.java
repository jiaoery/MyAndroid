package jixiang.com.myandroid.animation;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import jixiang.com.myandroid.R;


public class TestTranslate extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_animation);
		ImageView imageView = (ImageView) findViewById(R.id.image);
		TranslateAnimation animation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.translate_);
		imageView.setAnimation(animation);
		imageView.startAnimation(animation);
	}
}
