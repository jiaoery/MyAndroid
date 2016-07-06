package jixiang.com.myandroid.animation;

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

public class TestAnimation extends FragmentActivity{

	String[] items = {"Translate", "Rotate", "Scale", "alpha",  "AnimationSet","FrameAnimation"
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
			Intent intent = new Intent(this, TestTranslate.class);
			startActivity(intent);
			break;
		case 1:
			Intent intent1 = new Intent(this, TestRotate.class);
			startActivity(intent1);
			break;
		case 2:
			Intent intent2 = new Intent(this, TestScaleAnimation.class);
			startActivity(intent2);
			break ;
		case 3:
			Intent intent3 = new Intent(this, TestAlphaAnimation.class);
			startActivity(intent3);
			break;
		case 4:
			Intent intent4 = new Intent(this, TestAnimationSet.class);
			startActivity(intent4);
			break;
		case 5:
			Intent intent5 = new Intent(this, TestFrameAnimation.class);
			startActivity(intent5);
			break;
		}
	}
}
