package jixiang.com.myandroid.canvas;

import java.util.Calendar;
import java.util.Locale;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

public class LiveWallpaper extends WallpaperService {
	// 实现WallpaperService必须实现的抽象方法
	public Engine onCreateEngine() {
		// 返回自定义的Engine
		return new ClockEngine();
	}
	
	class ClockEngine extends Engine{
		
		float width; // 宽度
		float height; // 高度
		float halfWidth; //宽度的一半
		float halfHeight; //高度的一半
		float radius; // 表盘的半径
		float halfRadius; // 2分之一半径
		float tan;
		// Context mContext;
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
		String[] weeks = { "", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五" ,"星期六" };
		Paint paint = new Paint(); // 生成一个画笔

		RectF rectF = new RectF(); // 用来包裹日期
		RectF rectF1 = new RectF(); // 用来包裹星期

		boolean flag = true;
		
		PaintFlagsDrawFilter flagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
		BlurMaskFilter blurMaskFilter = new BlurMaskFilter(1, BlurMaskFilter.Blur.NORMAL);
		
		// 定义一个Handler
		Handler mHandler = new Handler();
		// 定义一个周期性执行的任务
		private final Runnable drawTarget = new Runnable() {
			public void run() {
				Calendar calendar = Calendar.getInstance(Locale.getDefault());
				year = calendar.get(Calendar.YEAR);
				month = calendar.get(Calendar.MONTH) + 1;
				day = calendar.get(Calendar.DAY_OF_MONTH);
				dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				hour = calendar.get(Calendar.HOUR);
				minute = calendar.get(Calendar.MINUTE);
				second = calendar.get(Calendar.SECOND);
				// 动态地绘制图形
				drawFrame();
			}
		};
		
		
		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			DisplayMetrics dm = new DisplayMetrics();
			dm = getResources().getDisplayMetrics();
			//float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
			//int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
			//float xdpi = dm.xdpi;
			//float ydpi = dm.ydpi;
			width = dm.widthPixels; // 屏幕宽（像素，如：480px）
			height = dm.heightPixels; // 屏幕高（像素，如：800px）
			halfWidth = width /2;
			halfHeight = height /2;
			Calendar calendar = Calendar.getInstance(Locale.getDefault());
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DAY_OF_MONTH);
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			if (width < height) {
				radius = Math.round((width * 0.75F) / 2);
			} else {
				radius = Math.round((height * 0.75F) / 2);
			}
			halfRadius = (radius - 5) / 2;
			tan = (float) Math.sqrt(0.75 * (radius - 5) * (radius - 5));
			radiusSubTen = radius - 5 - 10;
			halfRadiusSunTen = radiusSubTen / 2;
			tenTan = (float) Math.sqrt(0.75 * radiusSubTen * radiusSubTen);
			hourLength = radius / 2;
			minuteLength = (float) (radius * 0.70);
			secondLength = (float) (radius * 0.80);
		}
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			mHandler.removeCallbacks(drawTarget);
		}
		
		@Override
		public void onVisibilityChanged(boolean visible) {
			// 当界面可见时候，执行drawFrame()方法。
			if (visible) {
				// 动态地绘制图形
				drawFrame();
			} else {
				// 如果界面不可见，删除回调
				mHandler.removeCallbacks(drawTarget);
			}
		}
		
		public void drawFrame(){
			// 获取该壁纸的SurfaceHolder
			final SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = null;
			try {
				// 对画布加锁
				canvas = holder.lockCanvas();
				if (canvas != null) {
					canvas.save();
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
					paint.setStrokeWidth(10);
					paint.setColor(0x66aeaeae);
					canvas.drawCircle(halfWidth, halfHeight, radius, paint);
					
					paint.setStyle(Paint.Style.FILL);
					paint.setColor(0xFFFFDAB9);
					canvas.drawCircle(halfWidth, halfHeight, radius, paint);
					paint.setMaskFilter(null);

					// 绘制日期
					String date = year + "-" + month + "-" + day;
					paint.setColor(0xff1192cb);
					paint.setTextSize(30);
					paint.setStrokeWidth(1);
					float strWidth = paint.measureText(date);
					canvas.drawText(date, halfWidth - strWidth / 2, halfHeight - (30 + 5),
							paint);

					rectF.left = halfWidth - strWidth / 2 - 2;
					rectF.top = halfHeight - (30 + 5) - 30 - 2;
					rectF.right = halfWidth + strWidth / 2 + 2;
					rectF.bottom = halfHeight + 2 - 30;
					paint.setStyle(Paint.Style.STROKE);
					paint.setColor(0xff000000);
					paint.setStrokeWidth(1);
					canvas.drawRoundRect(rectF, 2, 2, paint);

					String week = weeks[dayOfWeek];
					paint.setColor(0xff1192cb);
					paint.setTextSize(24);
					paint.setStrokeWidth(1);
					paint.setStyle(Paint.Style.FILL);
					float wekWidth = paint.measureText(week);
					canvas.drawText(week, halfWidth - wekWidth / 2, halfHeight + 50, paint);

					rectF1.left = halfWidth - wekWidth / 2 - 2;
					rectF1.top = halfHeight + 2 + 26;
					rectF1.right = halfWidth + wekWidth / 2 + 2;
					rectF1.bottom = halfHeight + 2 + 50 + 2;
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
					canvas.drawCircle(halfWidth, halfHeight, 4, paint);
					canvas.restore();
				}
			} finally {
				if (canvas != null)
					holder.unlockCanvasAndPost(canvas);
			}
			// 指定0.3秒后重新执行mDrawCube一次
			mHandler.postDelayed(drawTarget, 300);
		}
	}
}