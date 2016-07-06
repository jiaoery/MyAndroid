package jixiang.com.myandroid.adapterview;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import jixiang.com.myandroid.R;

public class TestListViewSimpleAdapter extends Activity {

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_listview_simpleadapter);
		listView = (ListView) findViewById(R.id.listview3);

		String[] keys = { "friend_icon", "friend_name", "friend_sign" };

		// 构建我们的数据源
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 30; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(keys[0], R.drawable.a006);
			map.put(keys[2], "树不要皮，必死无疑，人不要脸，天下无敌!");
			map.put(keys[1], "马永贞");
			list.add(map);
		}

		/**
		 *  构建SimpleAdapter
		 *  this 上下文
		 *  list 数据源
		 *  resourceId(R.layout.qq_friend_item) 每一项的布局文件
		 *  from(keys) 源地 
		 *  to 目的地
		 *  from to 意思是从map里面获取到key的值，然后设置到相对应的布局文件的相对应的Id的控件上面
		 */
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.qq_friend_item, keys, 
				new int[] { R.id.header_icon,R.id.name,R.id.sign});
		
		listView.setAdapter(adapter);
	}
}
