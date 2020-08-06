package jixiang.com.myandroid.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.widget.ImageView;

import jixiang.com.myandroid.R;


public class TestFrameAnimation extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_animation);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ImageView imageView = (ImageView) findViewById(R.id.image);
		imageView.setBackgroundResource(R.drawable.running_horses);
		//AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.running_horses);
		AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
		animationDrawable.start();
	}
}
