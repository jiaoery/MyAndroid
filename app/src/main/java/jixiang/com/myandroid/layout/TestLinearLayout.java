package jixiang.com.myandroid.layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


import jixiang.com.myandroid.BaseActivity;
import jixiang.com.myandroid.R;

public class TestLinearLayout extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_linearlayout);
		findViewById(R.id.number_equ).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaseActivity.EXIT_ACTION);
				sendBroadcast(intent);
			}
		});
	}
	
	@Override
	public void onNetWorkStatusChanged1(boolean isAviable, int type) {
		System.out.println("TestLinearLayout isAviable="  + isAviable  + ", type"  + type);
	}

}
