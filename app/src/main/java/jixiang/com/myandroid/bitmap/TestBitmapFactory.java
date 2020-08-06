package jixiang.com.myandroid.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import androidx.fragment.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import jixiang.com.myandroid.R;


public class TestBitmapFactory extends FragmentActivity{

	ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout linearLayout = new LinearLayout(this);
		imageView = new ImageView(this);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
		imageView.setLayoutParams(layoutParams);
		linearLayout.addView(imageView);
		setContentView(linearLayout);
		imageView.setImageBitmap(decodeFileFrom(Environment.getExternalStorageDirectory().getPath() +"/file.png"));
	}
	
	public Bitmap decodeFileFrom(String path){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false; //设置这个属性，那么不会真正的解码bitmap， 而会返回一个null
		options.inDensity = 1; //密度
		options.inDither = false; //抖动
		options.inPreferQualityOverSpeed = false; //质量和解码的速度的优先
		options.inScaled = true; //压缩
		options.inDensity = 100; //像素压缩比
		options.inTargetDensity = 100; //目标的压缩比
		options.inSampleSize = 1; //缩放
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fruit_banana, options);
		Matrix matrix = new Matrix();
		/*int width = options.outWidth;
		int height = options.outHeight;
		System.out.println("width= "+ width + ", height=" + height);
		//
		Matrix matrix = new Matrix();
		matrix.setScale(2f, 2f);
		Bitmap bitmap2 = Bitmap.createBitmap(BitmapFactory.decodeFile(path), 0, 0, width, height, matrix, false);*/
		
		return bitmap;
	}
}
