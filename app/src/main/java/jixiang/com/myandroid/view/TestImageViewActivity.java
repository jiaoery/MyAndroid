package jixiang.com.myandroid.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;

import jixiang.com.myandroid.BaseActivity;
import jixiang.com.myandroid.R;


public class TestImageViewActivity extends BaseActivity{
	ImageView imageView;
	private int width;
	private int height;
	Bitmap bitmap;
	boolean isMeasured = false;
	//图片资源
	int[] images = {R.drawable.aa4, R.drawable.a005, R.drawable.a006, R.drawable.a007, R.drawable.a008};
	int index = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_imageview);
		imageView = (ImageView) findViewById(R.id.image1);
		//做刮奖层
		//首先需要拿到控件的宽高
		ViewTreeObserver observer = imageView.getViewTreeObserver();
		//在绘制这个控件的时候，会回调这个方法
		//实际上当imageView的尺寸改变，也会回调
		observer.addOnPreDrawListener(new OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				if(!isMeasured) {
					isMeasured = true;
					width = imageView.getMeasuredWidth();
					height = imageView.getMeasuredHeight();
					System.out.println("ImageView width=" + width + ", height=" + height);
					setCover();
					imageView.setOnTouchListener(new MyTouchListener());
				}
				//事件处理完毕，返回true，事件不再下发
				return true;
			}
		});
		findViewById(R.id.button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setCover();
				index++;
				if(index > images.length-1) {
					index = 0;
				}
				imageView.setBackgroundResource(images[index]);
			}
		});
	}
	
	
	private int x;
	private int y;
	
	//刮奖需要借助OnTouchListener 也就是触屏事件监听
	class MyTouchListener implements OnTouchListener{

		//MotionEvent 触屏事件的动作
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				/*x = (int) event.getX();
				y = (int) event.getY();
				System.out.println("down x=" + x + ",y=" + y);*/
				break;
			case MotionEvent.ACTION_MOVE:
				x = (int) event.getX();
				y = (int) event.getY();
				//System.out.println("move x=" + x + ",y=" + y);
				//超过边界检查
				if(x<0) {
					x = 0;
				}
				if(y < 0) {
					y = 0;
				}
				if(x > width -1) {
					x = width -1;
				}
				if(y > height -1) {
					y = height -1;
				}
				//进行像素操作
				for(int i=x-10; i< x+10; i++) {
					for(int k= y-10; k < y+10; k++ ) {
						//判断i,k是否在图片内
						if( i>=0 && i< width && k>=0 && k<height) {
							//检测坐标点(i,k)是否在以(x,y)为圆心点且半径为10的范围之内
							if( Math.sqrt((i-x) * (i-x) + (k -y) * (k-y)) < 10) {
								int pixel = 0x00000000; //将此坐标点的像素设置成全透明
								//pixels[i* width + k] = pixel;
								// 将此坐标的颜色值设置给bitmap
								bitmap.setPixel(i, k, pixel);
							}
						}
					}
				}
				//bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
				imageView.setImageBitmap(bitmap);
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
			return true;
		}
		
	}
	
	/**
	 * 生成刮奖的覆盖图片
	 */
	public void setCover(){
		//生成Bitmap
		bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		//然后设置像素
		for(int i=0; i<width; i++) {
			for(int j=0; j< height; j++) {
				int color = 0xff636363;
				bitmap.setPixel(i, j, color);
			}
		}
		//将bitmpa设置为image的内容
		imageView.setImageBitmap(bitmap);
	}
}
