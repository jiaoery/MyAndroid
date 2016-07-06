package jixiang.com.myandroid.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.adapter.MainAdapter;

public class TestFragment extends Activity{

	String[] items = {"Fragment As View", "FragmentLayout", "Fragment Manager"
			};

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
		switch(index) {
		case 0:
			Intent intent1 = new Intent(this, TestFragmentAsView.class);
			startActivity(intent1);
			break;
		case 1:
			Intent intent = new Intent(this, FragmentLayout.class);
			startActivity(intent);
			break;
		case 2:
			Intent intent2 = new Intent(this, TestFragmentManager.class);
			startActivity(intent2);
			break ;
		}
	}
}
