package jixiang.com.myandroid.adapterview;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import jixiang.com.myandroid.R;


public class ExpandableListViewAdapter extends BaseExpandableListAdapter{
	List<QQFriend> list;
	LayoutInflater inflater;
	Context mContext;
	
	public ExpandableListViewAdapter(Context context, List<QQFriend> list) {
		mContext = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	
	/**
	 * 返回分组的数量
	 */
	@Override
	public int getGroupCount() {
		if(list != null) {
			return list.size();
		}
		return 0;
	}
	/**
	 * 返回指定groupPosition位置分组下面的child的数量
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		if(list != null && list.get(groupPosition).QQList != null) {
			return list.get(groupPosition).QQList.size();
		}
		return 0;
	}
	/**
	 * 返回指定groupPosition位置分组的组数据项
	 */
	@Override
	public Object getGroup(int groupPosition) {
		if(list != null) {
			return list.get(groupPosition).groupBean;
		}
		return null;
	}
	/**
	 * 返回指定groupPosition分组下面的指定的child位置项的数据
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		if(list != null && list.get(groupPosition).QQList != null) {
			return list.get(groupPosition).QQList.get(childPosition);
		}
		return null;
	}
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	@Override
	public boolean hasStableIds() {
		return true;
	}

	/**
	 * 生成group项的view，并绑定数据
	 */
	@Override
	public View getGroupView(int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
		final GroupHolder groupHolder;
		if(convertView == null) {
			groupHolder = new GroupHolder();
			convertView = inflater.inflate(R.layout.group_item, parent, false);
			groupHolder.groupName = (TextView) convertView.findViewById(R.id.group_name);
			groupHolder.online = (TextView) convertView.findViewById(R.id.online);
			groupHolder.count = (TextView) convertView.findViewById(R.id.count);
			groupHolder.indicator = (ImageView) convertView.findViewById(R.id.indicator);
			convertView.setTag(groupHolder);
		} else {
			groupHolder = (GroupHolder) convertView.getTag();
		}
		if(groupPosition < list.size()) {
			//if(isExpanded) { //如果此分组项是展开的
			//	groupHolder.indicator.setBackgroundResource(R.drawable.indicator_expand);
			//} else {
			//	groupHolder.indicator.setBackgroundResource(R.drawable.indicator_normal);
			//}
			//设置name
			groupHolder.groupName.setText(list.get(groupPosition).groupBean.groupName);
			int count = 0;
			//设置在线的数目
			for(int i=0; i< list.get(groupPosition).QQList.size(); i++) {
				if(list.get(groupPosition).QQList.get(i).isOnLine) {
					count++;
				}
			}
			groupHolder.online.setText(count + "");
			groupHolder.count.setText(list.get(groupPosition).QQList.size() + "");
		}
		
		convertView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(isExpanded) { //如果是展开的,则收缩
						Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.indicator_collapse_animation);
						groupHolder.indicator.startAnimation(animation);
					} else {
						Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.indicator_expand_animation);
						groupHolder.indicator.startAnimation(animation);
					}
					break;
				}
				//返回false,会再触发 收缩展开事件
				return false;
			}
		});
		return convertView;
	}
	/**
	 * 生成child项的view 并绑定数据
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildHolder childHolder;
		if(convertView == null) {
			childHolder = new ChildHolder();
			convertView = inflater.inflate(R.layout.child_item, parent, false);
			childHolder.headerIcon = (ImageView) convertView.findViewById(R.id.header_icon);
			childHolder.name = (TextView) convertView.findViewById(R.id.name);
			childHolder.sign = (TextView) convertView.findViewById(R.id.sign);
			convertView.setTag(childHolder);
		} else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		if(list != null && list.get(groupPosition).QQList != null) {
			childHolder.headerIcon.setBackgroundResource(list.get(groupPosition).QQList.get(childPosition).header_icon);
			childHolder.name.setText(list.get(groupPosition).QQList.get(childPosition).name);
			childHolder.sign.setText(list.get(groupPosition).QQList.get(childPosition).sign);
		}
		return convertView;
	}
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	class GroupHolder{
		ImageView indicator;
		TextView groupName;
		TextView online;
		TextView count;
	}	
	class ChildHolder{
		ImageView headerIcon;
		TextView name;
		TextView sign;
	}
}
