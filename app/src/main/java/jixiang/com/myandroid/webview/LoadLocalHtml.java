package jixiang.com.myandroid.webview;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jixiang.com.myandroid.BaseActivity;
import jixiang.com.myandroid.R;


public class LoadLocalHtml extends BaseActivity {
	WebView webView;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_webview);
		webView = (WebView) findViewById(R.id.webview);
		//webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webView.getSettings().setJavaScriptEnabled(true); //运行执行javascript代码
		webView.setWebViewClient(new MyWebClient()); //在当前页面响应
		//webView.loadUrl("file:///android_asset/html/index.html");
		webView.loadUrl("http://192.168.11.250/myweb/html/index.html");
		//webView.loadData(data, mimeType, encoding);
		//webView.loadData(getData("html/index.html"), "text/html", "utf-8");
		
		//webViewClient.onReceivedHttpAuthRequest(view, handler, host, realm);
	}
	
	class MyWebClient extends WebViewClient {
		//接收到授权请求
		@Override
		public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
			super.onReceivedHttpAuthRequest(view, handler, host, realm);
			System.out.println("onReceivedHttpAuthRequest");
		}
		
		//加载资源
		@Override
		public void onLoadResource(WebView view, String url) {
			super.onLoadResource(view, url);
			System.out.println("onLoadResource url=" + url);
		}
		//开始加载
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			System.out.println("onPageStarted url=" + url);
		}
		//结束加载
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			System.out.println("onPageFinished url=" + url);
		}
		//是否重定向
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			System.out.println("shouldOverrideUrlLoading url=" + url);
			//return super.shouldOverrideUrlLoading(view, url);
			if(url.contains("http://192.168.11.250/myweb/html/Login.php")){
				view.loadUrl("http://www.163.com");
				return true;
			} else {
				return false;
			}
		}
		//是否拦截
		@Override
		public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
			System.out.println("shouldInterceptRequest url=" + url);
			if(url.contains("http://192.168.11.250/myweb/html/Login.php")){
				webView.stopLoading();
				WebResourceResponse response = new WebResourceResponse("text/html", "utf-8", null);
				return response;
			} else {
				return super.shouldInterceptRequest(view, url);
			}
		}
		
	}
	
	public String getData(String path){
		AssetManager assetManager = getAssets();
		StringBuffer stringBuffer = new StringBuffer();
		try {
			InputStream inputStream = assetManager.open(path);
			byte[] buffer = new byte[1024];
			int len;
			while((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				stringBuffer.append(new String(buffer,0,len));
			}
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(stringBuffer.toString());
		return stringBuffer.toString();
	}
	
	@Override
	public void onBackPressed() {
		String address = webView.getUrl();
		System.out.println("address=" + address);
		if(address.equals("http://192.168.11.250/myweb/html/index.html")){
			//要退出
			super.onBackPressed();
		}
		webView.goBack(); //网页回退，而不是活动回退
	}
}
