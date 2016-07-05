package jixiang.com.myandroid.view;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import jixiang.com.myandroid.R;

public class TestTabActivity extends Activity implements OnTabClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_tab);
		TabIndicator indicator = (TabIndicator) findViewById(R.id.tab);
		indicator.setOnTabClickListener(this);
	}

	@Override
	public void onTabClick(View v, int position) {
		switch(position) {
		case TabIndicator.TAB1:
			Toast.makeText(this, "click1", Toast.LENGTH_SHORT).show();
			break;
		case TabIndicator.TAB2:
			Toast.makeText(this, "click2", Toast.LENGTH_SHORT).show();
			break;
		case TabIndicator.TAB3:
			Toast.makeText(this, "click3", Toast.LENGTH_SHORT).show();
			break;
		case TabIndicator.TAB4:
			Toast.makeText(this, "click4", Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
