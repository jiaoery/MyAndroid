package jixiang.com.myandroid.adapterview;

import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import jixiang.com.myandroid.R;

public class TestCarActivity extends Activity{
	
	ListView listView;

	ArrayList<CarBean> list = new ArrayList<CarBean>(20);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_listview);
		listView = (ListView) findViewById(R.id.listview1);
		
		for(int i=1; i< 21; i++) {
			CarBean  bean = new CarBean();
			bean.rest = i*100;
			bean.max = i* 200 + bean.rest* i;
			bean.title = "得意世界停车场";
			bean.address = "地址：较场口88号负2楼，共有两个进出口，分别为702和429车站的斜对面";
			bean.distance = 1345;
			list.add(bean);
		}
		
		CarAdapter adapter = new CarAdapter(this, list);
		listView.setAdapter(adapter);
	}
}
