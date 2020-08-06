package jixiang.com.myandroid.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.adapterview.ExpandableListViewAdapter;
import jixiang.com.myandroid.adapterview.QQFriend;
import jixiang.com.myandroid.adapterview.QQFriendBean;
import jixiang.com.myandroid.adapterview.QQFriendGroupBean;


public class FragmentAsView extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		System.out.println("FragmentAsView=================");
		List<QQFriend> list = new ArrayList<QQFriend>();
		final ExpandableListView listView;
		View view = inflater.inflate(R.layout.test_expandablelistview, null);
		listView = (ExpandableListView) view.findViewById(R.id.expandable);
		//组装数据源
		QQFriend friend = new QQFriend();
		QQFriendGroupBean bean = new QQFriendGroupBean("my friend");
		friend.groupBean = bean;
		//添加组下面的成员
		List<QQFriendBean> qqList = new ArrayList<QQFriendBean>();
		QQFriendBean bean2 = new QQFriendBean(R.drawable.a005, "翘鱼团队", "不法分子", true);
		qqList.add(bean2);
		bean2 = new QQFriendBean(R.drawable.a006, "秦月", "你没想到，我也没想到", true);
		qqList.add(bean2);
		bean2 = new QQFriendBean(R.drawable.a007, "李金花", "离线请留言", false);
		qqList.add(bean2);		
		friend.QQList = qqList;
		list.add(friend);
		friend = new QQFriend();
		bean = new QQFriendGroupBean("hc");
		friend.groupBean = bean;
		//添加组下面的成员
		qqList = new ArrayList<QQFriendBean>();
		bean2 = new QQFriendBean(R.drawable.a008, "樊大人", "天气炎热", true);
		qqList.add(bean2);
		bean2 = new QQFriendBean(R.drawable.a009, "陈培", "好久没回到大学城，物是人非事事休", true);
		qqList.add(bean2);
		bean2 = new QQFriendBean(R.drawable.a010, "苍井空", "如果你不能在床上赚钱，就别躺在床上", false);
		qqList.add(bean2);		
		friend.QQList = qqList;
		list.add(friend);
		ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		
		return view;
	}
}
