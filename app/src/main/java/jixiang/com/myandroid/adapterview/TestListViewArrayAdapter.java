package jixiang.com.myandroid.adapterview;

import java.util.ArrayList;
import java.util.Collections;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import jixiang.com.myandroid.R;

public class TestListViewArrayAdapter extends Activity {
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_listview_arrayadapter);
		listView = (ListView) findViewById(R.id.listview2);

		/**
		 * 将main_titles转换成字符串数组
		 */
		String[] dataSource = this.getResources()
				.getStringArray(R.array.main_titles);
		
		
		ArrayList<String> list = new ArrayList<String>(dataSource.length);
		
		/*for(int i=0; i< dataSource.length; i++) {
			list.add(dataSource[i]);
		}*/
		
		//将字符串数组转换成字符串列表
		Collections.addAll(list, dataSource);

		// 生成一个ArrayAdapter 并使用数据源
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.only_textview);
		//设置数据源
		adapter.addAll(list);

		/**
		 * this context 上下文
		 * R.layout.simple_item_2 每一项数据需要使用到的布局文件
		 * textViewResourceId R.id.title 这个字符串要显示到哪个控件上，这个控件必须是TextView
		 * list 数据源
		 */
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				R.layout.simple_item_2, R.id.title, list);
		//设置适配器
		listView.setAdapter(adapter);
	}
}
