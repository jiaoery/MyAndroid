package jixiang.com.myandroid.webview;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import jixiang.com.myandroid.BaseActivity;
import jixiang.com.myandroid.R;
import jixiang.com.myandroid.adapter.MainAdapter;


public class TestWebView extends BaseActivity {
	String[] items = { "Load Html", "Load Local JS & jsinterface", "GET img url", "Login CSDN"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout linearLayout = new LinearLayout(this);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		linearLayout.setLayoutParams(layoutParams);

		ListView listView = new ListView(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		listView.setLayoutParams(params);
		listView.setDivider(new ColorDrawable(getResources().getColor(
				R.color.divider_color)));
		listView.setDividerHeight(1);
		listView.setSelector(R.drawable.list_item_selector);
		listView.setCacheColorHint(getResources().getColor(R.color.transparent));

		linearLayout.addView(listView);
		setContentView(linearLayout);


		MainAdapter adapter = new MainAdapter(items, this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				jump(position);
			}
		});
	}

	public void jump(int index) {
		Intent intent = null;
		switch (index) {
		case 0:
			intent = new Intent(this, LoadLocalHtml.class);
			break;
		case 1:
			intent = new Intent(this, LoadJSAndJSInterface.class);
			break;
		case 2:
			intent = new Intent(this, GetImageUrl.class);
			break;
		case 3:
			intent = new Intent(this, LoginCSDN.class);
			break;
		}
		if(intent != null) {
			startActivity(intent);
		}
	}
}
