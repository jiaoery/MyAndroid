package jixiang.com.myandroid.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class AsyncHttpClient implements OnCompleteListener{
	private static String JSESSIONID; // 定义一个静态的字段，保存sessionID
	
	//线程池最大容量
	private static final int MAX_THREAD_POOL_NUM = 5; 
	private static final int MAX_TRY_TIMES = 3;//最大失败尝试次数
	private static AsyncHttpClient asyncHttpClient;
	
	//正在运行的线程列表
	private static ArrayList<HttpClientThread> runningHttpClientThreads;
	//排队的线程列表
	private static ArrayList<HttpClientThread> queuedHttpClientThreads;
	
	
	private AsyncHttpClient() {
		//初始化正在运行的列表和排队的列表
		if(runningHttpClientThreads == null) {
			runningHttpClientThreads = new ArrayList<HttpClientThread>();
		}
		if(queuedHttpClientThreads == null) {
			queuedHttpClientThreads = new ArrayList<HttpClientThread>();
		}
	}
	
	public static AsyncHttpClient getInstance() {
		if(asyncHttpClient ==null) {
			asyncHttpClient = new AsyncHttpClient();
		}
		return asyncHttpClient;
	}

	public void get(String serverAddress, String url, HttpParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {
		//新生成一个请求线程
		HttpClientThread clientThread = new HttpClientThread(serverAddress, url, params, asyncHttpResponseHandler, 0, this);
		//加入到排队队列中
		queuedHttpClientThreads.add(clientThread);
		//可运行线程还未满,且等待线程队列中还有线程
		if(runningHttpClientThreads.size() <=  MAX_THREAD_POOL_NUM && queuedHttpClientThreads.size() > 0) {
			//从等待队列中取出第一个，加入到运行线程中去执行
			HttpClientThread httpClientThread = queuedHttpClientThreads.remove(0);
			if(httpClientThread != null) {
				try {
					if(!httpClientThread.isRunning) {
						httpClientThread.start();
						runningHttpClientThreads.add(httpClientThread);
					}
				} catch (IllegalThreadStateException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void post(String serverAddress, String url, HttpParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {
		//新生成一个请求线程
		HttpClientThread clientThread = new HttpClientThread(serverAddress, url, params, asyncHttpResponseHandler, 1, this);
		//加入到排队队列中
		queuedHttpClientThreads.add(clientThread);
		//可运行线程还未满,且等待线程队列中还有线程
		if(runningHttpClientThreads.size() <=  MAX_THREAD_POOL_NUM && queuedHttpClientThreads.size() > 0) {
			//从等待队列中取出第一个，加入到运行线程中去执行
			HttpClientThread httpClientThread = queuedHttpClientThreads.remove(0);
			if(httpClientThread != null) {
				try {
					if(!httpClientThread.isRunning) {
						httpClientThread.start();
						runningHttpClientThreads.add(httpClientThread);
					}
				} catch (IllegalThreadStateException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 文件上传， 可批量上传文件, 总文件大小不能超过2M
	 * 使用方法
	 * HttpParams httpParams = new HttpParams();
	 * httpParams.put("12.png",  Environment.getExternalStorageDirectory().toString() + File.separator + "12.png");
	 * httpParams.put("13.png",  Environment.getExternalStorageDirectory().toString() + File.separator + "13.png");
	 * HttpClientUtils.getInstance().upload("http://192.168.1.219/", "web/likun/single_upload.php", httpParams, new AsyncHttpResponseHandler(){
	 *		@Override
	 *		public void onStartUpload() {
	 *			System.out.println("开始上传");
	 *		}
	 *		
	 *		@Override
	 *		public void onUploadProgress(int progress) {
	 *			System.out.println("upload progress=" + progress);
	 *			Message message = new Message();
	 *			message.what = 124;
	 *			message.arg1 = progress;
	 *			handler.sendMessage(message);
	 *		}
	 *		
	 *		@Override
	 *		public void onUploadCompleted() {
	 *			System.out.println("上传完成");
	 *		}
	 *		
	 *		@Override
	 *		public void onSuccess(JSONArray jsonArray) {
	 *			System.out.println("图片在服务器的地址:" + jsonArray.toString());
	 *		}
	 *		
	 *		@Override
	 *		public void onFailure(String result, int statusCode,String errorResponse) {
	 *			System.out.println("result=" + result + ", statusCode=" + statusCode + ", errorResponse=" + errorResponse);
	 *		}
	 *		
	 *		
	 *	});
	 */
	public void upload(String serverAddress, String url, HttpParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {
		//新生成一个请求线程
		HttpClientThread clientThread = new HttpClientThread(serverAddress, url, params, asyncHttpResponseHandler, 2, this);
		//加入到排队队列中
		queuedHttpClientThreads.add(clientThread);
		//可运行线程还未满,且等待线程队列中还有线程
		if(runningHttpClientThreads.size() <=  MAX_THREAD_POOL_NUM && queuedHttpClientThreads.size() > 0) {
			//从等待队列中取出第一个，加入到运行线程中去执行
			HttpClientThread httpClientThread = queuedHttpClientThreads.remove(0);
			if(httpClientThread != null) {
				try {
					if(!httpClientThread.isRunning) {
						httpClientThread.start();
						runningHttpClientThreads.add(httpClientThread);
					}
				} catch (IllegalThreadStateException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 当一个请求线程执行完时，会回调此方法
	 * 从运行下载列表中移除此下载
	 * 在从排队列表中拿出一个出来执行
	 */
	@Override
	public void onComplete(HttpClientThread clientThread) {
		Logg.out("onComplete");
		//加同步锁
		synchronized (runningHttpClientThreads) {
			if(runningHttpClientThreads.contains(clientThread)) {
				//从队列中一次次线程
				runningHttpClientThreads.remove(clientThread);
				Logg.out("runningHttpClientThreads.size()=" + runningHttpClientThreads.size());
			}
		}
		//从等待队列线程中取出一个线程并执行
		//这儿没有加锁，可以允许多出一两个线程执行
		if(runningHttpClientThreads.size() <= MAX_THREAD_POOL_NUM && queuedHttpClientThreads.size() > 0) {
			HttpClientThread httpClientThread = queuedHttpClientThreads.remove(0);
			if(httpClientThread != null) {
				try {
					if(!httpClientThread.isRunning) {
						httpClientThread.start();
						runningHttpClientThreads.add(httpClientThread);
					}
				} catch (IllegalThreadStateException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 网络请求线程, 区分get和post
	 * @author eibit
	 */
	protected class HttpClientThread extends Thread{
		//HttpClient
		DefaultHttpClient httpClient =  new DefaultHttpClient();
		//URLConnection
		URLConnection urlConnection;
		//HttpURLConnection
		HttpURLConnection httpURLConnection;

		private String serverAddress;
		private String url;
		private HttpParams params;
		private AsyncHttpResponseHandler asyncHttpResponseHandler;
		//requestMethod 0 get, 1 post 
		private int requestMethod;
		
		private int statusCode;
		private boolean isSuccess = false;
		private String errorMsg;
		//是否在运行中
		private boolean isRunning = false;
		//是否已经完成
		//private boolean isComplete= false;
		//执行完成事件监听接口
		private OnCompleteListener listener;
		
		
		public HttpClientThread(String serverAddress, String url, HttpParams params, AsyncHttpResponseHandler asyncHttpResponseHandler, int requestMethod, OnCompleteListener listener) {
			this.serverAddress = serverAddress;
			this.url = url;
			this.params = params;
			this.asyncHttpResponseHandler = asyncHttpResponseHandler;
			this.requestMethod = requestMethod;
			this.listener = listener;
		}
		
		public void run() {
			isRunning = true;
			Logg.out(Thread.currentThread().getName() + " is Running");
			if(requestMethod == 0) {
				//GET
				doGet(serverAddress, url, params, asyncHttpResponseHandler);
			} else if (requestMethod == 1){
				//POST
				doPost(serverAddress, url, params, asyncHttpResponseHandler);
			} else if(requestMethod == 2) {
				doUpload(serverAddress, url, params, asyncHttpResponseHandler);
			}
			//isComplete = true;
			if(listener != null) {
				listener.onComplete(this);
			}
		}
		
		/**
		 * 拼接GET方法的URL地址
		 * @param serverAddress 请求的server地址前缀
		 * @param url 请求的路径地址
		 * @param params 请求的参数列表
		 * @return 拼接后的完整的URL地址
		 */
		private String getGetUrl(String serverAddress, String url, HttpParams params) {
			String realUrl ="";
			//去掉地址后面的/
			if(serverAddress != null && serverAddress.charAt(serverAddress.length() - 1) == '/') {
				serverAddress = serverAddress.substring(0, serverAddress.length() -1);
			}
			//去掉url前面的/
			if(url != null && url.length() > 0 && url.charAt(0) == '/') {
				url = url.substring(1, url.length());
			}
			//去掉url后面的？
			if(url != null && url.length() > 0 && url.charAt(url.length() - 1) == '?') {
				url = url.substring(0, url.length() -1);
			}
			if(params != null && params.getParamsCount() > 0) {
				realUrl = serverAddress + "/" + url + "?";
				for(int i=0; i< params.getParamsCount(); i++) {
					Param param = params.get(i);
					if(i != 0) { //调整优化选择
						if(param.vObject != null) {
							try {
								String value = URLEncoder.encode(param.vObject.toString(), "utf-8");
								realUrl += "&" + param.key + "=" + value; 
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
								realUrl += "&" + param.key + "=" + param.vObject; 
							}
						} else {
							realUrl += "&" + param.key + "="; 
						}
					} else {
						if(param.vObject != null) {
							try {
								String value = URLEncoder.encode(param.vObject.toString(), "utf-8");
								realUrl += param.key + "=" + value;
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
								realUrl += param.key + "=" + param.vObject;
							}
						} else {
							realUrl += param.key + "=";
						}
					}
				}
			} else {
				realUrl = serverAddress + "/" + url;
			}
			return realUrl;
		}
		
		/**
		 * 执行get请求
		 * @param serverAddress  请求的server地址前缀
		 * @param url 请求的路径地址
		 * @param params  请求的参数列表
		 * @param asyncHttpResponseHandler 下载完成时的回调接口对象
		 */
		private void doGet(String serverAddress, String url, HttpParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {
			String realUrl =getGetUrl(serverAddress, url, params);
			Logg.out("get realUrl="+ realUrl);
			//String response= getResponse(realUrl); //调用 Apache HttpClient
			String response = getResponse(realUrl);
			int tryTimes = 1;
			while(!isSuccess && tryTimes < MAX_TRY_TIMES){
				response = getResponse(realUrl);
				tryTimes++;
				Logg.out("get result eror, try it " + tryTimes + " times again");
			}
			if(isSuccess == true) {
				asyncHttpResponseHandler.onSuccess(response);
				try {
					JSONObject jsonObject = new JSONObject(response);
					if(asyncHttpResponseHandler != null) {
						asyncHttpResponseHandler.onSuccess(jsonObject);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					try {
						JSONArray jsonArray = new JSONArray(response);
						if(asyncHttpResponseHandler != null) {
							asyncHttpResponseHandler.onSuccess(jsonArray);
						}
					} catch (JSONException e1) {
						e1.printStackTrace();
						/*if(asyncHttpResponseHandler != null) {
							asyncHttpResponseHandler.onFailure(response, statusCode, "Convert result to JSON ERROR, please check it");
						}*/
					}
				}
			} 
			else {
				if(asyncHttpResponseHandler != null) {
					asyncHttpResponseHandler.onFailure(response, statusCode, errorMsg);
				}
			}
			
		}
		
		/**
		 * 使用HttpUrlConnection 执行GET请求
		 * @param realUrl 请求的完整的URl地址
		 * @return 请求的返回结果
		 */
		private String myGet(String realUrl) {
			PrintWriter out = null;
			BufferedReader in = null;
			String result = "";
			try {
				URL url = new URL(realUrl);
				httpURLConnection = (HttpURLConnection) url.openConnection();
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
						result += "\n" + line;
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
		
		/**
		 * 使用HttpClient 执行GET请求
		 * @param url 请求的完整的url地址
		 * @return 请求的返回结果
		 */
		private String getResponse(String url) {
			String response= "";
			//httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			try {
				if (null != JSESSIONID) {
					httpGet.setHeader("Cookie", "JSESSIONID=" + JSESSIONID);
				}
				HttpResponse httpResponse = httpClient.execute(httpGet);
				statusCode = httpResponse.getStatusLine().getStatusCode();
				if(statusCode == HttpStatus.SC_OK ) {
					isSuccess = true;
					HttpEntity entity = httpResponse.getEntity();
					/* 获取cookieStore */
					CookieStore cookieStore = httpClient.getCookieStore();
					List<Cookie> cookies = cookieStore.getCookies();
					for (int i = 0; i < cookies.size(); i++) {
						if ("JSESSIONID".equals(cookies.get(i).getName())) {
							JSESSIONID = cookies.get(i).getValue();
							break;
						}
					}
					response = EntityUtils.toString(httpResponse.getEntity());
					/*if(entity != null) {
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
						String line = "";
						while((line = bufferedReader.readLine()) != null) {
							//System.out.println("line=" + line);
							response += line + "\n";
						}
						bufferedReader.close();
					}*/
				} else {
					isSuccess = false;
					errorMsg = "get error response from server";
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return response;
		}
		
		/**
		 * 拼接POST URL 地址
		 * @param serverAddress 请求的URL地址前缀
		 * @param url 请求的url路径部分
		 * @return 拼接好后的完整的URL地址
		 */
		private String getPostUrl(String serverAddress, String url) {
			String realUrl ="";
			//去掉地址后面的/
			if(serverAddress != null && serverAddress.charAt(serverAddress.length() - 1) == '/') {
				serverAddress = serverAddress.substring(0, serverAddress.length() -1);
			}
			//去掉url前面的/
			if(url != null && url.length() > 0 && url.charAt(0) == '/') {
				url = url.substring(1, url.length());
			}
			//去掉url后面的？
			if(url != null && url.length() > 0 && url.charAt(url.length() - 1) == '?') {
				url = url.substring(0, url.length() -1);
			}
			realUrl = serverAddress + "/" + url;
			return realUrl;
		}
		
		
		/**
		 * 执行post请求，并将结果返回
		 * @param serverAddress  请求的URL地址前缀
		 * @param url 请求的url路径部分
		 * @param params 请求参数
		 * @param asyncHttpResponseHandler 请求完成时的回调接口对象
		 */
		private void doPost(String serverAddress, String url, HttpParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {
			String realUrl = getPostUrl(serverAddress, url);
			Logg.out("post realUrl =" + realUrl);
			//String response = postResponse(realUrl, params); //执行apache httpclient
			String paramsString = getMyPostParamsString(params);
			Logg.out("paramsString=" + paramsString);
			String response = postResponse(realUrl, params);
			
			//重新尝试获取值
			int tryTimes = 1;
			while(!isSuccess && tryTimes < MAX_TRY_TIMES){
				response = getResponse(realUrl);
				tryTimes++;
				Logg.out("get result eror, try it " + tryTimes + " times again");
			}
			
			if(isSuccess == true) {
				asyncHttpResponseHandler.onSuccess(response);
				try {
					JSONObject jsonObject = new JSONObject(response);
					if(asyncHttpResponseHandler != null) {
						asyncHttpResponseHandler.onSuccess(jsonObject);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					try {
						JSONArray jsonArray = new JSONArray(response);
						if(asyncHttpResponseHandler != null) {
							asyncHttpResponseHandler.onSuccess(jsonArray);
						}
					} catch (JSONException e1) {
						e1.printStackTrace();
						/*if(asyncHttpResponseHandler != null) {
							asyncHttpResponseHandler.onFailure(response, statusCode, "Convert result to JSON ERROR, please check it");
						}*/
					}
				}
			} else {
				if(asyncHttpResponseHandler != null) {
					asyncHttpResponseHandler.onFailure(response, statusCode, errorMsg);
				}
			}
		}
		

		/**
		 * 使用HttpClient执行POST请求
		 * @param url 请求的url地址
		 * @param params 请求的参数
		 * @return 返回post请求结果
		 */
		private String postResponse(String url, HttpParams params) {
			String result = "";
			//httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			for(int i=0; i< params.getParamsCount(); i++) {
				Param param = params.get(i);
				paramsList.add(new BasicNameValuePair(param.key, param.vObject.toString()));
			}
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(paramsList, HTTP.UTF_8));
				if (null != JSESSIONID) {
					httpPost.setHeader("Cookie", "JSESSIONID=" + JSESSIONID);
				}
				//发送post请求
				HttpResponse httpResponse = httpClient.execute(httpPost);
				//如果服务器成功地返回响应
				statusCode = httpResponse.getStatusLine().getStatusCode();
				if(statusCode == HttpStatus.SC_OK ) {
					isSuccess = true;
					result = EntityUtils.toString(httpResponse.getEntity());
					Logg.out("result =" + result);
					/* 获取cookieStore */
					CookieStore cookieStore = httpClient.getCookieStore();
					List<Cookie> cookies = cookieStore.getCookies();
					for (int i = 0; i < cookies.size(); i++) {
						if ("JSESSIONID".equals(cookies.get(i).getName())) {
							JSESSIONID = cookies.get(i).getValue();
							break;
						}
					}
				} else {
					isSuccess = false;
					errorMsg = "get erro response from server";
				}
			} catch (UnsupportedEncodingException e) {
				isSuccess = false;
				errorMsg = "get erro response from server:" + e.getMessage();
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				isSuccess = false;
				errorMsg = "get erro response from server:" + e.getMessage();
				e.printStackTrace();
			} catch (IOException e) {
				isSuccess = false;
				errorMsg = "get erro response from server:" + e.getMessage();
				e.printStackTrace();
			}
			return result;
		}

		/**
		 * 使用 URLConnection执行网络请求
		 * @param url 请求的url地址
		 * @param params 请求的参数列表
		 * @return 请求的返回结果
		 */
		private String sendPost(String url, String params) {
			PrintWriter out = null;
			BufferedReader in = null;
			String result = "";
			try {
				URL realUrl = new URL(url);
				// 打开与URL之间的连接
				urlConnection = realUrl.openConnection();
				// 设置通用的请求属性
				// 设置通用的请求属性
				urlConnection.setRequestProperty("accept", "*/*");
				//connection.setRequestProperty("connection", "Keep-Alive");
				urlConnection.setRequestProperty("user-agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
				urlConnection.setRequestProperty("Content-Type",  "application/x-www-form-urlencoded");
				urlConnection.setConnectTimeout(5000);
				// 发送POST请求必须设置如下两行
				urlConnection.setDoOutput(true);
				urlConnection.setDoInput(true);
				// 获取URLConnection对象对应的输入流
				out = new PrintWriter(urlConnection.getOutputStream());
				// 发送请求参数
				out.print(params);
				// flush输出流的缓冲
				out.flush();
				// 定义BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					//System.out.println("line=" + line);
					result += "\n" + line;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {

						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		
		
		/**
		 * 执行post请求，并将结果返回
		 * @param serverAddress  请求的URL地址前缀
		 * @param url 请求的url路径部分
		 * @param params 请求参数
		 * @param asyncHttpResponseHandler 请求完成时的回调接口对象
		 */
		private void doUpload(String serverAddress, String url, HttpParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {
			String realUrl = getPostUrl(serverAddress, url);
			//开始上传回调
			if(asyncHttpResponseHandler != null) {
				asyncHttpResponseHandler.onStartUpload();
			}
			Logg.out("post realUrl =" + realUrl);
			String response = upload(realUrl, params, asyncHttpResponseHandler);
			
			int tryTimes = 1;
			while(!isSuccess && tryTimes < MAX_TRY_TIMES){
				response = getResponse(realUrl);
				tryTimes++;
				Logg.out("get result eror, try it " + tryTimes + " times again");
			}
			
			if(isSuccess == true) {
				asyncHttpResponseHandler.onSuccess(response);
				try {
					JSONObject jsonObject = new JSONObject(response);
					if(asyncHttpResponseHandler != null) {
						asyncHttpResponseHandler.onSuccess(jsonObject);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					try {
						JSONArray jsonArray = new JSONArray(response);
						if(asyncHttpResponseHandler != null) {
							asyncHttpResponseHandler.onSuccess(jsonArray);
						}
					} catch (JSONException e1) {
						e1.printStackTrace();
						/*if(asyncHttpResponseHandler != null) {
							asyncHttpResponseHandler.onFailure(response, statusCode, "Convert result to JSON ERROR, please check it");
						}*/
					}
				}
			} else {
				if(asyncHttpResponseHandler != null) {
					asyncHttpResponseHandler.onFailure(response, statusCode, errorMsg);
				}
			}
		}
		
		private String upload(String url, HttpParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {
			BufferedReader in = null;
			String result = "";
			String end = "\r\n";
			String twoHyphens = "--";
			String boundary = "*****";
			try {
				URL realUrl = new URL(url);
				httpURLConnection = (HttpURLConnection) realUrl.openConnection();
				// 设置通用的请求属性
				// 设置通用的请求属性
				// 发送POST请求必须设置如下两行
				httpURLConnection.setDoOutput(true);
				httpURLConnection.setDoInput(true);
				httpURLConnection.setUseCaches(false);
				httpURLConnection.setRequestMethod("POST");
				httpURLConnection.setRequestProperty("accept", "*/*");
				httpURLConnection.setRequestProperty("connection", "Keep-Alive");
				httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
				httpURLConnection.setRequestProperty("Charset", "UTF-8");
				httpURLConnection.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				httpURLConnection.setConnectTimeout(5000);
				
				long sumSize = 0; //总的文件的大小
				long sendLength = 0; //已发送的总长度
				for(int i=0; i< params.getParamsCount(); i++) {
					if(params.get(i).key.equals("to_path")) {
						//此参数不是文件
					} else {
						String fileName = params.get(i).key; //拿到文件名
						String filePath = params.get(i).vObject.toString(); //拿到文件路径
						File file = new File(filePath);
						if(file.exists()) {
							if(file.length() > 1024*1024*4) {
								isSuccess  = false;
								result = "upload canceled";
								statusCode = 404;
								errorMsg = "One of File's size bigger than 4M, cancel uploading";
								return result;
							}
							sumSize += file.length(); //统计每个文件的长度
						}
					}
				}
				Logg.out("sumSize=" + sumSize);
				/*if(sumSize > 1024*1024*2) { //如果文件大于2M，则取消上传
					isSuccess  = false;
					result = "upload canceled";
					statusCode = 404;
					errorMsg = "File size bigger than 2M, cancel uploading";
					return result;
				}*/
				
				/* 设置DataOutputStream */
				DataOutputStream ds = new DataOutputStream(httpURLConnection.getOutputStream());
				for(int i=0; i< params.getParamsCount(); i++) {
					String fileName = params.get(i).key; //拿到文件名
					String filePath = params.get(i).vObject.toString(); //拿到文件路径
					ds.writeBytes(twoHyphens + boundary + end);
					ds.writeBytes("Content-Disposition: form-data; " + "name=\"file1\";filename=\"" + fileName + "\"" + end);
					ds.writeBytes(end);
					/* 取得文件的FileInputStream */
					FileInputStream fStream = new FileInputStream(filePath);
					/* 设置每次写入1024bytes */
					int bufferSize = 1024;
					byte[] buffer = new byte[bufferSize];
					int length = -1;
					/* 从文件读取数据至缓冲区 */
					while ((length = fStream.read(buffer)) != -1) {
						/* 将资料写入DataOutputStream中 */
						ds.write(buffer, 0, length);
						ds.flush();
						sendLength += length;
						if(asyncHttpResponseHandler != null) {
							asyncHttpResponseHandler.onUploadProgress((int)(sendLength * 100 / sumSize));
						}
					}
					ds.writeBytes(end);
					ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
					/* close streams */
					fStream.close();
					ds.flush();
				}
				ds.close();
				//上传完成回调
				if(asyncHttpResponseHandler != null) {
					asyncHttpResponseHandler.onUploadCompleted();
				}

				// 定义BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(
						httpURLConnection.getInputStream()));
				statusCode = httpURLConnection.getResponseCode();
				if(statusCode == HttpURLConnection.HTTP_OK) {
					isSuccess = true;
					String line;
					while ((line = in.readLine()) != null) {
						//System.out.println("line=" + line);
						result += "\n" + line;
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
		
		/**
		 * 将请求参数拼接成URL格式 name=12345&passwd=123456
		 * @param params 请求参数列表
		 * @return 返回拼接后的参数列表
		 */
		private String getMyPostParamsString(HttpParams params) {
			String paramString ="";
			if(params != null && params.getParamsCount() > 0) {
				for(int i=0; i< params.getParamsCount(); i++) {
					Param param = params.get(i);
					if(i != 0) { //调整优化选择
						if(param.vObject != null) {
							try {
								String value = URLEncoder.encode(param.vObject.toString(), "utf-8");
								paramString += "&" + param.key + "=" + value; 
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
								paramString += "&" + param.key + "=" + param.vObject; 
							}
						} else {
							paramString += "&" + param.key + "="; 
						}
					} else {
						if(param.vObject != null) {
							try {
								String value = URLEncoder.encode(param.vObject.toString(), "utf-8");
								paramString += param.key + "=" + value;
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
								paramString += param.key + "=" + param.vObject;
							}
						} else {
							paramString += param.key + "=";
						}
					}
				}
			} 
			return paramString;
		}
		
		
		/**
		 * 使用HttpURLConnection执行post请求
		 * @param realUrl 请求的url地址
		 * @param params 请求的网络参数列表，格式 name=12345&passwd=123456
		 * @author 请求的返回结果
		 */
		private String myPost(String realUrl, String params) {
			PrintWriter out = null;
			BufferedReader in = null;
			String result = "";
			try {
				URL url = new URL(realUrl);
				httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setConnectTimeout(10 * 1000);
				httpURLConnection.setRequestMethod("POST");
				httpURLConnection.setDoInput(true);
				httpURLConnection.setDoOutput(true);
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
				// 得到文件的大小
				out = new PrintWriter(httpURLConnection.getOutputStream());
				out.print(params);
				out.flush();
				statusCode = httpURLConnection.getResponseCode();
				if(statusCode == HttpURLConnection.HTTP_OK) {
					isSuccess = true;
					// 定义BufferedReader输入流来读取URL的响应
					in = new BufferedReader(new InputStreamReader(
							httpURLConnection.getInputStream()));
					String line;
					while ((line = in.readLine()) != null) {
						//System.out.println("line=" + line);
						result += "\n" + line;
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
}
