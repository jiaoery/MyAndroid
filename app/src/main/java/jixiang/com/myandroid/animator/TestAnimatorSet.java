package jixiang.com.myandroid.animator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;

import jixiang.com.myandroid.R;


public class TestAnimatorSet extends FragmentActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anim_set);
	}
	public void togetherRun(View view){
		ObjectAnimator animatorX = ObjectAnimator.ofFloat(findViewById(R.id.id_ball), "scaleX", 1.0f, 2.0f).setDuration(2000);
		Animator animatorY = ObjectAnimator.ofFloat(findViewById(R.id.id_ball), "scaleY", 1.0f, 2.0f).setDuration(2000);
		Animator animatorA = ObjectAnimator.ofFloat(findViewById(R.id.id_ball), "alpha", 1.0f, 0.5f).setDuration(2000);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setInterpolator(new LinearInterpolator());
		animatorSet.playTogether(animatorX, animatorY, animatorA);
		animatorSet.start();
		animatorSet.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				
			}
		});
	}
	public void playWithAfter(View view){
		float cx = findViewById(R.id.id_ball).getX();
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(findViewById(R.id.id_ball), "scaleX",
				1.0f, 2f);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(findViewById(R.id.id_ball), "scaleY",
				1.0f, 2f);
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(findViewById(R.id.id_ball),
				"x",  cx ,  0f);
		ObjectAnimator anim4 = ObjectAnimator.ofFloat(findViewById(R.id.id_ball),
				"x", 0, cx);
		ObjectAnimator anim5 = ObjectAnimator.ofFloat(findViewById(R.id.id_ball), "scaleX",
				2.0f, 1.0f);
		ObjectAnimator anim6 = ObjectAnimator.ofFloat(findViewById(R.id.id_ball), "scaleY",
				2.0f, 1.0f);
		/**
		 * anim1，anim2,anim3同时执行
		 * anim4, anim5,anim6接着执行
		 */
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(anim1).with(anim2);
		animSet.play(anim2).with(anim3);
		animSet.play(anim4).after(anim3);
		animSet.play(anim4).with(anim5).with(anim6);
		animSet.setDuration(1000);
		animSet.start();
	}
}
