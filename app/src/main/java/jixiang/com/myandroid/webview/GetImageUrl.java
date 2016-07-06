package jixiang.com.myandroid.webview;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jixiang.com.myandroid.R;

public class GetImageUrl extends FragmentActivity{
	WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_webview);
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true); //运行执行javascript代码
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.setWebViewClient(new MyWebViewClient()); //在当前页面响应
		webView.addJavascriptInterface(new MyWebAppInterface(this), "demo");
		webView.loadUrl("http://www.12365auto.com/news/2014-07-10/20140710115457.shtml");
	}

	class MyWebViewClient extends WebViewClient{
		
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			//通过返回的webview加载webview默认脚本 在脚本中动态注入js 
		    view.loadUrl(  
		                    "javascript:(function(){"  
		                    + "  var objs = document.getElementsByTagName(\"img\"); "  
		                    + "  for(var i=0;i<objs.length;i++){"  
		                    + "     objs[i].onclick=function(){"  
		                    + "          window.demo.jsInvokeJava(this.src); "  
		                    + "     }"
		                    + "  }"  
		                    + "})");  
		         }

	}
}
