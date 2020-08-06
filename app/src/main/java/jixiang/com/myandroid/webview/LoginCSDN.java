package jixiang.com.myandroid.webview;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jixiang.com.myandroid.R;


public class LoginCSDN extends FragmentActivity{

	WebView webView;
	String url = "http://passport.csdn.net/account/login?from=http://my.csdn.net/my/mycsdn";
	String userName= "08254976@163.com";
	String passwd = "TIANyu860825";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_webview);
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true); //运行执行javascript代码
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.setWebViewClient(new MyWebViewClient()); //在当前页面响应
		//webView.addJavascriptInterface(new MyWebAppInterface(this), "demo");
		webView.loadUrl(url);
	}

	class MyWebViewClient extends WebViewClient{
		
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			String wholeJS = getData("html/login_csdn.js");
			System.out.println("onPageFinished=====================");
			//通过返回的webview加载webview默认脚本 在脚本中动态注入js 
		    /*view.loadUrl(  
                    "javascript:"  
                    + "  var iName = document.getElementById(\"username\"); "  
                    + "  var iPasswd = document.getElementById(\"password\");"  
                    + "  iName.value = \"08254976@163.com\"; "  
                    + "  iPasswd.value = \"TIANyu860825\"; "  
                    );*/
			
			/*view.loadUrl(  
                    "javascript:(function(){"  
                    + "  var iName = document.getElementById(\"username\"); "  
                    + "  var iPasswd = document.getElementById(\"password\");"  
                    + "  iName.value = \"08254976@163.com\"; "  
                    + "  iPasswd.value = \"TIANyu860825\"; "  
                    + "})()");*/
			
			view.loadUrl(  
                    "javascript:(function blank(userName, passWd){"  
                    + "  var iName = document.getElementById(\"username\"); "  
                    + "  var iPasswd = document.getElementById(\"password\");"  
                    + "  iName.value = userName; "  
                    + "  iPasswd.value = passWd; "  
                    + "})('" +userName + "', '" + passwd +"')");
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
		if(address.equals(url)){
			//要退出
			super.onBackPressed();
		}
		webView.goBack(); //网页回退，而不是活动回退
	}
}