package jixiang.com.myandroid.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import jixiang.com.myandroid.R;

public class AActivity extends Activity{
	EditText input;
	TextView result;
	long resultFab;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_activity);
		input = (EditText) findViewById(R.id.input);
		result = (TextView) findViewById(R.id.result);
		findViewById(R.id.send).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				send();
			}
		});
	}
	public void send(){
		String str = input.getText().toString();
		if(str != null && !str.equals("")) {
			Intent intent = new Intent(this, BActivity.class);
			long value = Long.parseLong(str);
			//把输入的n的值，传入到intent里面
			intent.putExtra("value", value);
			//将这个请求发送给B，让他来做计算
			startActivityForResult(intent, 100);
		}
		
	}
	/*
	 * 接收从其他activity返回的值
	 * requestCode 请求码 
	 * resultCode 结果码
	 * data 结果返回数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 100 && resultCode == RESULT_OK) {
			//求斐波拉契数列的值的返回
			if(data != null && data.hasExtra("result")) {
				resultFab = data.getLongExtra("result", 0);
				result.setText(resultFab + "");
			}
		}
	}
}
