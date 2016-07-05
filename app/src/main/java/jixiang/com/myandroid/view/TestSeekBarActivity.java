package jixiang.com.myandroid.view;


import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import jixiang.com.myandroid.R;

public class TestSeekBarActivity extends Activity{
	SeekBar seekBar1;

	SeekBar seekBar2;
	
	RatingBar ratingBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_seekbar);
		seekBar1 = (SeekBar) findViewById(R.id.seekbar1);
		seekBar2 = (SeekBar) findViewById(R.id.seekbar2);
		ratingBar = (RatingBar) findViewById(R.id.ratingbar);
		
		/**
		 * seekBar的进度值改变的监听
		 */
		seekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			/**
			 * 停止触摸
			 */
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				System.out.println("stop");
			}
			
			/**
			 * 开始触控到滑块
			 */
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				System.out.println("start");
			}
			
			/**
			 * fromUser 是否是用户拖动进度值触发的
			 */
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				System.out.println("progress=" + progress+ ", fromUser=" + fromUser);
			}
		});
		
		seekBar2.setProgress(20);
		
		/**
		 * 评分值改变
		 */
		ratingBar.setOnRatingBarChangeListener(new  OnRatingBarChangeListener() {
			
			/**
			 * ratingBar 控件
			 * rating 评分值
			 * fromUser 是否是用户操作
			 */
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				System.out.println("rating=" + rating + ", fromUser="+ fromUser);
			}
		});
	}
}
