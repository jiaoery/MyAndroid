package jixiang.com.myandroid.databaseexam;


import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import jixiang.com.myandroid.R;

public class QQDinaryView extends FragmentActivity{
	DinaryBean bean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qq_dinary_view);
		if(getIntent() != null && getIntent().hasExtra("bean")) {
			bean = (DinaryBean) getIntent().getSerializableExtra("bean");
		}
		TextView title = (TextView) findViewById(R.id.dianry_title);
		TextView content = (TextView) findViewById(R.id.dinary_content);
		TextView publishTime = (TextView) findViewById(R.id.publish_time);

		//设置数据
		if(bean != null) {
			title.setText(bean.title);
			content.setText(bean.content);
			publishTime.setText("发表时间  " + bean.publish_time);
		}
		findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
