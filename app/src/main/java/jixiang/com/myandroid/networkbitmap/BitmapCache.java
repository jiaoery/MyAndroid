package jixiang.com.myandroid.networkbitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
/**
 * 图片缓存
 *
 */
public class BitmapCache {
	private LruCache<String, Bitmap> mBitmapCache;

	public BitmapCache() {
		// 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。 
	    // LruCache通过构造函数传入缓存值，以B为单位。 
		int maxMemory = (int) (Runtime.getRuntime().maxMemory()); 
		// 使用最大可用内存值的1/8作为缓存的大小。 
	    int cacheSize = maxMemory / 8;
	    // by default use 1/8 maxHeapSize as a limit for the in memory Lrucache
		mBitmapCache = new LruCache<String, Bitmap>(cacheSize) { 
			//重写此方法来衡量每张图片的大小，默认返回图片数量。 
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
			
			@Override
			protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
				super.entryRemoved(evicted, key, oldValue, newValue);
			}
			
		};
	}

	/**
	 * 想缓存中添加一张图片
	 * @param url 图片路径，作为key值
	 * @param b 原位图
	 */
	public synchronized void addBitmap(String url, Bitmap b) {
		mBitmapCache.put(url, b);
	}

	/**
	 * 从缓存中获取图片
	 * @param url 图片的路径
	 * @return 缓存的图片， null如果路径为空，或者缓存图片不存在
	 */
	public synchronized Bitmap getBitmap(String url) {
		if (url == null) {
			return null;
		}
		return mBitmapCache.get(url);
	}
	
	/**
	 * 移出此缓存
	 * @param key
	 */
	public synchronized  void remove(String key){
		if(key != null && mBitmapCache != null) {
			Bitmap bm = mBitmapCache.remove(key);
			if(bm != null && !bm.isRecycled()) {
				bm.recycle();
				bm = null;
			}
		}
	}
	
	/**
	 * 清空缓存
	 */
	public void clearCache(){
		if(mBitmapCache != null) {
			if(mBitmapCache.size() > 0) {
				mBitmapCache.evictAll();
			}
			mBitmapCache = null;
		}
	}
}
