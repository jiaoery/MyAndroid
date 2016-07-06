package jixiang.com.myandroid.webview;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import jixiang.com.myandroid.dialog.CustomAlertDialog;


public class MyWebAppInterface {

	Context mContext;
	
	public MyWebAppInterface(Context mContext) {
		this.mContext = mContext;
	}
	
	@JavascriptInterface
	public void showToast(String msg){
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}
	
	@JavascriptInterface
	public void showDialog(String msg){
		CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(mContext);
		builder.setTitle("你的用户名");
		builder.setMessage(msg);
		builder.setPositiveButton("确定", null);
		builder.create().show();
	}
	@JavascriptInterface
	public void jsInvokeJava(String img) { 
        Log.i("Image", "被点击的图片地址为：" + img); //此时拿到了图片的绝对地址 下载下来进行放大预览等操作都可以；
	}
}
