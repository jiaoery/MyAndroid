package jixiang.com.myandroid.view;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import jixiang.com.myandroid.R;

public class TestTextViewActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_textview);
		findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}
}
