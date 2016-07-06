package jixiang.com.myandroid.networkbitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import jixiang.com.myandroid.http.Logg;


/**
 * 从SD卡缓存目录中加载图片
 * 通过URL进行文件查找匹配
 */
public class BitmapSDLoaderTask extends AsyncTask<String, Void, Bitmap> {
	private static final String TAG = BitmapSDLoaderTask.class.getCanonicalName();

	private WeakReference<ImageView> imageViewReference;
	private BitmapSDLoadListener mListener;
	public String mUrl;
	//private boolean mError;
	private int mWidth;
	private int mHeight;
	private static int defaultWidth = 480;
	private static int defaultHeight = 800;

	/**
	 * 图片加载器回调接口
	 *
	 */
	public interface BitmapSDLoadListener {
		public void sdNotFound();

		public void sdLoadBitmap(Bitmap b);

		public void sdOnLoadError();

		public void sdOnLoadCancelled();
	}

	public BitmapSDLoaderTask(ImageView imageView, BitmapSDLoadListener listener) {
		imageViewReference = new WeakReference<ImageView>(imageView);
		mListener = listener;
	}
	
	/**
	 * Conservatively estimates inSampleSize. Given a required width and height,
	 * this method calculates an inSampleSize that will result in a bitmap that is
	 * approximately the size requested, but guaranteed to not be smaller than
	 * what is requested.
	 * 
	 * @param options
	 *          the {@link BitmapFactory.Options} obtained by decoding the image
	 *          with inJustDecodeBounds = true
	 * @param reqWidth
	 *          the required width
	 * @param reqHeight
	 *          the required height
	 * 
	 * @return the calculated inSampleSize
	 */
	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}
	
	/**
	 * 从路径中加载图片
	 */
	@Override
	protected Bitmap doInBackground(String... params) {
		ImageView imageView = imageViewReference.get();
		Bitmap bitmap = null;
		if(imageView != null) {
			mWidth = imageView.getMeasuredWidth();
			mHeight = imageView.getMeasuredHeight();
			if(mWidth == 0 || mHeight == 0) {
				mWidth = defaultWidth;
				mHeight = defaultHeight;
			}
			mUrl = params[0];
			if (mUrl == null) {
				return null;
			}
			String filename = mUrl;
			if (isCancelled()) {
				return null;
			}
			if (filename != null) {
				try {
					System.gc();
					FileInputStream local = null;
					File file = new File(filename);
					local = new FileInputStream(file);
					final BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					BitmapFactory.decodeFileDescriptor(local.getFD(), null, options);
					options.inSampleSize = calculateInSampleSize(options, mWidth, mHeight);
					options.inJustDecodeBounds = false;
					options.inPreferQualityOverSpeed = false; //速度优先
					try {
						bitmap = BitmapFactory.decodeFileDescriptor(local.getFD(), null, options);
					} catch(OutOfMemoryError error) {
						System.gc();
						try {
							bitmap = BitmapFactory.decodeFileDescriptor(local.getFD(), null, options);
						} catch(OutOfMemoryError outOfMemoryError) {
							Logg.e(TAG, "OUT of Memory");
						}
					}
					if(local != null) {
						local.close();
						local = null;
					}
					System.gc();
				} catch (FileNotFoundException e) {
					Logg.w(TAG, "File not founded in filesystem");
				} catch (IOException e) {
					Logg.w(TAG, "Loading file occur IO exception.");
				}
			}
			return bitmap;
		} else {
			return null;
		}
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (bitmap == null  && !isCancelled()) {
			mListener.sdNotFound();
		} else {
			ImageView imageView = imageViewReference.get();
			if(imageView != null) {
				if(bitmap != null) {
					if(!isCancelled()) {
						mListener.sdLoadBitmap(bitmap);
					} else {
						mListener.sdOnLoadCancelled();
						if(!bitmap.isRecycled()) {
							bitmap.recycle();
							bitmap = null;
						}
					}
				} else {
					mListener.sdOnLoadError();
				}
			} else { //图片控件不再使用，无论加载与否，都不在需要这张图片以节省空间
				mListener.sdOnLoadCancelled(); 
				if(bitmap != null && !bitmap.isRecycled()) {
					bitmap.recycle();
					bitmap = null;
				}
			}
			
			//旧版本的代码，可能会照成内存泄露
			/*if (isCancelled() && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			ImageView imageView = imageViewReference.get();
			if (imageView != null ) {
				if (bitmap != null) {
					mListener.sdLoadBitmap(bitmap);
				} else if (!isCancelled()) {
					mListener.sdOnLoadError();
				} else if (isCancelled()) {
					mListener.sdOnLoadCancelled();
				}
			} else {
				mListener.sdOnLoadError();
			}*/
		}
	}
}
