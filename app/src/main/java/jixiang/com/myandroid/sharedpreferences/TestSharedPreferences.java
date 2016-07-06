package jixiang.com.myandroid.sharedpreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import jixiang.com.myandroid.R;

public class TestSharedPreferences extends FragmentActivity implements OnClickListener{
	EditText editText;
	SharedPreferences preferences;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().getDecorView().findViewById(android.R.id.content);
		setContentView(R.layout.test_shared_prefs);
		editText = (EditText) findViewById(R.id.input);
		preferences = this.getSharedPreferences("lock", MODE_WORLD_WRITEABLE);
		findViewById(R.id.set).setOnClickListener(this);
		findViewById(R.id.get).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.set:
			String password = editText.getText().toString().trim();
			if(password != null && !password.equals("")) {
				Editor editor = preferences.edit();
				editor.putString("password", password);
				editor.commit();  //提交
			}
			break;
		case R.id.get:
			String pwd= preferences.getString("password", ""); 
			Toast.makeText(this, "password=" + pwd, Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
