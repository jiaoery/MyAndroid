package jixiang.com.myandroid.adapterview;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import jixiang.com.myandroid.R;

public class FruitAdapter extends BaseAdapter{
	List<TestSpinnerActivity.FruitBean> list;
	LayoutInflater inflater;
	public FruitAdapter(Context context, List<TestSpinnerActivity.FruitBean> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		if(list != null) {
			return list.size();
		}return 0;
	}
	@Override
	public Object getItem(int position) {
		if(list != null && position < list.size()){
			return list.get(position);
		}return null;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.grid_item, parent, false);
			holder.fruitIcon = (ImageView) convertView.findViewById(R.id.fruit_icon);
			holder.furitTitle = (TextView) convertView.findViewById(R.id.fruit_title);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if(position < list.size()) {
			holder.furitTitle.setText(list.get(position).title);
			holder.fruitIcon.setBackgroundResource(list.get(position).iconId);
		}
		return convertView;
	}
	class Holder{
		ImageView fruitIcon;
		TextView furitTitle;
	}
	
}
