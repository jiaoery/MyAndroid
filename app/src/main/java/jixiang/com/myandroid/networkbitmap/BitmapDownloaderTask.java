package jixiang.com.myandroid.networkbitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.widget.ImageView;

import jixiang.com.myandroid.http.Logg;

/**
 * 图片网络下载器
 * BitmapDownloaderTask 此类主要是从网络上下载图片
 * @author Administrator
 *
 */
public class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
	private static final String TAG = BitmapDownloaderTask.class.getCanonicalName();
	public String mUrl;
	private final Context mContext;
	private final BitmapDownloadListener mListener;
	private HttpGet mGetRequest;
	private String picPath = null;
	
	private WeakReference<ImageView> imageViewReference;
	private int mWidth;
	private int mHeight;
	private static int defaultWidth = 480;
	private static int defaultHeight = 800;

	/**
	 * 下载回调接口
	 */
	public interface BitmapDownloadListener {
		public void onComplete(Bitmap bitmap);

		public void onError();

		public void onCancel();
	}

	public BitmapDownloaderTask(ImageView imageView, BitmapDownloadListener listener) {
		mContext = imageView.getContext().getApplicationContext();
		imageViewReference = new WeakReference<ImageView>(imageView);
		mListener = listener;
	}
	
	public BitmapDownloaderTask(ImageView imageView, String path, BitmapDownloadListener listener) {
		mContext = imageView.getContext().getApplicationContext();
		imageViewReference = new WeakReference<ImageView>(imageView);
		mListener = listener;
		picPath = path;
	}
	
	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
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

	@Override
	protected Bitmap doInBackground(String... params) {
		mUrl = params[0];
		Bitmap bitmap = null;
		try {
			bitmap = downloadBitmap();
		} catch (Exception e) {
			Logg.w(TAG, "Error downloading bitmap" + e.getMessage());
		}
		return bitmap;
	}

	//for 2.2 where onCancelled(Object obj) is not implemented
	@Override
	protected void onCancelled() {
		mListener.onCancel();
		//if the task is cancelled, abort the image request
		if (mGetRequest != null) {
			Logg.w(TAG, "Aborting get request for:  " + mUrl);
			mGetRequest.abort();
			mGetRequest = null;
		}
	}

	
	@Override
	// Once the image is downloaded, associates it to the imageView
	// 一旦图片下载完成，将图片跟imageview关联起来
	protected void onPostExecute(Bitmap bitmap) {
		if (isCancelled()) {
			//done = false;
			mListener.onCancel();
			if(bitmap != null && !bitmap.isRecycled()){ //回收掉图片，不造成空间的浪费
				bitmap.recycle();
				bitmap = null; 
			}
		} else {
			if(bitmap != null) {
				mListener.onComplete(bitmap);
			} else {
				mListener.onError(); //图片下载失败
			}
		}
		/*Logg.w(TAG, "onPostExecute:  " + done);
		if (done) {
			mListener.onComplete();
		} else {
			mListener.onError();
		}*/
	}

	/**
	 * 解析URL返回状态码
	 * @return 返回状态码 
	 */
	private int resolveUrl() {
		HttpHead headRequest = new HttpHead(mUrl);
		AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
		int statusCode = HttpStatus.SC_OK;
		try {
			HttpResponse response = client.execute(headRequest);
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT || statusCode == HttpStatus.SC_MOVED_PERMANENTLY ||
					statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				mUrl = response.getFirstHeader("Location").getValue();
				return resolveUrl();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
		return statusCode;
	}

	/**
	 * 从网络上下载图片
	 */
	private Bitmap downloadBitmap() {
		if (isCancelled()) {
			return null;
		}
		Bitmap bitmap = null;
		ImageView imageView = imageViewReference.get();
		if(imageView != null) {
			mWidth = imageView.getMeasuredWidth();
			mHeight = imageView.getMeasuredHeight();
			if(mWidth == 0 || mHeight == 0) {
				mWidth = defaultWidth;
				mHeight = defaultHeight;
			}
			Logg.out("width=" + mWidth);
			Logg.out("height=" + mHeight);
		}
		
		//get the filename before we follow any redirects. very important
		String filename = MD5.md5(mUrl); 
		AndroidHttpClient client = AndroidHttpClient.newInstance("Android");

		try {
			mGetRequest = new HttpGet(mUrl);
			HttpResponse response = client.execute(mGetRequest);
			Logg.out("aaaaaaaaaaaaaa  download image from server");
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT || statusCode == HttpStatus.SC_MOVED_PERMANENTLY ||
					statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				statusCode = resolveUrl();
				if (statusCode == HttpStatus.SC_OK) {
					mGetRequest = new HttpGet(mUrl);
					response = client.execute(mGetRequest);
					statusCode = response.getStatusLine().getStatusCode();
				}
			}

			if (isCancelled()) {
				Logg.i(TAG, "Download of " + mUrl + " was cancelled");
				bitmap = null;
			} else if (statusCode != HttpStatus.SC_OK) {
				Logg.w(TAG, "Error " + statusCode + " while retrieving bitmap from " + mUrl);
				bitmap = null;
			} else {
				if (isCancelled()) {
					return null;
				}
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream inputStream = null;
					try {
						inputStream = entity.getContent();
						if (isCancelled()) {
							return null;
						}

						FileOutputStream fos = null;
						if(picPath == null) {
							fos = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
						} else {
							File file = new File(picPath, filename);
							fos = new FileOutputStream(file);
						}

						byte[] buffer = new byte[1024];
						int len = 0;
						while (!isCancelled() && (len = inputStream.read(buffer)) > 0) {
							fos.write(buffer, 0, len);
						}
						fos.flush();
						fos.close();
						if (isCancelled()) {
							return null;
						}
						
						//加载SD卡里面的图片
						System.gc();
						FileInputStream local = null;
						if(picPath == null) {
							local = mContext.openFileInput(filename);
						} else {
							File file = new File(picPath, filename);
							local = new FileInputStream(file);
						}
						final BitmapFactory.Options options = new BitmapFactory.Options();
						options.inJustDecodeBounds = true;
						BitmapFactory.decodeFileDescriptor(local.getFD(), null, options);

						options.inSampleSize = calculateInSampleSize(options, mWidth, mHeight);
						options.inJustDecodeBounds = false;
						try{
							bitmap = BitmapFactory.decodeFileDescriptor(local.getFD(), null, options);
						} catch(OutOfMemoryError error) {
							System.gc();
							try{
								bitmap = BitmapFactory.decodeFileDescriptor(local.getFD(), null, options);
							} catch(OutOfMemoryError error2) {
								Logg.e(TAG, "OUT of Memory");
							}
						}
					} finally {
						if (inputStream != null) {
							inputStream.close();
						}
						entity.consumeContent();
					}
				}
			}
		} catch (IllegalArgumentException e) {
			bitmap = null;
			Logg.w(TAG, "Error while retrieving bitmap from " + mUrl + e.getMessage());
		} catch (FileNotFoundException e) {
			mGetRequest.abort();
			bitmap = null;
			Logg.w(TAG, "Error while retrieving bitmap from " + mUrl + e.getMessage());
		} catch (IOException e) {
			mGetRequest.abort();
			bitmap = null;
			Logg.w(TAG, "Error while retrieving bitmap from " + mUrl+ e.getMessage());
		} finally {
			mGetRequest = null;
			client.close();
		}
		return bitmap;
	}
}