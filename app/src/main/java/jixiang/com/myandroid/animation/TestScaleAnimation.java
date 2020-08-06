package jixiang.com.myandroid.animation;


import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import jixiang.com.myandroid.R;

public class TestScaleAnimation extends FragmentActivity{

	ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_animation);
		imageView = (ImageView) findViewById(R.id.image);
		/*Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
		
		
		imageView.setAnimation(animation);
		imageView.startAnimation(animation);*/
	}
	
	boolean flag = false;
	@Override
	protected void onResume() {
		super.onResume();
		ViewTreeObserver observer = imageView.getViewTreeObserver();
		observer.addOnPreDrawListener(new OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				if(!flag) {
					flag = true;
					int width = imageView.getWidth();
					int height = imageView.getHeight();
					int x = (int) imageView.getX();
					int y = (int) imageView.getY();
					System.out.println("width=" + width + ", height=" + height);
					ScaleAnimation animation = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f, width/2, height/2);
					animation.setRepeatCount(Animation.INFINITE);
					animation.setRepeatMode(Animation.REVERSE);
					animation.setDuration(3000);
					imageView.setAnimation(animation);
					imageView.startAnimation(animation);
				}
				return true;
			}
		});
		
	}
}
