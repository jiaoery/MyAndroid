package jixiang.com.myandroid.animator;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.adapter.MainAdapter;


public class TestAnimatorActivity extends FragmentActivity {

	String[] items = { "使用xml创建属性动画", "ObjectAnimator的方式创建属性动画",
			"AnimatorSet方式创建动画", "View的Anim方法", "Layout Anim",
			"ValueAnimator创建动画" };

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
			intent = new Intent(this, XmlAnimatorActivity.class);
			break;
		case 1:
			intent = new Intent(this, TestObjectAnimatorAcitivity.class);
			break;
		case 2:
			intent = new Intent(this, TestAnimatorSet.class);
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		}
		if(intent != null) {
			startActivity(intent);
		}
	}
}
