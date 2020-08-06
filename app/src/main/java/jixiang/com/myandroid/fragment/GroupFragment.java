package jixiang.com.myandroid.fragment;

import java.util.ArrayList;
import java.util.Collections;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import jixiang.com.myandroid.R;

public class GroupFragment extends Fragment{
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		System.out.println("GroupFragment============================");
		View view = inflater.inflate(R.layout.test_listview_arrayadapter, null);
		ListView listView = (ListView) view.findViewById(R.id.listview2);

		/**
		 * 将main_titles转换成字符串数组
		 */
		String[] dataSource = this.getResources()
				.getStringArray(R.array.main_titles);
		ArrayList<String> list = new ArrayList<String>(dataSource.length);
		//将字符串数组转换成字符串列表
		Collections.addAll(list, dataSource);
		// 生成一个ArrayAdapter 并使用数据源
		//TestFragmentManager.this = getActivity();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.only_textview);
		//设置数据源
		adapter.addAll(list);
		/**
		 * this context 上下文
		 * R.layout.simple_item_2 每一项数据需要使用到的布局文件
		 * textViewResourceId R.id.title 这个字符串要显示到哪个控件上，这个控件必须是TextView
		 * list 数据源
		 */
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
				R.layout.simple_item_2, R.id.title, list);
		//设置适配器
		listView.setAdapter(adapter);
		return view;
	}
}
