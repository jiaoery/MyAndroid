package jixiang.com.myandroid.intent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.adapter.MainAdapter;


public class TestIntentAcitvity extends Activity {
	String[] items = { "ComponentName", "Action", "Category", "Data", "Type",
			"Extra", "Flag" };

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
		switch (index) {
		case 0:
			// 显示启动
			ComponentName componentName = new ComponentName(
					"com.three.androidlearning",
					"com.three.androidlearning.view.TestProgressBarActivity");
			Intent intent = new Intent();
			// intent = new Intent(this, TestProgressBarActivity.class);
			intent.setComponent(componentName);
			startActivity(intent);
			break;
		case 1:
			// 隐式启动
			Intent intent2 = new Intent();
			intent2.setAction("com.three.android.see");
			startActivity(intent2);
			break;
		case 2:
			Intent intent3 = new Intent();
			intent3.setAction("com.three.android.see");
			intent3.addCategory("com.three.android.mycategory");
			startActivity(intent3);
			break;
		case 3:
			// 打电话
			Intent intent4 = new Intent();
			intent4.setAction(Intent.ACTION_CALL);
			intent4.setData(Uri.parse("tel:18523910579"));
			startActivity(intent4);
			break;
		case 4:
			// type类型
			Intent intent5 = new Intent();
			intent5.setAction(Intent.ACTION_VIEW);
			//intent5.setData(Uri.parse("file:///sdcard/file.png")); // 这个方法不能用
			//intent5.setType("image/png");
			/***
			 * public Intent setData(Uri data) {
			 *      mData = data; 
			 *      mType = null;
			 *      return this; 
			 * }
			 * public Intent setType(String type) {
             *    mData = null;
             *    mType = type;
             *    return this;
             * }
			 * 如果要同时设置data 和type 就不能使用上面的两个单独的方法
			 * 而应该使用setDataAndType 方法
			 */
			intent5.setDataAndType(Uri.parse("file:///sdcard/hai.mp3"),
					"audio/x-mpeg");
			intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent5);
			break;
		case 5:
			//extra 传递数据的
			//数据使用Bundle 封装的
			Intent intent6 = new Intent();
			intent6.setAction("com.three.android.see");
			intent6.addCategory("com.three.android.person");
			Bundle bundle = new Bundle();
			bundle.putString("name", "zhangsan");
			bundle.putInt("age", 20);
			intent6.putExtras(bundle);
			startActivity(intent6);
			break;
		case 6:
			Intent intent7 = new Intent();
			intent7.setAction("com.hanchang.fileexplorer");
			intent7.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent7);
			break;
		}
	}
}
