package jixiang.com.myandroid.intent;

import android.app.Activity;
import android.os.Bundle;

public class ActionActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getIntent() != null && getIntent().hasExtra("name")) {
			System.out.println("name=" + getIntent().getStringExtra("name"));
			System.out.println("age=" + getIntent().getIntExtra("age", 0));
		}
		
	}
}
