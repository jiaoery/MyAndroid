package jixiang.com.myandroid.animator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import jixiang.com.myandroid.R;

public class TestObjectAnimatorAcitivity extends FragmentActivity{
	ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xml_for_anim);
		findViewById(R.id.scale_x).setVisibility(View.INVISIBLE);
		findViewById(R.id.scale_xy).setVisibility(View.GONE);
		imageView = (ImageView) findViewById(R.id.id_mv);
		findViewById(R.id.id_mv).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				scaleXY();
			}
		});
	}
	
	public void scaleXY(){
		ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "abc", 1.0f, 0.2f);
		animator.setDuration(4000);
		animator.start();
		animator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float vlaue = (Float)animation.getAnimatedValue();
				System.out.println("vlaue=" + vlaue);
				imageView.setAlpha(vlaue);
				imageView.setScaleX(vlaue);
				imageView.setScaleY(vlaue);
				/*int position = (int) (vlaue * 100);
				System.out.println("position=" + position);
				imageView.setX(position); 
				imageView.setY(position);*/
			}
		});
	}
}
