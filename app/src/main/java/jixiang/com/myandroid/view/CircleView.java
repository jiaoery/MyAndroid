package jixiang.com.myandroid.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import jixiang.com.myandroid.R;


public class CircleView extends View {
	public int width;
	public int height;
	public int max = 10; //总共的停车位
	public int rest = 3;//剩下的停车位
	Paint paint; //画笔
	int innerRadius; //内圈的半径
	int outerRadius; //外圈的半径
	int TextSize = 32;

	public CircleView(Context context) {
		this(context, null);
	}

	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setRestAndMax(int rest, int max){
		this.max = max;
		this.rest = rest;
		invalidate();
	}

	/**
	 * 测量控件所需的高度和宽度4
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
     */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(widthMeasureSpec == MeasureSpec.AT_MOST) {
			System.out.println("AT_MOST");
		} else if(widthMeasureSpec == MeasureSpec.EXACTLY){
			System.out.println("EXACTLY");
		} else if(widthMeasureSpec == MeasureSpec.UNSPECIFIED) {
			System.out.println("UNSPECIFIED");
		}
		System.out.println("widthMeasureSpec="+ widthMeasureSpec + ", heightMeasureSpec=" + heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 当布局大小改变时回调该方法
	 * @param changed
	 * @param left
	 * @param top
	 * @param right
     * @param bottom
     */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		System.out.println("onLayout change=" + changed + ", left=" + left + ", top=" + top + ", right="+ right + ", bottom=" + bottom);
		width = getMeasuredWidth();
		height = getMeasuredHeight();
		System.out.println("width=" + width + ", height=" + height );
		innerRadius = (int) (0.75 * width) / 2;
	}
	
	
	/**
	 * 绘制界面
	 * canvas 画布
	 */
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//画内圈
		paint = new Paint();
		paint.setAntiAlias(true);//反锯齿
		paint.setColor(0xffe7e7e7);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		System.out.println("innerRadius=" + innerRadius);
		canvas.drawCircle(width/2, height/2, innerRadius, paint);
		
		//画车
		Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.car1)).getBitmap();
		int carWidth = bitmap.getWidth();
		int carHeight = bitmap.getHeight();
		canvas.drawBitmap(bitmap, width/2 - carWidth/2, height/2 - carHeight -2, paint);
		
		//画外圈
		paint.setStyle(Paint.Style.STROKE); //设置描边，不进行填充
		paint.setStrokeWidth(6); //设置线宽
		canvas.drawCircle(width/2, height/2, width/2 -6, paint);
		
		//画字符
		//百分比
		paint.setColor(getResources().getColor(R.color.common_blue));
		paint.setTextSize(TextSize);
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.FILL);
		int percent;
		if(max == 0) {
			percent = 0;
		} else {
			percent = rest * 100 / max;
		}
		String text = percent + "%";
		//字符串需要占用的宽度
		/**
		 * 计算出当前绘制出来的字符串有多宽高，可以这么来！
         *方法1：
         *Paint pFont = new Paint(); 
         *Rect rect = new Rect();

         *返回包围整个字符串的最小的一个Rect区域
         *pFont.getTextBounds(str, 0, 1, rect); 
         *strwid = rect.width();
         *strhei = rect.height();
		 */
		
		int strWidth = (int) paint.measureText(text);
		canvas.drawText(text, width /2 - strWidth/2 , height /2 + TextSize + 2, paint);
		
		//画圆弧
		paint.setStyle(Paint.Style.STROKE);//设置描边，不进行填充
		paint.setStrokeWidth(6); //设置线宽
		paint.setColor(getResources().getColor(R.color.common_blue));
		//folat 
		RectF oval = new RectF(6, 6, width -6, height-6);
		float sweepAngle;
		if(max == 0) {
			sweepAngle = 0;
		} else {
			sweepAngle = rest * 360 / max;
		}
		canvas.drawArc(oval, 0, sweepAngle, false, paint);
	}

}
