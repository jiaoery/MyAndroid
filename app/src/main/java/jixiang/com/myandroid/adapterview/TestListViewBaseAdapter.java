package jixiang.com.myandroid.adapterview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.view.TestImageViewActivity;


public class TestListViewBaseAdapter extends Activity {

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_listview_baseadapter);
		listView = (ListView) findViewById(R.id.listview3);
		registerForContextMenu(listView);
		// 创建数据源
		final List<QQFriendBean> list = new ArrayList<QQFriendBean>();
		QQFriendBean bean = new QQFriendBean();
		// for (int i = 0; i < 100; i++) {

		bean.header_icon = R.drawable.a005;
		bean.name = "刘德华";
		bean.sign = "谁把我儿子抢了？";
		list.add(bean);
		bean = new QQFriendBean();
		bean.header_icon = R.drawable.a006;
		bean.name = "张韶涵";
		bean.sign = "信春哥得永生？";
		list.add(bean);
		bean = new QQFriendBean();
		bean.header_icon = R.drawable.a007;
		bean.name = "张学友";
		bean.sign = "菊花朵朵开!";
		list.add(bean);
		bean = new QQFriendBean();
		bean.header_icon = R.drawable.a008;
		bean.name = "张无忌";
		bean.sign = "倚天屠龙记？";
		list.add(bean);
		bean = new QQFriendBean();
		bean.header_icon = R.drawable.a009;
		bean.name = "斯瓦辛格";
		bean.sign = "Suck it？";
		list.add(bean);
		// }

		// 初始化适配器
		QQFriendAdapter adapter = new QQFriendAdapter(this, list);
		listView.setAdapter(adapter);

		// listView的每一项的点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {

			/**
			 * parent集合类控件，这里就是ListView view 当前点击的项的视图 position 位置 id 行ID
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(TestListViewBaseAdapter.this,
						"你点击的是：" + list.get(position).name, Toast.LENGTH_SHORT)
						.show();
				((QQFriendAdapter) parent.getAdapter()).setSelection(position);
				Intent intent = new Intent(TestListViewBaseAdapter.this,
						TestImageViewActivity.class);
				startActivity(intent);
			}

		});

		/*
		 * //listView的每一项的长按事件 listView.setOnItemLongClickListener(new
		 * OnItemLongClickListener() {
		 *//**
		 * parent集合类控件，这里就是ListView view 当前点击的项的视图 position 位置 id 行ID
		 */
		/*
		 * @Override public boolean onItemLongClick(AdapterView<?> parent, View
		 * view, int position, long id) {
		 * Toast.makeText(TestListViewBaseAdapter.this, "你长按的是：" +
		 * list.get(position).name, Toast.LENGTH_SHORT).show(); //fasle 还会触发点击事件
		 * //true 就不会触发点击事件 return false; } });
		 */

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 1, 0, "修改");
		menu.add(0, 2, 0, "删除");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo menuInfo = 
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo(); 

		int position = menuInfo.position;   //这个就是当前的index
		switch(item.getItemId()) {
		case 1:
			System.out.println("position=" + position + ", 修改");
			break;
		case 2:
			System.out.println("position=" + position + ", 删除");
			break;
		}
		return super.onContextItemSelected(item);
	}
}
