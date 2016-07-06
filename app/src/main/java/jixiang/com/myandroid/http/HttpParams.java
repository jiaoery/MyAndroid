package jixiang.com.myandroid.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class HttpParams {
	
	private ArrayList<Param> params;
	//private HashMap<String, Object> hashMap;
	
	public HttpParams() {
		params = new ArrayList<Param>();
	}
	
	public void put(String key, boolean value) {
		if(params != null && key != null) {
			Param param = new Param();
			param.key = key;
			param.vObject= value;
			params.add(param);
		}
	}
	
	public void put(String key, byte value) {
		if(params != null && key != null) {
			Param param = new Param();
			param.key = key;
			param.vObject= value;
			params.add(param);
		}
	}
	
	public void put(String key, char value) {
		if(params != null && key != null) {
			Param param = new Param();
			param.key = key;
			param.vObject= value;
			params.add(param);
		}
	}
	
	public void put(String key, int value){
		if(params != null && key != null) {
			Param param = new Param();
			param.key = key;
			param.vObject= value;
			params.add(param);
		}
	}
	
	public void put(String key, long value) {
		if(params != null && key != null) {
			Param param = new Param();
			param.key = key;
			param.vObject= value;
			params.add(param);
		}
	}
	
	public void put(String key, float value) {
		if(params != null && key != null) {
			Param param = new Param();
			param.key = key;
			param.vObject= value;
			params.add(param);
		}
	}
	
	public void put(String key, double value) {
		if(params != null && key != null) {
			Param param = new Param();
			param.key = key;
			param.vObject= value;
			params.add(param);
		}
	}
	
	public void put(String key, String value) {
		if(params != null && key != null) {
			Param param = new Param();
			param.key = key;
			param.vObject= value;
			params.add(param);
		}
	}
	
	public int getParamsCount() {
		return params.size();
	}
	
	public Param get(int index) {
		return params.get(index);
	}
	
	/**
	 * 拼接GET方法的URL地址
	 * @param serverAddress 请求的server地址前缀
	 * @param url 请求的路径地址
	 * @param params 请求的参数列表
	 * @return 拼接后的完整的URL地址
	 */
	public static String getGetUrl(String serverAddress, String url, HttpParams params) {
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
}
