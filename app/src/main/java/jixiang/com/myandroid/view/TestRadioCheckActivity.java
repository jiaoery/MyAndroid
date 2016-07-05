package jixiang.com.myandroid.view;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import jixiang.com.myandroid.R;

public class TestRadioCheckActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_radio_check);
		final RadioButton radioButton = (RadioButton) findViewById(R.id.radiobutton);
		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			}
		});
		findViewById(R.id.test).setOnClickListener(new  OnClickListener() {
			@Override
			public void onClick(View v) {
				radioButton.setChecked(false);
				//radioGroup.clearCheck();  === radioGroup.check(-1);
				radioGroup.check(-1);
			}
		});
		/**
		 * 单选按钮组的点击事件
		 */
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			/**
			 * RadioGroup
			 * checkId RadioGroup 里面的RadioButton xml里面定义的ID值
			 */
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
				if(radioButton != null)
				Toast.makeText(TestRadioCheckActivity.this, "You're a " + radioButton.getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
