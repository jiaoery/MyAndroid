package jixiang.com.myandroid.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import jixiang.com.myandroid.R;

public class QQBottomFragment extends Fragment implements OnClickListener{
	public static interface OnQQBottomClickListener{
		void onBottomClick(View v, int position);
	}
	public static final int CHAT_POSITION = 0;
	public static final int FRIEND_POSITION = 1;
	public static final int GROUP_POSITION = 2;
	public static final int DYNAMIC_POSITION = 3;
	private OnQQBottomClickListener bottomClickListener;
	TextView chatView;
	TextView friendView;
	TextView groupView;
	TextView dynamicView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.qq_bottom, null);
		chatView = (TextView) view.findViewById(R.id.chat);
		friendView = (TextView) view.findViewById(R.id.friends);
		groupView = (TextView) view.findViewById(R.id.group);
		dynamicView = (TextView) view.findViewById(R.id.dynamic);
		view.findViewById(R.id.chat).setOnClickListener(this);
		view.findViewById(R.id.friends).setOnClickListener(this);
		view.findViewById(R.id.group).setOnClickListener(this);
		view.findViewById(R.id.dynamic).setOnClickListener(this);
		return view;
	}

	//设置事件监听
	public void setQQBottomClickListener(OnQQBottomClickListener bottomClickListener){
		this.bottomClickListener = bottomClickListener;
	}
	
	public void setSelected(int position){
		switch(position) {
		case CHAT_POSITION:
			//改变其背景，让其显示选中
			chatView.setBackgroundResource(R.drawable.tab_selected);
			friendView.setBackgroundResource(R.drawable.tab_normal);
			groupView.setBackgroundResource(R.drawable.tab_normal);
			dynamicView.setBackgroundResource(R.drawable.tab_normal);
			break;
		case FRIEND_POSITION:
			chatView.setBackgroundResource(R.drawable.tab_normal);
			friendView.setBackgroundResource(R.drawable.tab_selected);
			groupView.setBackgroundResource(R.drawable.tab_normal);
			dynamicView.setBackgroundResource(R.drawable.tab_normal);
			break;
		case GROUP_POSITION:
			chatView.setBackgroundResource(R.drawable.tab_normal);
			friendView.setBackgroundResource(R.drawable.tab_normal);
			groupView.setBackgroundResource(R.drawable.tab_selected);
			dynamicView.setBackgroundResource(R.drawable.tab_normal);
			break;
		case DYNAMIC_POSITION:
			chatView.setBackgroundResource(R.drawable.tab_normal);
			friendView.setBackgroundResource(R.drawable.tab_normal);
			groupView.setBackgroundResource(R.drawable.tab_normal);
			dynamicView.setBackgroundResource(R.drawable.tab_selected);
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		int position = 1;
		switch(v.getId()) {
		case R.id.chat:
			position = CHAT_POSITION;
			break;
		case R.id.friends:
			position = FRIEND_POSITION;
			break;
		case R.id.group:
			position = GROUP_POSITION;
			break;
		case R.id.dynamic:
			position = DYNAMIC_POSITION;
			break;
		}
		setSelected(position);
		if(bottomClickListener != null) {
			bottomClickListener.onBottomClick(v, position);
		}
	}
}
