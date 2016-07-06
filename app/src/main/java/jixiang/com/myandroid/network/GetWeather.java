package jixiang.com.myandroid.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import jixiang.com.myandroid.http.AsyncHttpResponseHandler;
import jixiang.com.myandroid.http.HttpClientUtils;
import jixiang.com.myandroid.http.HttpParams;


public class GetWeather extends FragmentActivity{
	/**
	 * {"weatherinfo":{
	 * "city":"北京",
	 * "cityid":"101010100",
	 * "temp":"10",
	 * "WD":"东南风",
	 * "WS":"2级",
	 * "SD":"26%",
	 * "WSE":"2",
	 * "time":"10:25",
	 * "isRadar":"1",
	 * "Radar":"JC_RADAR_AZ9010_JB",
	 * "njd":"暂无实况",
	 * "qy":"1012"
	 * }}
	 * 
	 * 
	 */
	public static class CityWeather{
		public String city;
		public String cityid; 
		public int temp; //温度
		public String WD; //风向
		public String WS;
		public String SD;
		public int WSE;
		public String time;
		public int isRadar;
		public String Radar;
		public String njd;
		public String qy;
	}
	public static class WeatherInfo{
		public CityWeather weatherinfo;
	}
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//new MyThread().start();
		HttpParams params = new HttpParams();
		params.put("username", "Wangmazi");
		params.put("passwd", "654321");
		HttpClientUtils.getInstance().post("http://192.168.11.250/", "mydata/Login.php", params, new AsyncHttpResponseHandler(){
			//操作成功的结果返回回调，无论格式是否是符合JSON的格式，都会回调
			@Override
			public void onSuccess(String result) {
				System.out.println("========"+ result);
			}
			//成功执行，数据返回时一个符合JSONObject格式
			@Override
			public void onSuccess(JSONObject jsonObject) {
				//解析，并刷新界面
				System.out.println("json=" +jsonObject.toString());
			}
			//成功执行，数据返回是一个符合JSONArray对的格式
			@Override
			public void onSuccess(JSONArray jsonArray) {
				//解析，并刷新界面
			}
			/* 网络请求失败    result 结果值
			 * statusCode 执行的状态码 200 执行成功， 404 页面没找到 500服务器内部错误
			 * errorResponse 错误的提示信息
			 */
			@Override
			public void onFailure(String result, int statusCode,
					String errorResponse) {
				//请求失败，提示或重新请求
			}
			//开始上传
			@Override
			public void onStartUpload() {
			}
			//上传进度值改变
			@Override
			public void onUploadProgress(int progress) {
			}
			//上传完成
			@Override
			public void onUploadCompleted() {
			}
		});
		
	}
	Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch(msg.what) {
			case 123:
				String result = (String) msg.obj;
				System.out.println(result);
				//Gson gson = new Gson();
				//WeatherInfo info = gson.fromJson(result, WeatherInfo.class);
				//更新界面
				
				break;
			}
			return true;
		}
	});
	class MyThread extends Thread{
		@Override
		public void run() {
			HttpParams params = new HttpParams();
			params.put("username", "Wangmazi");
			params.put("passwd", "654321");
			//String result = getResult("http://192.168.11.250/", "mydata/Login.php", params);
			String result = postResult("http://192.168.11.250/", "mydata/Login.php", params);
			Message message = new Message();
			message.what = 123;
			message.obj = result; //传递网络请求结果
			handler.sendMessage(message);
		}
	}
	public String getWeather(){
		String result = "";
		String url = "http://192.168.11.250/mydata/Login.php";
		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection(); //打开连接
			connection.setRequestProperty("accept", "*/*"); //设置MIME类型， 这里允许发送类型
			connection.setRequestProperty("connection", "Keep-Alive");//保持连接
			connection.setDoOutput(true);//设置允许输出流
			connection.setDoInput(true);//允许输入流
			connection.connect(); //建立实际连接
			
			//获取到输出流
			PrintWriter writer = new PrintWriter(connection.getOutputStream()); //获取到输出流
			writer.print("username=zhangsan&passwd=123456");
			writer.flush(); //发送出去
			writer.close();
			
			InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "utf-8"); //获取到输入流
			//对流进行处理
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = "";
			while((line = bufferedReader.readLine()) != null){
				result+= line + "\n";
			}
			bufferedReader.close();
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	public String getResult(String url, String path, HttpParams params){
		String result = "";
		//设置连接超时和socket超时
		BasicHttpParams params1 = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params1, 5000);
		HttpConnectionParams.setSoTimeout(params1, 5000);
		HttpClient client = new DefaultHttpClient(params1);//创建一个HttpClient实例
		HttpGet httpGet = new HttpGet(HttpParams.getGetUrl(url, path, params));
		try {
			HttpResponse response = client.execute(httpGet); //执行get请求
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//请求成功
				result = EntityUtils.toString(response.getEntity()); //获取结果
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	public String postResult(String url, String path, HttpParams params){
		String result = "";
		//设置连接超时和socket超时
		BasicHttpParams params1 = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params1, 5000);
		HttpConnectionParams.setSoTimeout(params1, 5000);
		HttpClient client = new DefaultHttpClient(params1);//创建一个HttpClient实例
		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		for(int i=0; i< params.getParamsCount(); i++) {
			BasicNameValuePair pair = new BasicNameValuePair(params.get(i).key, params.get(i).vObject.toString());
			list.add(pair);
		}
		HttpPost httpPost = new HttpPost(url+path);
		try {
			StringEntity entity = new UrlEncodedFormEntity(list); //构造参数字符串
			entity.setContentType("application/x-www-form-urlencoded");//设置web表单格式
			httpPost.setEntity(entity);
			HttpResponse response = client.execute(httpPost); //执行get请求
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//请求成功
				result = EntityUtils.toString(response.getEntity()); //获取结果
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
