package jixiang.com.myandroid.networkbitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;


/**
 * 
 * 图片下载器实例
 */
public class BitmapUtil {
	private static BitmapDownloader mBitmapDownload;
	private static String cachePath;
	
	/**
	 * 获取BitmapDownloader实例
	 * @return BitmapDownloader实例,并获取到一个下载器
	 */
	public static BitmapDownloader getInstance(){
		if(mBitmapDownload == null){
			mBitmapDownload = BitmapDownloader.getInstance(0);
			if(!TextUtils.isEmpty(cachePath)){
				mBitmapDownload.setCachePath(cachePath);
			}
			//设置出现错误时的图片
			//mBitmapDownload.setErrorDrawable(errorDrawable);
			//设置下载中状态下的图片
			//mBitmapDownload.setInProgressDrawable(downloadingDrawable);
			mBitmapDownload.setAnimateImageAppearance(BitmapDownloader.AnimateAppearance.ANIMATE_NEVER);
			createCachePath(SystemBase.IMAGE_CACHE_PATH);
			setCachePath(SystemBase.IMAGE_CACHE_PATH);
		}
		return mBitmapDownload;
	}
	
	/**
	 * 获取BitmapDownloader实例
	 * @return BitmapDownloader实例,并获取到一个下载器
	 */
	public static BitmapDownloader getInstance(int errorDrawable, int downloadingDrawable){
		if(mBitmapDownload == null){
			mBitmapDownload = BitmapDownloader.getInstance(0);
			if(!TextUtils.isEmpty(cachePath)){
				mBitmapDownload.setCachePath(cachePath);
			}
			//设置出现错误时的图片
			mBitmapDownload.setErrorDrawable(errorDrawable);
			//设置下载中状态下的图片
			mBitmapDownload.setInProgressDrawable(downloadingDrawable);
			mBitmapDownload.setAnimateImageAppearance(BitmapDownloader.AnimateAppearance.ANIMATE_NEVER);
			createCachePath(SystemBase.IMAGE_CACHE_PATH);
			setCachePath(SystemBase.IMAGE_CACHE_PATH);
		}
		return mBitmapDownload;
	}
	
	
	
	/**
	 * 可设置同时最大下载图片数量
	 * @param maxDownload
	 * @return
	 */
	public static BitmapDownloader getInstance(int maxDownload){
		mBitmapDownload = BitmapDownloader.getInstance(maxDownload);
		if(!TextUtils.isEmpty(cachePath)){
			mBitmapDownload.setCachePath(cachePath);
		}
		return mBitmapDownload;
	}
	/**
	 * 可设置图片缓存位置
	 * @param path
	 * @return
	 */
	public static BitmapDownloader getInstance(String path, int errorDrawable, int downloadingDrawable){
		getInstance(errorDrawable, downloadingDrawable);
		setCachePath(path);
		return mBitmapDownload;
	}
	
	/**
	 * 设置最大图片加载数量和图片缓存位置
	 * @param maxDownload
	 * @param path
	 * @return
	 */
	public static BitmapDownloader getInstance(int maxDownload, String path){
		getInstance(maxDownload);
		setCachePath(path);
		return mBitmapDownload;
	}

	/**
	 * 设置图片缓存路径
	 * @param path
	 */
	public static void setCachePath(String path){
		if(mBitmapDownload != null){
			 mBitmapDownload.setCachePath(path);
		}
	}

	/**
	 * 创建图片缓存目录
	 * @param path 缓存目录路径
	 */
	public static void createCachePath(String path){
		File file = new File(path);
		if(!file.exists()) {
			File parent = file.getParentFile();
			createCachePath(parent.getAbsolutePath());
			file.mkdir();
		}
	}
	
	/**
	 * 图片质量循环压缩，如果图片大于1M则每次压缩10%， 直到小于1M，此方法会降低位图质量
	 * @param image 源位图
	 * @return 压缩后的位图
	 */
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
		while (baos.toByteArray().length / 1024 > 1024) {
			baos.reset();// 重置baos即清空baos
			//这里压缩options%，把压缩后的数据存放到baos中
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			options -= 10;// 每次都减少10
		}
		//把压缩后的数据baos存放到ByteArrayInputStream中
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		// 把ByteArrayInputStream数据生成图片
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		return bitmap;
	}

	/**
	 * 对位图进行质量压缩，并缩放到480 * 800
	 * @param path 源位图路径
	 * @return 返回处理后的位图
	 */
	public static Bitmap comp(String path) {
		Bitmap image = BitmapFactory.decodeFile(path);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		// 判断如果图片大于2M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
		if (baos.toByteArray().length / 1024 > 1024 * 2) {
			baos.reset();// 重置baos即清空baos
			// 这里压缩50%，把压缩后的数据存放到baos中
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		Options newOpts = new Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 将位图存储在路径为destPath的路径中. 如果目标文件存在，则覆盖保存
	 * @param bitmap 源位图
	 * @param destPath 存储目标文件路径
	 */
	public static void writeImage(Bitmap bitmap, String destPath) {
		try {
			File file = new File(destPath);
			if (file.exists()) {
				file.delete();
			}
			if (!file.exists()) {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
				out = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取合适的Bitmap平时获取Bitmap就用这个方法吧.
	 * @param path 路径.
	 * @param data byte[]数组.
	 * @param context 上下文
	 * @param uri uri
	 * @param target 模板宽或者高的大小.
	 * @param width 是否是宽度
	 * @return Bitmap
	 */
	public static Bitmap getResizedBitmap(String path, byte[] data, Context context, Uri uri, int target, boolean width) {
		Options options = null;
		if (target > 0) {
			Options info = new Options();
			// 这里设置true的时候，decode时候Bitmap返回的为空，
			// 将图片宽高读取放在Options里.
			info.inJustDecodeBounds = false;
			decode(path, data, context, uri, info);
			int dim = info.outWidth;
			if (!width)
				dim = Math.max(dim, info.outHeight);
			int ssize = sampleSize(dim, target);
			options = new Options();
			options.inSampleSize = ssize;
		}
		Bitmap bm = null;
		try {
			bm = decode(path, data, context, uri, options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bm;

	}

	/**
	 * 解析Bitmap的公用方法.
	 * @param path 路径.
	 * @param data byte[]数组.
	 * @param context 上下文
	 * @param uri uri
	 * @param options 位图选项
	 * @return bitmap
	 */
	public static Bitmap decode(String path, byte[] data, Context context, Uri uri, Options options) {
		Bitmap result = null;
		if (path != null) {
			result = BitmapFactory.decodeFile(path, options);
		} else if (data != null) {
			result = BitmapFactory.decodeByteArray(data, 0, data.length, options);
		} else if (uri != null) {
			// uri不为空的时候context也不要为空.
			ContentResolver cr = context.getContentResolver();
			InputStream inputStream = null;
			try {
				inputStream = cr.openInputStream(uri);
				result = BitmapFactory.decodeStream(inputStream, null, options);
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 获取合适的sampleSize. 这里就简单实现都是2的倍数啦.
	 * @param width
	 * @param target
	 * @return
	 */
	private static int sampleSize(int width, int target) {
		int result = 1;
		for (int i = 0; i < 10; i++) {
			if (width < target * 2) {
				break;
			}
			width = width / 2;
			result = result * 2;
		}
		return result;
	}

	/**
	 * bitmap转换成base64
	 * @param bitmap
	 * @return 转换后的字符串
	 */
	public static String bitmapToBase64(Bitmap bitmap) {
		if (bitmap == null)
			return "";
		return Base64.encodeToString(PhotoUtils.bitmapToByte(bitmap),
				Base64.DEFAULT);
	}
}
