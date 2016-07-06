package jixiang.com.myandroid.networkbitmap;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
/**
 * 位图处理工具类
 *
 */
public class PhotoUtils {
	/**
	 * 将bytes数组转化成bitmap，然后进行maxSize缩放
	 * @param src 源bytes数组
	 * @param maxSize 缩放后大小
	 * @return 返回缩放后的bitmap的bytes数组，或者是源bytes如果出现了OOM或者是异常
	 */
	public static byte[] scal(byte[] src, int maxSize) {
		try {
			Bitmap tempBitmap = byteToBitmap(src);
			Bitmap descBitmap = scal(tempBitmap, maxSize);
			byte[] rData = bitmapToByte(descBitmap);
			tempBitmap.recycle();
			descBitmap.recycle();
			return rData;
		} catch (OutOfMemoryError e) {
			return src;
		} catch (Exception e) {
			return src;
		}
	}

	/**
	 * 图像缩放
	 * @param src 源位图
	 * @param maxSize 缩放后的大小
	 * @return 缩放后的位图，null如果源位图为空
	 */
	public static Bitmap scal(Bitmap src, int maxSize) {
		if (src == null)
			return null;
		int width = src.getWidth();
		int height = src.getHeight();
		float scal = 1f;
		if (width > maxSize || height > maxSize) { //源位图的size大于缩放后的size
			if (width > height) {
				scal = (float) maxSize / (float) width;
			} else {
				scal = (float) maxSize / (float) height;
			}
		}
		Matrix matrix = new Matrix();
		matrix.postScale(scal, scal);
		Bitmap rBitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(),
				src.getHeight(), matrix, true);
		return rBitmap;
	}

	/**
	 * 图片缩放
	 * @param src 原始bitmap
	 * @param r 缩放比
	 * @return 缩放后的图片，如果发生OOM，则返回源图
	 */
	public static Bitmap scal(Bitmap src, float r) {
		if (src == null)
			return null;
		Matrix matrix = new Matrix();
		matrix.postScale(r, r);
		try {
			Bitmap rBitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
			return rBitmap;
		} catch (OutOfMemoryError e) {
			return src;
		}
	}

	/**
	 * 将bitmap转换成bytes数组, quality 50
	 * @param bitmap 源位图
	 * @return bitmap's bytes, null if bitmap is null
	 */
	public static byte[] bitmapToByte(Bitmap bitmap) {
		if (bitmap == null)
			return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
		return baos.toByteArray();
	}

	/**
	 * 将bitmap转换成bytes数组, quality 100
	 * @param bitmap 源位图
	 * @param fmt 压缩格式
	 * @return bitmap's bytes, null if bitmap is null
	 */
	public static byte[] bitmapToByte(Bitmap bitmap, Bitmap.CompressFormat fmt) {
		if (bitmap == null)
			return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(fmt, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 将bytes数组转换成bitmap
	 * @param bitmapData 源bytes数组
	 * @return 转换后的bitmap，null如果bytes为空，或者出现了OOM
	 */
	public static Bitmap byteToBitmap(byte[] bitmapData) {
		if (bitmapData == null)
			return null;
		Options options = new Options();
		options.inJustDecodeBounds = true;
		int srcWidth = options.outWidth;
		options.inJustDecodeBounds = false;
		int be = 0;
		be = (int) Math.round(((double) srcWidth) / ((double) 80));
		options = new Options();
		options.inSampleSize = be;
		try {
			return BitmapFactory.decodeByteArray(bitmapData, 0,
					bitmapData.length, options);
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	/**
	 * 将bytes数组转换成位图，并缩放成size大小
	 * @param bitmapData 源bytes数组
	 * @param size 缩放后的大小
	 * @return 转换和缩放后的位图，null如果bytes为空，或者出现了OOM
	 */
	public static Bitmap byteToBitmap(byte[] bitmapData, int size) {
		if (bitmapData == null)
			return null;
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory
				.decodeByteArray(bitmapData, 0, bitmapData.length, options);
		int w = options.outWidth;
		int h = options.outHeight;
		int sc = 0;
		if (w > size || h > size)
			if (w > h) {
				sc = Math.round((float) w / (float) size);
			} else {
				sc = Math.round((float) h / (float) size);
			}
		options.inJustDecodeBounds = false;
		options.inSampleSize = sc;
		try {
			return BitmapFactory.decodeByteArray(bitmapData, 0,
					bitmapData.length, options);
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	/**
	 * 位图旋转
	 * @param b 源位图
	 * @param degrees 旋转角度
	 * @return 旋转后的位图，null 如果原位图b为空或者degrees为空
	 * 		   如果旋转后的图片仍和源位图一致，或出现OOM，则返回源位图，
	 */
	public static Bitmap rotate(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2,
					(float) b.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					return b2;
				} else {
					return b;
				}
			} catch (OutOfMemoryError ex) {
				return b;
			}
		}
		return null;
	}

	/**
	 * 调整位图旋转后的位置
	 * @param bm 源位图
	 * @param orientationDegree 水平方向的旋转角度
	 * @return 处理后的位图
	 */
	public Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {
		Matrix m = new Matrix();
		m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
		float targetX, targetY;
		if (orientationDegree == 90) {
			targetX = bm.getHeight();
			targetY = 0;
		} else {
			targetX = bm.getHeight();
			targetY = bm.getWidth();
		}
		final float[] values = new float[9];
		m.getValues(values);
		float x1 = values[Matrix.MTRANS_X];
		float y1 = values[Matrix.MTRANS_Y];
		m.postTranslate(targetX - x1, targetY - y1);
		Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);
		Paint paint = new Paint();
		Canvas canvas = new Canvas(bm1);
		canvas.drawBitmap(bm, m, paint);
		return bm1;
	}

	/**
	 * 从path路径loading位图，并缩放到size大小
	 * @param path 源位图存储路径
	 * @param size 缩放后的大小
	 * @return loading后的位图，null如果出现OOM
	 */
	public static Bitmap loadFile(String path, int size) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		int w = options.outWidth;
		int h = options.outHeight;
		int sc = 1;
		if (w > size || h > size)
			if (w > h) {
				sc = Math.round((float) w / (float) size);
			} else {
				sc = Math.round((float) h / (float) size);
			}
		options.inJustDecodeBounds = false;
		options.inSampleSize = sc;
		try {
			return BitmapFactory.decodeFile(path, options);
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	/**
	 * 从path路径loading位图
	 * @param path 源位图的存储路径
	 * @return loading后的位图，null如果出现了OOM
	 */
	public static Bitmap loadFile(String path) {
		try {
			return BitmapFactory.decodeFile(path);
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	/**
	 * 将bitmap转换成圆角位图
	 * @param bitmap 源位图
	 * @param pixels 圆角半径
	 * @return 转换后的位图
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), bitmap.getConfig());
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 从filePath路径中读取位图，并将位图转换成圆角位图
	 * @param filePath 源位图的存储路径
	 * @param pixels 圆角的半径
	 * @return 返回处理后的位图，null读取位图失败或者出现了OOM
	 */
	public static Bitmap roundCornerFromFile(String filePath, int pixels) {
		try {
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			if (bitmap == null)
				return null;
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), bitmap.getConfig());
			Canvas canvas = new Canvas(output);
			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = pixels;
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			bitmap.recycle();
			bitmap = null;
			return output;
		} catch (OutOfMemoryError e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 从filePath路径中读取位图，将其缩放到size大小，最转换成圆角
	 * @param filePath 源位图的存储路径 
	 * @param pixels 圆角的半径
	 * @param size 缩放后的大小
	 * @return 返回处理后的位图，null读取源位图错误或者出现了OOM异常
	 */
	public static Bitmap roundCornerFromFile(String filePath, int pixels, int size) {
		try {
			Bitmap bitmap = loadFile(filePath, size);
			if (bitmap == null)
				return null;
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), bitmap.getConfig());
			Canvas canvas = new Canvas(output);
			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = pixels;
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			bitmap.recycle();
			bitmap = null;
			return output;
		} catch (OutOfMemoryError e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

}
