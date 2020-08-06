package jixiang.com.myandroid.webview;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jixiang.com.myandroid.R;


public class LoadJSAndJSInterface extends FragmentActivity implements OnClickListener{
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_webview_js);
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true); //运行执行javascript代码
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.setWebViewClient(new WebViewClient()); //在当前页面响应
		webView.addJavascriptInterface(new MyWebAppInterface(this), "demo");
		webView.loadUrl("file:///android_asset/html/login.html");
		
		findViewById(R.id.insert).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		webView.loadUrl("javascript:insert('Hello,World!')"); //点击button调用html的js脚本 
	}
}
