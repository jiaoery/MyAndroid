package jixiang.com.myandroid.networkbitmap;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.RejectedExecutionException;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.http.Logg;

public class BitmapDownloader {

	private static final String TAG = BitmapDownloader.class.getCanonicalName();
    /**
     * DOWNLOAD_TAG
     */
	public static final int DOWNLOAD_TAG = R.id.bmd__image_downloader;
	/**
	 * 默认的最大下载数量
	 */
	private final int DEFAULT_DOWNLOAD_NUM = 10;
	/**
	 * 图片缓存路径目录
	 * 文件名是通过MD5 hash而来的
	 */
	private String DEFAULT_PATH_PIC = null;
	/**
	 * BitmapDownloader实例引用,单例模式
	 */
	private static BitmapDownloader mBitmapDowloader = null;
	/**
	 * BitmapCache 图片内存缓存
	 */
	private final BitmapCache mBitmapCache = new BitmapCache();
	/**
	 * 下载列表
	 */
	private ArrayList<Download> mQueuedDownloads;
	/**
	 * 正在下载中的列表
	 */
	private ArrayList<Download> mRunningDownloads;
	/**
	 * 重复的下载
	 */
	private HashMap<String, ArrayList<Download>> mDuplicateDownloads;
	/**
	 * 最大下载的数量
	 */
	private int mMaxDownloads = DEFAULT_DOWNLOAD_NUM;
	/**
	 * 下载错误时显示的图片
	 */
	private Drawable mErrorDrawable;
	/**
	 * 下载中显示的图片
	 */
	private Drawable mInProgressDrawable;
	/**
	 * 下载错误时的图片的资源ID
	 */
	private int mErrorDrawableResource;
	/**
	 * 下载中的图片的资源ID
	 */
	private int mInProgressDrawableResource;
	/**
	 * 是否显示动画
	 */
	private boolean mAnimateImageAppearance = false;
	/**
	 * 下载完成时是否显示动画
	 */
	private boolean mAnimateImageAppearanceAfterDownload = true;
	/**
	 * 下载完成回调
	 */
	private BitmapLoaderCallback mCallback;
	
	/**
	 * 动画枚举类
	 */
	public static enum AnimateAppearance {
		ANIMATE_ALWAYS, ANIMATE_AFTER_DOWNLOAD, ANIMATE_NEVER
	}

	/**
	 * 下载完成回调接口
	 */
	public static interface BitmapLoaderCallback {
		public void onLoaded(ImageView view);
	}

	/**
	 * 如果maxDownloads小于0，BitmapDownloader将会设置最大下载数量为默认的5个.否则就使用maxDownloads这个参数,
	 * 一旦设置了下载最大值，就不能在重新设置，如果一定要设置，请将mBitMapDowloader设置为空，并重新通过getInstance方法获取mBitMapDowloader
	 * @param maxDownloads 最大下载值
	 * @return BitmapDownloader实例
	 */
	public static BitmapDownloader getInstance(int maxDownloads){
		if(mBitmapDowloader == null) {
			if(maxDownloads <= 0) {
				mBitmapDowloader = new BitmapDownloader();
			} else {
				mBitmapDowloader = new BitmapDownloader(maxDownloads);
			}
		}
		return mBitmapDowloader;
	}
	
	/**
	 * 单例模式，私有构造器
	 */
	private BitmapDownloader() {
		setup(DEFAULT_DOWNLOAD_NUM);
	}

	/**
	 * 单例模式，私有构造器
	 * @param maxDownloads 最大下载的个数
	 */
	private BitmapDownloader(int maxDownloads) {
		setup(maxDownloads);
	}
	
	/**
	 * 初始化下载器，设置最大下载数量
	 * @param maxDownloads 最大下载数量
	 */
	private void setup(int maxDownloads) {
		mQueuedDownloads = new ArrayList<Download>();
		mRunningDownloads = new ArrayList<Download>();
		mMaxDownloads = maxDownloads;
		mDuplicateDownloads = new HashMap<String, ArrayList<Download>>();
	}

	/**
	 * 设置下载错误时显示的 Drawable
	 * @param errorDrawable 下载错误时显示的 Drawable
	 */
	public void setErrorDrawable(Drawable errorDrawable) {
		mErrorDrawable = errorDrawable;
		mErrorDrawableResource = -1;
	}
	
	/**
	 * 设置图片缓存路径
	 * @param path 
	 */
	public void setCachePath(String path){
		DEFAULT_PATH_PIC = path;
	}
	
	/**
	 * 设置下载中显示的Drawable
	 * @param inProgressDrawable 下载中显示的Drawable
	 */
	public void setInProgressDrawable(Drawable inProgressDrawable) {
		mInProgressDrawable = inProgressDrawable;
		mInProgressDrawableResource = -1;
	}

	/**
	 * 设置下载错误时显示的位图资源ID
	 * @param errorDrawable 下载错误时显示的位图资源ID
	 */
	public void setErrorDrawable(int errorDrawable) {
		mErrorDrawable = null;
		mErrorDrawableResource = errorDrawable;
	}

	/**
	 * 设置下载中显示的位图的资源的ID
	 * @param inProgressDrawable 下载中显示的位图的资源的ID
	 */
	public void setInProgressDrawable(int inProgressDrawable) {
		mInProgressDrawable = null;
		mInProgressDrawableResource = inProgressDrawable;
	}

	/**
	 * 设置动画
	 * @param animate AnimateAppearance
	 */
	public void setAnimateImageAppearance(AnimateAppearance animate) {
		switch (animate) {
		case ANIMATE_ALWAYS: {
			mAnimateImageAppearance = true;
			mAnimateImageAppearanceAfterDownload = true;
			break;
		}
		case ANIMATE_AFTER_DOWNLOAD: {
			mAnimateImageAppearance = false;
			mAnimateImageAppearanceAfterDownload = true;
			break;
		}
		case ANIMATE_NEVER: {
			mAnimateImageAppearance = false;
			mAnimateImageAppearanceAfterDownload = false;
			break;
		}
		default:
			break;
		}
	}

	/**
	 * 设置下载事件完成监听器
	 */
	public void setBitmapLoaderCallback(BitmapLoaderCallback callback) {
		mCallback = callback;
	}

	/**
	 * 下载图片, 不适用于每次更新的ImageView
	 * @param url 图片资源URL地址
	 * @param imageView 关联的ImageView
	 */
	public void download(String serverAddress,String url, ImageView imageView) {
		//实例化一个下载器
		Download d = new Download(serverAddress,url, imageView);
		//下载图片
		d.loadImage();
	}

	/**
	 * 下载图片，此方法适用于经常刷新的图片下载, 例如验证码等
	 * @param url 图片资源URL地址
	 * @param imageView 关联的ImageView
	 */
	public void reDownload(String serverAddress, String url, ImageView imageView) {
		//实例化一个下载器
		Download d = new Download(serverAddress, url, imageView);
		//重新下载图片
		d.reLoadImage();
	}
	
	/**
	 * 从SD卡加载图片
	 */
	public void loadFromSD(String path, ImageView imageView){
		Download d = new Download(imageView,path);
		d.loadFromSD();
	}

	/**
	 * 取消所有的下载
	 */
	public void cancelAllDownloads() {
		mQueuedDownloads.clear();
		for (Download download : mRunningDownloads) {
			BitmapDownloaderTask task = download.getBitmapDownloaderTask();
			if (task != null) {
				task.cancel(true);
			}
		}
		mRunningDownloads.clear();
	}

	/**
	 * 此类才是真正的图片下载器
	 * 图片下载器的运行时，优先从内存缓存中查找，如果存在，则直接使用，
	 * 如果不存在，则在SD缓存目录中加载，如果SD卡中存在，则使用，
	 * 如果SD卡中也不存在，则直接从网络上下载
	 */
	public class Download implements BitmapDownloaderTask.BitmapDownloadListener, BitmapLoaderTask.BitmapLoadListener, BitmapSDLoaderTask.BitmapSDLoadListener {
		/**
		 * 位图资源的网络URL地址
		 */
		private String mUrl;
		/**
		 * 弱引用关联到ImageView引用
		 */
		private WeakReference<ImageView> mImageViewRef;
		/**
		 * 网络下载器实例
		 */
		private BitmapDownloaderTask mBitmapDownloaderTask;
		/**
		 * SD卡缓存目录图片加载器实例
		 */
		private BitmapLoaderTask mBitmapLoaderTask;
		
		/**
		 * 仅仅从SD卡加载图片
		 */
		private BitmapSDLoaderTask mBitmapSDLoaderTask;
		/**
		 * 是否已取消
		 */
		private boolean mIsCancelled;
		/**
		 * 是否已经下载过
		 */
		private boolean mWasDownloaded = false;
		
		//private Context mContext;

		/**
		 * 图片下载器，构造器
		 * @param url 图片
		 * @param imageView
		 */
		public Download(String serverAddress, String url, ImageView imageView) {
			//mContext = imageView.getContext().getApplicationContext();
			//拼装成URL
			this.mUrl = serverAddress + url;
			//弱引用
			this.mImageViewRef = new WeakReference<ImageView>(imageView);
			mIsCancelled = false;
			//取消原先的图片，设置成默认图片
			imageView.setImageDrawable(null);
			//imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.image_default));
			//imageView.setBackgroundResource(R.drawable.image_default);
			imageView.setImageResource(R.drawable.image_default);
		}

		public Download(ImageView imageView, String path){
			//mContext = imageView.getContext().getApplicationContext();
			this.mUrl = path;
			this.mImageViewRef = new WeakReference<ImageView>(imageView);
			mIsCancelled = false;
			mIsCancelled = false;
			//取消原先的图片，设置成默认图片
			imageView.setImageDrawable(null);
			//imageView.setBackgroundResource(R.drawable.image_default);
		}
		
		/**
		 * 获取网络下载器实例
		 * @return 网络下载器实例
		 */
		public BitmapDownloaderTask getBitmapDownloaderTask() {
			return mBitmapDownloaderTask;
		}

		/**
		 * 获取弱应用的imageView
		 * @return 弱应用的imageView
		 */
		public ImageView getImageView() {
			return mImageViewRef.get();
		}

		/**
		 * 返回图片的URL资源地址
		 * @return 图片的URL资源地址
		 */
		public String getUrl() {
			return mUrl;
		}

		/**
		 * 加载图片，不适合经常需要刷新的图片
		 * 先判断内存缓存是否存在，如果存在则加载此图片
		 * 如果内存缓存不存在，则从SD卡加载图片
		 * 如果SD卡中此图片存在，则加载此图片，并将其添加到内存缓存中
		 * 如果SD卡中也没有这个图片，则执行网络下载
		 */
		public void loadImage() {
			ImageView imageView = mImageViewRef.get();
			if (imageView != null) {
				//拿到缓存的Bitmap
				Bitmap cachedBitmap = mBitmapCache.getBitmap(mUrl);
				// find the old download, cancel it and set this download as the current
				// download for the imageview
				//通过tag拿到旧的下载器实例.并取消掉
				Download oldDownload = (Download) imageView.getTag(DOWNLOAD_TAG);
				if (oldDownload != null) {
					oldDownload.cancel();
					Logg.d(TAG, "oldDownload.cancel()");
				}
				//如果缓存图片存在
				if (cachedBitmap != null) {
					Logg.d(TAG, "cachedBitmap != null");
					mWasDownloaded = false;
					BitmapDrawable bm = new BitmapDrawable(imageView.getResources(), cachedBitmap);
					loadDrawable(bm);
					imageView.setTag(DOWNLOAD_TAG, null);
				} else {
					//内存中的缓存图片不存在
					Logg.d(TAG, "cachedBitmap == null");
					imageView.setTag(DOWNLOAD_TAG, this);
					loadFromDisk(imageView);
				}
			}
		}
		
		/**
		 * 从SD卡加载图片
		 */
		public void loadFromSD(){
			ImageView imageView = mImageViewRef.get();
			if(imageView != null) {
				//拿到缓存的Bitmap
				Bitmap cachedBitmap = mBitmapCache.getBitmap(mUrl);
				// find the old download, cancel it and set this download as the current
				// download for the imageview
				//通过tag拿到旧的下载器实例.并取消掉
				Download oldDownload = (Download) imageView.getTag(DOWNLOAD_TAG);
				if (oldDownload != null) {
					oldDownload.cancel();
					imageView.setTag(DOWNLOAD_TAG, null);
					Logg.d(TAG, "oldDownload.cancel()");
				}
				//如果缓存图片存在
				if (cachedBitmap != null) {
					Logg.d(TAG, "cachedBitmap != null");
					mWasDownloaded = false;
					BitmapDrawable bm = new BitmapDrawable(imageView.getResources(), cachedBitmap);
					loadDrawable(bm);
					imageView.setTag(DOWNLOAD_TAG, null);
				} else {
					//内存中的缓存图片不存在
					Logg.d(TAG, "cachedBitmap == null");
					imageView.setTag(DOWNLOAD_TAG, this);
					loadFromSD(imageView);
				}
			}
		}
		
		/**
		 * 重新下载图片,适用于需要每次更新的图片
		 */
		public void reLoadImage() {
			ImageView imageView = mImageViewRef.get();
			if (imageView != null) {
				Logg.out("reLoadImage");
				imageView.setTag(DOWNLOAD_TAG, this);
				doDownload();
			}
		}

		/**
		 * 执行网络下载
		 */
		public void doDownload() {
			if (mIsCancelled) {
				// if the download has been cancelled, do not download
				// this image, but start the next one
				if (!mQueuedDownloads.isEmpty() && mRunningDownloads.size() < mMaxDownloads) {
					Download d = mQueuedDownloads.remove(0);
					d.doDownload();
				}
				return;
			}
			ImageView imageView = mImageViewRef.get();
			if (imageView != null && imageView.getTag(DOWNLOAD_TAG) == this) {
				if(TextUtils.isEmpty(DEFAULT_PATH_PIC) || !isDirExists(DEFAULT_PATH_PIC)) {
					//如果缓存目录为空，则将图片缓存到/data/data/package/cache下面
					DEFAULT_PATH_PIC = imageView.getContext().getApplicationContext().getCacheDir().getAbsolutePath();
					mBitmapDownloaderTask = new BitmapDownloaderTask(imageView, this);
				} else {
					//将图片下载到指定的缓存目录
					mBitmapDownloaderTask = new BitmapDownloaderTask(imageView, DEFAULT_PATH_PIC, this);
				}
				//设置下载中的图片
				loadInProgressDrawable(imageView);
				//执行下载
				mBitmapDownloaderTask.execute(mUrl);
				Logg.d(TAG, "bbbbbbbbbbbbbbbbbbbbb  doDownload: " + mUrl);
				//将当前下载器加入到正在下载列表中
				mRunningDownloads.add(this);
			}
		}
		
		public boolean isDirExists(String path){
			File file = new File(path);
			if(file.exists() && file.isDirectory()) {
				return true;
			}
			return false;
		}

		/**
		 * 是否开始下载
		 * @return ture如果已经在下载，false还未开始下载
		 */
		private boolean isBeingDownloaded() {
			for (Download download : mRunningDownloads) {
				if (download == null) {
					continue;
				}
				ImageView otherImageView = download.getImageView();
				ImageView thisImageView = getImageView();
				if (thisImageView == null || otherImageView == null) {
					continue;
				}
				if (otherImageView.equals(thisImageView) && download.getUrl().equals(mUrl)) {
					return true;
				}
			}
			return false;
		}
		
		/**
		 * 从SD卡缓存木中加载图片,并显示在ImageView上
		 * @param imageView 图片关联的ImageView
		 */
		private void loadFromDisk(ImageView imageView) {
			if (imageView != null && !mIsCancelled) {
				if(DEFAULT_PATH_PIC == null || !isDirExists(DEFAULT_PATH_PIC)) {
					mBitmapLoaderTask = new BitmapLoaderTask(imageView, this);
				} else {
					mBitmapLoaderTask = new BitmapLoaderTask(imageView, DEFAULT_PATH_PIC, this);
				}
				try {
					mBitmapLoaderTask.execute(mUrl,"refresh");
				} catch (RejectedExecutionException e) {
				}
			}
		}
		
		/**
		 * 仅仅从sd卡加载图片文件,并显示在imageView上
		 * 
		 */
		private void loadFromSD(ImageView imageView){
			if(imageView != null && !mIsCancelled) {
				mBitmapSDLoaderTask = new BitmapSDLoaderTask(imageView, this);
				try {
					//Logg.out("Loading image form filesysem path= " + mUrl);
					mBitmapSDLoaderTask.execute(mUrl, "refresh");
				} catch(RejectedExecutionException e) {
					
				}
			} 
		}
		

		/**
		 * 取消下载, 网络下载和SD卡加载全部取消
		 */
		private void cancel() {
			Logg.d(TAG, "cancel requested for: " + mUrl);
			mIsCancelled = true;
			//从列表中移除
			if (mQueuedDownloads.contains(this)) {
				mQueuedDownloads.remove(this);
			}
			//取消网络下载器
			if (mBitmapDownloaderTask != null) {
				mBitmapDownloaderTask.cancel(true);
			}
			//取消SD卡加载器
			if (mBitmapLoaderTask != null) {
				mBitmapLoaderTask.cancel(true);
			}
		}

		/**
		 * 查找同一个ImageView在下载中列表里下载不同URL资源的ID
		 * @return 同一个ImageView下载不同URL资源的ID, -1如果没有其他的下载
		 */
		private int indexOfDownloadWithDifferentURL() {
			for (Download download : mRunningDownloads) {
				if (download == null) {
					continue;
				}
				ImageView otherImageView = download.getImageView();
				ImageView thisImageView = getImageView();
				if (thisImageView == null || otherImageView == null) {
					continue;
				}
				if (otherImageView.equals(thisImageView) && !download.getUrl().equals(mUrl)) {
					return mRunningDownloads.indexOf(download);
				}
			}
			return -1;
		}

		/**
		 * 查看此下载是否已在下载列表中
		 * @return true如果已有相同下载，false列表中不存在相同下载
		 */
		private boolean isQueuedForDownload() {
			for (Download download : mQueuedDownloads) {
				if (download == null) {
					continue;
				}
				ImageView otherImageView = download.getImageView();
				ImageView thisImageView = getImageView();
				if (thisImageView == null || otherImageView == null) {
					continue;
				}
				if (otherImageView.equals(thisImageView) && download.getUrl().equals(mUrl)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * 查看此下载是否已在下载列表中，如果存在返回其在列表中的位置
		 * @return 另外的相同下载在列表中的位置，-1如果不存在其他相同下载
		 */
		private int indexOfQueuedDownloadWithDifferentURL() {
			for (Download download : mQueuedDownloads) {
				if (download == null) {
					continue;
				}
				ImageView otherImageView = download.getImageView();
				ImageView thisImageView = getImageView();
				if (thisImageView == null || otherImageView == null) {
					continue;
				}
				if (otherImageView.equals(thisImageView) && !download.getUrl().equals(mUrl)) {
					return mQueuedDownloads.indexOf(download);
				}
			}
			return -1;
		}

		/**
		 * 查看此下载URL是否已在列表中
		 * @return true如果已有别的在下载此URL资源，false不存在其他下载在下载此URL资源
		 */
		private boolean isAnotherQueuedOrRunningWithSameUrl() {
			for (Download download : mQueuedDownloads) {
				if (download == null) {
					continue;
				}
				if (download.getUrl().equals(mUrl)) {
					return true;
				}
			}
			for (Download download : mRunningDownloads) {
				if (download == null) {
					continue;
				}
				if (download.getUrl().equals(mUrl)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * 加载图片显示到关联的ImageView上
		 * @param d 设置到ImageView上的drawable
		 */
		private void loadDrawable(Drawable d) {
			loadDrawable(d, true);
		}

		/**
		 * 加载图片显示到关联的ImageView上
		 * @param d 设置到ImageView上的drawable
		 * @param animate 是否显示动画
		 */
		private void loadDrawable(Drawable d, boolean animate) {
			Logg.d(TAG, "loadDrawable: " + d);
			ImageView imageView = getImageView();
			if (imageView != null) {
				if (animate && (mAnimateImageAppearance || (mAnimateImageAppearanceAfterDownload && mWasDownloaded))) {
					Drawable current = imageView.getDrawable();
					if (current == null) {
						current = new ColorDrawable(Color.TRANSPARENT);
					}
					Drawable[] layers = { current, d };
					BitmapTransitionDrawable drawable = new BitmapTransitionDrawable(layers);
					drawable.setTransitionCallback(new BitmapTransitionDrawable.BitmapTransitionCallback() {

						@Override
						public void onStarted() {
						}

						@Override
						public void onEnded() {
							ImageView imageView = getImageView();
							// the imageview tag must be null as we've already removed
							// ourselves as the tag from the imageview. If a new downloader is
							// using the imageview, ir would have set itself as the tag
							if (imageView != null && imageView.getTag() == null) {
								Drawable d = ((BitmapTransitionDrawable) imageView.getDrawable()).getDrawable(1);
								imageView.setImageDrawable(d);
								if (mCallback != null) {
									mCallback.onLoaded(imageView);
								}
							}
						}
					});
					imageView.setImageDrawable(drawable);
					 // fade out the old image
					drawable.setCrossFadeEnabled(true);
					drawable.startTransition(200);
				} else {
					imageView.setImageDrawable(d);
					if (mCallback != null) {
						mCallback.onLoaded(imageView);
					}
				}
			}
		}

		/**
		 * 下载完成的时候被回调
		 * 下载完成后，从SD卡加载，并将其添加到内存缓存中
		 * 如果队列中还有未下载的URL资源，则从队列中出队一个执行下载
		 */
		@Override
		public void onComplete(Bitmap bitmap) {
			Logg.d(TAG, "onComplete: " + mUrl);
			//从下载列表中移除
			mRunningDownloads.remove(this);
			//设置已下载
			mWasDownloaded = true;
			
			/*ImageView imageView = mImageViewRef.get();
			if (imageView != null && this == imageView.getTag(DOWNLOAD_TAG)) {
				//如果imageview还未被回收，从SD卡加载图片
				loadFromDisk(getImageView());
			}*/
			loadBitmap(bitmap); //直接加载图片

			//查看此URL的其他下载
			ArrayList<Download> duplicates = mDuplicateDownloads.get(mUrl);
			if (duplicates != null) {
				for (Download dup : duplicates) {
					Logg.d(TAG, "onComplete: " + dup.mUrl);
					// load the image.
					//相同下载挨个通知去加载图片
					if(dup != null && dup.getImageView() != null) {
						if (dup.getImageView().getTag(DOWNLOAD_TAG) == dup) {
							//dup.loadFromDisk(dup.getImageView());
							dup.loadBitmap(bitmap);
						}
					}
				}
				//移除这些相同的URL下载
				mDuplicateDownloads.remove(mUrl);
			}

			//获取下一个下载，继续下载
			if (!mQueuedDownloads.isEmpty()) {
				Download d = mQueuedDownloads.remove(0);
				d.doDownload();
			}
		}

		/**
		 * 下载错误的时候回调此方法
		 */
		@Override
		public void onError() {
			Logg.d(TAG, "onError: " + mUrl);
			mRunningDownloads.remove(this);
			ImageView imageView = mImageViewRef.get();
			mWasDownloaded = true;
			if (imageView != null) {
				loadErrorDrawable(imageView);
			}

			if (imageView != null && this == imageView.getTag(DOWNLOAD_TAG)) {
				imageView.setTag(DOWNLOAD_TAG, null);
			}
			if (!mQueuedDownloads.isEmpty()) {
				Download d = mQueuedDownloads.remove(0);
				d.doDownload();
			}
		}

		/**
		 * 加载下载错误时图片
		 * @param imageView 图片关联的ImageView
		 */
		private void loadErrorDrawable(ImageView imageView) {
			if (mErrorDrawableResource == -1 && mErrorDrawable != null) {
				imageView.setImageDrawable(mErrorDrawable);
			} else if (mErrorDrawableResource != -1) {
				imageView.setImageResource(mErrorDrawableResource);
			} else {
				imageView.setImageResource(R.drawable.image_default);
				//imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.image_default));
			}
		}

		/**
		 * 显示下载中的图片
		 * @param imageView 图片关联的ImageView
		 */
		private void loadInProgressDrawable(ImageView imageView) {
			if (mInProgressDrawableResource == -1 && mInProgressDrawable != null) {
				imageView.setImageDrawable(mInProgressDrawable);
			} else if (mInProgressDrawableResource != -1) {
				imageView.setImageResource(mInProgressDrawableResource);
			} else {
				imageView.setImageResource(R.drawable.image_default);
				//imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.image_default));
			}
		}

		/**
		 * 下载取消时，回调此方法
		 * 取消时，从列表中出队，下载下一个
		 */
		@Override
		public void onCancel() {
			mIsCancelled = true;
			Logg.d(TAG, "onCancel: " + mUrl);
			mRunningDownloads.remove(this);

			ImageView imageView = mImageViewRef.get();
			if (imageView != null && this == imageView.getTag(DOWNLOAD_TAG)) {
				imageView.setTag(DOWNLOAD_TAG, null);
			}
			if (!mQueuedDownloads.isEmpty()) {
				Download d = mQueuedDownloads.remove(0);
				Logg.d(TAG, "starting DL of: " + d.getUrl());
				d.doDownload();
			}
		}

		/**
		 * 文件在文件系统中未找到时回调此方法
		 * 处理方法是如果此文件不在，查找是否有其他在下载此URL，
		 * 如果没有其他下载，则加入到下载队列中
		 */
		@Override
		public void notFound() {
			Logg.d(TAG, "notFound: " + mUrl);
			if (mIsCancelled){
				return;
			}
			ImageView imageView = getImageView();

			if (imageView == null || this != imageView.getTag(DOWNLOAD_TAG)) {
				return;
			}

			loadInProgressDrawable(imageView);

			if (isAnotherQueuedOrRunningWithSameUrl()) {
				if (mDuplicateDownloads.containsKey(mUrl)) {
					ArrayList<Download> arr = mDuplicateDownloads.get(mUrl);
					arr.add(this);
					mDuplicateDownloads.put(mUrl, arr);
				} else {
					ArrayList<Download> arr = new ArrayList<Download>();
					arr.add(this);
					mDuplicateDownloads.put(mUrl, arr);
				}
			} else {
				// check if this imageView is being used with a different URL, if so
				// cancel the other one.
				int queuedIndex = indexOfQueuedDownloadWithDifferentURL();
				int downloadIndex = indexOfDownloadWithDifferentURL();
				while (queuedIndex != -1) {
					mQueuedDownloads.remove(queuedIndex);
					Logg.d(TAG, "notFound(Removing): " + mUrl);
					queuedIndex = indexOfQueuedDownloadWithDifferentURL();
				}
				if (downloadIndex != -1) {
					//取消掉其他所有加载此URL图片的加载器
					Download runningDownload = mRunningDownloads.get(downloadIndex);
					BitmapDownloaderTask downloadTask = runningDownload.getBitmapDownloaderTask();
					if (downloadTask != null) {
						downloadTask.cancel(true);
						Logg.d(TAG, "notFound(Cancelling): " + mUrl);
					}
				}

				//添加到下载队列中，执行下载
				if (!(isBeingDownloaded() || isQueuedForDownload())) {
					if (mRunningDownloads.size() >= mMaxDownloads) {
						Logg.d(TAG, "notFound(Queueing): " + mUrl);
						mQueuedDownloads.add(this);
					} else {
						Logg.d(TAG, "notFound(Downloading): " + mUrl);
						doDownload();
					}
				}
			}
		}

		/**
		 * SD卡加载图片完成后，回调此方法，将图片显示到ImageView上，
		 * 并添加到内存缓存中
		 * @param b SD卡加载器加载完成返回的位图
		 */
		@Override
		public void loadBitmap(Bitmap b) {
			Logg.d(TAG, "loadBitmap: " + mUrl);
			mBitmapCache.addBitmap(mUrl, b);
			ImageView imageView = getImageView();
			if (imageView != null && this == imageView.getTag(DOWNLOAD_TAG)) {
				BitmapDrawable bm = new BitmapDrawable(imageView.getResources(), b);
				loadDrawable(bm);
				imageView.setTag(DOWNLOAD_TAG, null);
			}
			mWasDownloaded = false;
		}

		/**
		 * 当SD卡加载错误时，回调此方法
		 */
		@Override
		public void onLoadError() {
			Logg.d(TAG, "onLoadError: " + mUrl);
			ImageView imageView = getImageView();
			if (imageView != null && this == imageView.getTag(DOWNLOAD_TAG)) {
				imageView.setTag(DOWNLOAD_TAG, null);
				loadErrorDrawable(imageView);
			}
			notFound(); //破损文件已删除，直接未找到，重新下载
		}

		/**
		 * SD卡加载器取消时，回调此方法
		 * 设置imageView显示加载中的图片
		 */
		@Override
		public void onLoadCancelled() {
			Logg.d(TAG, "onLoadCancelled: " + mUrl);
			ImageView imageView = getImageView();
			if (imageView != null && this == imageView.getTag(DOWNLOAD_TAG)) {
				imageView.setTag(DOWNLOAD_TAG, null);
				loadInProgressDrawable(imageView);
			}
		}

		/**
		 * 文件系统里面没有找到这张图片
		 */
		@Override
		public void sdNotFound() {
			Logg.d(TAG, "notFound: " + mUrl);
			if (mIsCancelled){
				return;
			}
			ImageView imageView = getImageView();
			if (imageView == null || this != imageView.getTag(DOWNLOAD_TAG)) {
				return;
			}
			loadInProgressDrawable(imageView);

			//这段代码无用，因为SD卡加载根本不在队列里面
			/*if (isAnotherQueuedOrRunningWithSameUrl()) {
				if (mDuplicateDownloads.containsKey(mUrl)) {
					ArrayList<Download> arr = mDuplicateDownloads.get(mUrl);
					arr.add(this);
					mDuplicateDownloads.put(mUrl, arr);
				} else {
					ArrayList<Download> arr = new ArrayList<Download>();
					arr.add(this);
					mDuplicateDownloads.put(mUrl, arr);
				}
			} else {
				// check if this imageView is being used with a different URL, if so
				// cancel the other one.
				int queuedIndex = indexOfQueuedDownloadWithDifferentURL();
				int downloadIndex = indexOfDownloadWithDifferentURL();
				while (queuedIndex != -1) {
					mQueuedDownloads.remove(queuedIndex);
					Logg.d(TAG, "notFound(Removing): " + mUrl);
					queuedIndex = indexOfQueuedDownloadWithDifferentURL();
				}
				if (downloadIndex != -1) {
					//取消掉其他所有加载此URL图片的加载器
					Download runningDownload = mRunningDownloads.get(downloadIndex);
					BitmapDownloaderTask downloadTask = runningDownload.getBitmapDownloaderTask();
					if (downloadTask != null) {
						downloadTask.cancel(true);
						Logg.d(TAG, "notFound(Cancelling): " + mUrl);
					}
				}
			}*/
		}

		/**
		 * 从文件系统里面加载文件完成
		 */
		@Override
		public void sdLoadBitmap(Bitmap b) {
			Logg.d(TAG, "loadBitmap from filesystem: " + mUrl);
			mBitmapCache.addBitmap(mUrl, b);
			ImageView imageView = getImageView();
			if (imageView != null && this == imageView.getTag(DOWNLOAD_TAG)) {
				BitmapDrawable bm = new BitmapDrawable(imageView.getResources(), b);
				loadDrawable(bm);
				imageView.setTag(DOWNLOAD_TAG, null);
			}
			mWasDownloaded = false;
		}

		/**
		 * 从sd卡里加载图片错误
		 */
		@Override
		public void sdOnLoadError() {
			Logg.d(TAG, "onSDLoadError from file system: " + mUrl);
			ImageView imageView = getImageView();
			if (imageView != null && this == imageView.getTag(DOWNLOAD_TAG)) {
				imageView.setTag(DOWNLOAD_TAG, null);
				loadErrorDrawable(imageView);
			}
		}

		/**
		 * 从sd卡里加载图片取消
		 */
		@Override
		public void sdOnLoadCancelled() {
			Logg.d(TAG, "onSDLoadCancelled from filesystem: " + mUrl);
			ImageView imageView = getImageView();
			if (imageView != null && this == imageView.getTag(DOWNLOAD_TAG)) {
				imageView.setTag(DOWNLOAD_TAG, null);
				loadInProgressDrawable(imageView);
			}
		}
	}

}
