package jixiang.com.myandroid.bitmap;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import jixiang.com.myandroid.R;

public class PreviewImageActivity extends FragmentActivity implements OnClickListener{

	ImageView imageView;
	private float scaleSize = 1.0f;
	Bitmap bitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_preview);
		imageView = (ImageView) findViewById(R.id.image);
		/*imageView.setDrawingCacheEnabled(true);
		imageView.buildDrawingCache();*/
		findViewById(R.id.top).setOnClickListener(this);
		findViewById(R.id.left).setOnClickListener(this);
		findViewById(R.id.right).setOnClickListener(this);
		findViewById(R.id.bottom).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.top:
			scale();
			break;
		case R.id.left:
			counter(1);
			break;
		case R.id.right:
			counter(0);
			break;
		case R.id.bottom:
			scaleSmall();
			break;
		}
	}
	
	public void scale(){
		if(scaleSize <= 3.0) {
			ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageView, "scaleX", scaleSize, (float)(scaleSize + 0.2));
			ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView, "scaleY", scaleSize, (float)(scaleSize + 0.2));
			AnimatorSet animatorSet = new AnimatorSet();
			animatorSet.setInterpolator(new LinearInterpolator());
			animatorSet.setDuration(1000);
			animatorSet.playTogether(animatorX, animatorY);
			animatorSet.start();
			scaleSize += 0.2f;
		}
	}
	
	public void scaleSmall(){
		if(scaleSize >= 0.2f) {
			ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageView, "scaleX", scaleSize, (float)(scaleSize - 0.2));
			ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView, "scaleY", scaleSize, (float)(scaleSize - 0.2));
			AnimatorSet animatorSet = new AnimatorSet();
			animatorSet.setInterpolator(new LinearInterpolator());
			animatorSet.setDuration(1000);
			animatorSet.playTogether(animatorX, animatorY);
			animatorSet.start();
			scaleSize -= 0.2f;
		}
	}
	
	public void counter(int position){
		/*ObjectAnimator animatorX;
		if(position == 0) {
			animatorX = ObjectAnimator.ofFloat(imageView, "abc", 0, 90);
		} else {
			animatorX = ObjectAnimator.ofFloat(imageView, "abc", 0, -90);
		}
		animatorX.start();
		animatorX.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (Float) animation.getAnimatedValue();
				imageView.setRotation(value);
			}
		});*/
		bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
		Matrix matrix = new Matrix(); //图形变换矩阵
		if(position == 0) {
			//旋转90度, 以图片为中心点
			matrix.setRotate(90, bitmap.getWidth() /2, bitmap.getHeight()/2);
		} else {
			matrix.setRotate(-90, bitmap.getWidth() /2, bitmap.getHeight()/2);
		}
		try{
			//新生成一个位图，使用这个变换矩阵对新的位图进行旋转
			Bitmap bm1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			imageView.setImageBitmap(bm1); //将这个位图设置到控件上
			if(bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle(); //把旧的位图回收掉
			}
		} catch(OutOfMemoryError error) {
			error.printStackTrace();
		}
	}
}
