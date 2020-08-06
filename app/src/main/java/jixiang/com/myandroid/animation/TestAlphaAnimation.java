package jixiang.com.myandroid.animation;


import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import jixiang.com.myandroid.R;

public class TestAlphaAnimation extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_animation);
		ImageView imageView = (ImageView) findViewById(R.id.image);
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
		imageView.setAnimation(animation);
		imageView.startAnimation(animation);
	}
}
