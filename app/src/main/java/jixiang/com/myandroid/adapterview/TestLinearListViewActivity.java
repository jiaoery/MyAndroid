package jixiang.com.myandroid.adapterview;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import jixiang.com.myandroid.R;

public class TestLinearListViewActivity extends Activity {

	HorizontalListView horizontalListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_linearlist);
		horizontalListView = (HorizontalListView) findViewById(R.id.linearlistview);

		final List<QQFriendBean> list = new ArrayList<QQFriendBean>();
		QQFriendBean bean = new QQFriendBean();

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

		LinearListAdapter adapter = new LinearListAdapter(this, list);
		horizontalListView.setAdapter(adapter);
		
		horizontalListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				System.out.println("click position=" + position);
			}
		});
	}
}
