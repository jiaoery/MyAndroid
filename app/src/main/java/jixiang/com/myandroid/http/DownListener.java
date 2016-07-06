package jixiang.com.myandroid.http;

public interface DownListener {

	/**
	 * 开始下载
	 */
	void onStartDownLoad();
	
	/**
	 * 下载进度改变
	 * @param completeRate
	 */
	void onCompleteRateChanged(int completeRate);
	
	/**
	 * 下载完成
	 */
	void onDownloadCompleted(String fileName);
}
