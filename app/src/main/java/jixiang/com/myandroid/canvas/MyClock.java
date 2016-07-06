package jixiang.com.myandroid.canvas;

import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class MyClock extends View  {
	float width; // 宽度
	float height; // 高度
	float halfWidth; //宽度的一半
	float halfHeight; //高度的一半
	float radius; // 表盘的半径
	float halfRadius; // 2分之一半径
	float tan;
	float radiusSubTen; // 半径减去10
	float halfRadiusSunTen;
	float tenTan;
	float hourLength; // 时针长
	float minuteLength; // 分针长
	float secondLength; // 秒针长
	float widthHour = 10; // 时针宽
	float widthMinute = 6; // 分针宽
	float widthSecond = 3; // 秒针宽

	int year;
	int month;
	int day;
	int dayOfWeek;
	int hour;
	int minute;
	int second;
	float textSizeDate; //日期的字体的大小
	float textSizeWeek; //星期的字体大小
	float datePadding; //日期字体距离表盘中心点的垂直间距
	float weekPadding; //星期字体距离表盘中心点的垂直间距
	float panelOutPadding; //表盘的外间距
	float whiteCircleRadius; //中心白点的半径
	String[] weeks = { "", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五" ,"星期六" };
	Paint paint = new Paint(); // 生成一个画笔

	RectF rectF = new RectF(); // 用来包裹日期
	RectF rectF1 = new RectF(); // 用来包裹星期

	boolean flag = true;
	
	PaintFlagsDrawFilter flagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
	BlurMaskFilter blurMaskFilter = new BlurMaskFilter(2, BlurMaskFilter.Blur.NORMAL);

	public MyClock(Context context) {
		this(context, null);
	}

	public MyClock(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyClock(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = getMeasuredWidth();
		height = getMeasuredHeight();
		halfWidth = width /2;
		halfHeight = height /2;
		if (width < height) {
			radius = (width * 0.90F) / 2;
		} else {
			radius = (height * 0.90F) / 2;
		}
		panelOutPadding = width / 2 - radius;
		if(panelOutPadding > 10) {
			panelOutPadding = 10;
		}
		halfRadius = (radius - 5) / 2;
		tan = (float) Math.sqrt(0.75 * (radius - 5) * (radius - 5));
		radiusSubTen = radius - 5 - 10;
		halfRadiusSunTen = radiusSubTen / 2;
		tenTan = (float) Math.sqrt(0.75 * radiusSubTen * radiusSubTen);
		hourLength = radius / 2;
		minuteLength = (float) (radius * 0.70);
		secondLength = (float) (radius * 0.80);
		int pixels = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                200, getResources().getDisplayMetrics());
		if(radius  > pixels / 2) {
			textSizeDate = 30;
			textSizeWeek = 24;
			datePadding = 35;
			weekPadding = 50;
			whiteCircleRadius = (widthHour -2) /2;
		}else {
			textSizeDate = (float) (radius / pixels * 30 * 1.2);
			textSizeWeek = (float) (radius / pixels * 24 * 1.2);
			datePadding = radius / pixels * 35;
			weekPadding = radius / pixels * 50;
			widthHour = radius / pixels * widthHour;
			widthMinute = radius / pixels * widthMinute;
			widthSecond = radius / pixels * widthSecond;
			
			if(widthHour < 5) {
				widthHour = 5;
				whiteCircleRadius = (widthHour -1) /2;
			}
			if(widthMinute < 4) {
				widthMinute = 4;
			}
			if(widthSecond < 2) {
				widthSecond = 2;
			}
			
		}
		
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				invalidate();
				break;
			}
			return true;
		}
	});

	class MyThread extends Thread {
		@Override
		public void run() {
			while (flag) {
				Calendar calendar = Calendar.getInstance(Locale.getDefault());
				year = calendar.get(Calendar.YEAR);
				month = calendar.get(Calendar.MONTH) + 1;
				day = calendar.get(Calendar.DAY_OF_MONTH);
				dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				hour = calendar.get(Calendar.HOUR);
				minute = calendar.get(Calendar.MINUTE);
				second = calendar.get(Calendar.SECOND);
				handler.sendEmptyMessage(123);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//canvas 反锯齿
		canvas.setDrawFilter(flagsDrawFilter);
		//边缘模糊
		paint.setMaskFilter(blurMaskFilter);
		paint.setColor(0xffffffff);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(6);
		paint.setStyle(Paint.Style.STROKE);
		paint.setDither(true);

		// 绘制表盘
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(panelOutPadding);
		paint.setColor(0x66333333);
		canvas.drawCircle(halfWidth, halfHeight, radius, paint);
		
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0xFFFFDAB9);
		canvas.drawCircle(halfWidth, halfHeight, radius, paint);
		paint.setMaskFilter(null);

		// 绘制日期
		String date = year + "-" + month + "-" + day;
		paint.setColor(0xff1192cb);
		paint.setTextSize(textSizeDate);
		FontMetrics fm = paint.getFontMetrics();
		int textHeight = (int) (Math.ceil(fm.descent - fm.ascent) + 2);
		System.out.println("textHeight="+ textHeight);
		paint.setStrokeWidth(1);
		float strWidth = paint.measureText(date);
		canvas.drawText(date, halfWidth - strWidth / 2, halfHeight - datePadding, paint);

		rectF.left = halfWidth - strWidth / 2 - 2;
		rectF.top = halfHeight - datePadding - textSizeDate - 2;
		rectF.right = halfWidth + strWidth / 2 + 2;
		rectF.bottom = halfHeight - textSizeDate + 2 + 2;
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(0xff000000);
		paint.setStrokeWidth(1);
		canvas.drawRoundRect(rectF, 2, 2, paint);
		  

		String week = weeks[dayOfWeek];
		paint.setColor(0xff1192cb);
		paint.setTextSize(textSizeWeek);
		paint.setStrokeWidth(1);
		paint.setStyle(Paint.Style.FILL);
		float wekWidth = paint.measureText(week);
		//canvas.drawText(week, halfWidth - wekWidth / 2, halfHeight + weekPadding, paint);
		canvas.drawText(week, halfWidth - wekWidth / 2, halfHeight + textSizeWeek * 2, paint);

		rectF1.left = halfWidth - wekWidth / 2 - 2;
		rectF1.top = halfHeight + textSizeWeek;
		rectF1.right = halfWidth + wekWidth / 2 + 2;
		rectF1.bottom = halfHeight + textSizeWeek * 2 + 2 + 2;
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(0xff000000);
		paint.setStrokeWidth(1);
		canvas.drawRoundRect(rectF1, 2, 2, paint);

		paint.setColor(0xff898989);
		float x31;
		float y31;
		float x21;
		float y21;
		paint.setStrokeWidth(2);
		for(int i = 1; i <= 60; i++) {
			if(i % 5 == 0) {
				continue;
			}
			x31  = (float) (width / 2 + (radius -5) * Math.sin(Math.toRadians(6 * i)));
			y31  = (float) (height / 2 - (radius-5) * Math.cos(Math.toRadians(6 * i)));
			x21 = (float) (width / 2 + (radiusSubTen + 5 ) * Math.sin(Math.toRadians(6 * i)));
			y21 = (float) (height / 2- (radiusSubTen + 5) * Math.cos(Math.toRadians(6 * i)));
			canvas.drawLine(x31, y31, x21, y21, paint);
			//时间复杂度和空间复杂度相差不大，只是在值的精确上面下面的算法要好些,避免了乘除运算
		}
		
		paint.setStrokeWidth(3);
		// 12点
		float x = halfWidth;
		float y = halfHeight - (radius - 5);
		float x1 = halfWidth;
		float y1 = halfHeight - (radius - 5) + 13;
		paint.setStrokeWidth(3);
		canvas.drawLine(x, y, x1, y1, paint);

		// 1点
		x = halfWidth + halfRadius;
		y = halfHeight - tan;
		x1 = halfWidth + halfRadiusSunTen;
		y1 = halfHeight - tenTan;
		canvas.drawLine(x, y, x1, y1, paint);

		// 2点
		x = halfWidth + tan;
		y = halfHeight - halfRadius;
		x1 = halfWidth + tenTan;
		y1 = halfHeight - halfRadiusSunTen;
		canvas.drawLine(x, y, x1, y1, paint);

		// 3点
		x = halfWidth + (radius - 5);
		y = halfHeight;
		x1 = halfWidth + (radius - 5) - 13;
		y1 = halfHeight;
		canvas.drawLine(x, y, x1, y1, paint);

		// 4点
		x = halfWidth + tan;
		y = halfHeight + halfRadius;
		x1 = halfWidth + tenTan;
		y1 = halfHeight + halfRadiusSunTen;
		canvas.drawLine(x, y, x1, y1, paint);

		// 5点
		x = halfWidth + halfRadius;
		y = halfHeight + tan;
		x1 = halfWidth + halfRadiusSunTen;
		y1 = halfHeight + tenTan;
		canvas.drawLine(x, y, x1, y1, paint);

		// 6点
		x = halfWidth;
		y = halfHeight + (radius - 5);
		x1 = halfWidth;
		y1 = halfHeight + radiusSubTen - 3;
		canvas.drawLine(x, y, x1, y1, paint);

		// 7点
		x = halfWidth - halfRadius;
		y = halfHeight + tan;
		x1 = halfWidth - halfRadiusSunTen;
		y1 = halfHeight + tenTan;
		canvas.drawLine(x, y, x1, y1, paint);

		// 8
		x = halfWidth - tan;
		y = halfHeight + halfRadius;
		x1 = halfWidth - tenTan;
		y1 = halfHeight + halfRadiusSunTen;
		canvas.drawLine(x, y, x1, y1, paint);

		// 9
		x = halfWidth - (radius - 5);
		y = halfHeight;
		x1 = halfWidth - (radius - 5) + 10 + 3;
		y1 = halfHeight;
		canvas.drawLine(x, y, x1, y1, paint);

		// 10
		x = halfWidth - tan;
		y = halfHeight - halfRadius;
		x1 = halfWidth - tenTan;
		y1 = halfHeight - halfRadiusSunTen;
		canvas.drawLine(x, y, x1, y1, paint);

		// 11
		x = halfWidth - halfRadius;
		y = halfHeight - tan;
		x1 = halfWidth - halfRadiusSunTen;
		y1 = halfHeight - tenTan;
		canvas.drawLine(x, y, x1, y1, paint);

		// 画时针
		x = halfWidth;
		y = halfHeight + 10;
		float degrees = (float) (hour * 30 + minute * 0.5);
		x1 = halfWidth;
		y1 = halfHeight - hourLength;
		paint.setStrokeWidth(widthHour);
		canvas.save();
		canvas.rotate(degrees, halfWidth, halfHeight);
		canvas.drawLine(x, y, x1, y1, paint);
		canvas.restore();

		// 画分针
		x = halfWidth;
		y = halfHeight + 10;
		degrees = (float) (minute * 6);
		x1 = halfWidth;
		y1 = halfHeight - minuteLength;
		paint.setStrokeWidth(widthMinute);
		canvas.save();
		canvas.rotate(degrees, halfWidth, halfHeight);
		canvas.drawLine(x, y, x1, y1, paint);
		canvas.restore();

		//
		x = halfWidth;
		y = halfHeight + 10;
	    degrees = (float) (second * 6);
		x1 = halfWidth;
		y1 = halfHeight - secondLength;
		paint.setStrokeWidth(widthSecond);
		canvas.save();
		canvas.rotate(degrees, halfWidth, halfHeight);
		canvas.drawLine(x, y, x1, y1, paint);
		canvas.restore();

		// 画白色原点的内圆
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0Xffffffff);
		canvas.drawCircle(halfWidth, halfHeight, whiteCircleRadius, paint);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		// System.out.println("onDetachedFromWindow==============================");
		flag = false;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		// System.out.println("onAttachedToWindow==============================");
		new MyThread().start();
	}
}
