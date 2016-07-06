package jixiang.com.myandroid.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

import android.os.AsyncTask;
//params 参数 使用在doInBackground 方法中，为可变参数类型
//Progress 发布进度值的类型
//Result 执行完doInBackground 的返回值类型
public class DownloadFilesTask extends AsyncTask<String, Integer, String> {
	public static interface OnDownLoadComplete{
		void publishProgress(Integer... progress);
		void postResult(String result);
	}
	OnDownLoadComplete complete;
	public void setOnDownLoadComplete(OnDownLoadComplete complete){
		this.complete = complete;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	//后台执行
	//在非主线程中执行的
	protected String doInBackground(String... urls) {
		int count = urls.length;
		String result= "";
		for (int i = 0; i < count; i++) {
			result += myGet(urls[0]);
			publishProgress(i);
		}
		return result;
	}
	//发布进度
	protected void onProgressUpdate(Integer... progress) {
		//setProgressPercent(progress[0]);
		if(complete != null) {
			complete.publishProgress(progress);
		}
	}
	//发布结果
	protected void onPostExecute(String result) {
		if(complete != null) {
			complete.postResult(result);
		}
		//showDialog("Downloaded " + result + " bytes");
	}
	
	/**
	 * 使用HttpUrlConnection 执行GET请求
	 * @param realUrl 请求的完整的URl地址
	 * @return 请求的返回结果
	 */
	private String myGet(String realUrl) {
		int statusCode= -1;
		boolean isSuccess;
		String errorMsg;
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL url = new URL(realUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(10 * 1000);
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setDoInput(true);
			httpURLConnection
					.setRequestProperty(
							"Accept",
							"image/gif, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-app lication, application/cnd.ms- excel, application/vnd.ms-powerpoint, application/msword, */*");
			httpURLConnection.setRequestProperty("Accept-Language", "zh_CN");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection
					.setRequestProperty(
							"User-Agent",
							"Mozilla/4.0(compatible; MSIE 7.0; Windows NT 5.2; Trident/4.0; . NET CTR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			statusCode = httpURLConnection.getResponseCode();
			if(statusCode == HttpURLConnection.HTTP_OK) {
				isSuccess = true;
				// 定义BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(
						httpURLConnection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					//System.out.println("line=" + line);
					result += line;
				}
			} else {
				isSuccess = false;
			}
			httpURLConnection.disconnect();
		} catch (MalformedURLException e) {
			isSuccess = false;
			errorMsg = "url is not correct, please check it";
			e.printStackTrace();
		} catch (ProtocolException e) {
			isSuccess = false;
			errorMsg = "unknown protocol, url is not correct, please check it";
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			isSuccess = false;
			errorMsg = "connection timeout";
			e.printStackTrace();
		} catch (IOException e) {
			isSuccess = false;
			errorMsg = "IOexception occur";
			e.printStackTrace();
		}  finally {
			if(out != null) {
				out.close();
			}
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查找网络错误原因
		switch(statusCode) {
		case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
			errorMsg = "connect to server time out";
			break;
		case HttpURLConnection.HTTP_INTERNAL_ERROR:
			errorMsg = "connect to server internal error";
			break;
		case HttpURLConnection.HTTP_NOT_FOUND:
			errorMsg = "the url not found";
			break;
		case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
			errorMsg = "gateway timeout";
			break;
		}
		return result;
	}
}
