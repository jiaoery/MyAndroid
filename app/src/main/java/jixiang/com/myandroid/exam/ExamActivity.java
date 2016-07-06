package jixiang.com.myandroid.exam;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import jixiang.com.myandroid.R;

public class ExamActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_exam);
		findViewById(R.id.question1).setOnClickListener(this);
		findViewById(R.id.question2).setOnClickListener(this);
		findViewById(R.id.question3).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch(v.getId()){
		case R.id.question1:
			intent = new Intent(this, ShowTimeActivity.class);
			break;
		case R.id.question2:
			intent = new Intent(this, ShowListViewActivity.class);
			break;
		case R.id.question3:
			intent = new Intent(this, ShowGridActivity.class);
			break;
		}
		if(intent != null) {
			startActivity(intent);
		}
	}
	
	
}
