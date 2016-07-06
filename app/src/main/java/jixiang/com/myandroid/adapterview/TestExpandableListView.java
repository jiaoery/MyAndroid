package jixiang.com.myandroid.adapterview;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import jixiang.com.myandroid.R;

public class TestExpandableListView extends Activity{
	
	List<QQFriend> list = new ArrayList<QQFriend>();
	ExpandableListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_expandablelistview);
		listView = (ExpandableListView) findViewById(R.id.expandable);
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
		bean2 = new QQFriendBean(R.drawable.a008, "樊大人", "他妈的", true);
		qqList.add(bean2);
		bean2 = new QQFriendBean(R.drawable.a009, "陈培", "好久没回到大学城，物是人非事事休", true);
		qqList.add(bean2);
		bean2 = new QQFriendBean(R.drawable.a010, "苍井空", "如果你不能在床上赚钱，就别躺在床上", false);
		qqList.add(bean2);		
		friend.QQList = qqList;
		list.add(friend);
		final ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(this, list);
		listView.setAdapter(adapter);
		
		//group项的收缩事件
		listView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			
			@Override
			public void onGroupCollapse(int groupPosition) {
				/*View view = listView.getChildAt(groupPosition);
				if(view != null) {
				    //此种方式去获取当前点击的group项的view可能会出问题
				    //无论是group还是child实际上都是 ExpandleListView的子控件
				    //所以根据groupPosition来获取group项的view是不可取的
					ImageView imageView = (ImageView) view.findViewById(R.id.indicator);
					Animation animation = AnimationUtils.loadAnimation(TestExpandableListView.this, R.anim.indicator_collapse_animation);
					imageView.startAnimation(animation); //此处可能会报出空指针异常
				}*/
			}
		});
		//group项的展开事件
		listView.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				/*View view = listView.getChildAt(groupPosition);
				if(view != null) {
					ImageView imageView = (ImageView) view.findViewById(R.id.indicator);
					Animation animation = AnimationUtils.loadAnimation(TestExpandableListView.this, R.anim.indicator_expand_animation);
					imageView.startAnimation(animation);
				}*/
			}
		});
		/**
		 * group项的点击事件
		 */
		listView.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				if(listView.isGroupExpanded(groupPosition)) {
					listView.collapseGroup(groupPosition);
				} else {
					listView.expandGroup(groupPosition, true);
				}
				//返回true， 默认是不会在触发展开和收缩事件,但是你可以手动的在此方法里面展开或收缩group项
				//默认返回值是false
				return true;
			}
		});
		/**
		 * 子项的点击事件
		 */
		listView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				//
				return false;
			}
		});
	}
}
