package jixiang.com.myandroid.databaseexam;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import jixiang.com.myandroid.R;

public class QQDinaryAdapter extends BaseAdapter{
	List<DinaryBean> list; //数据源
	LayoutInflater inflater;
	
	public QQDinaryAdapter(Context context, List<DinaryBean> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if(list != null && position < list.size()) {
			return list.get(position);
		}
		return null;
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
			convertView = inflater.inflate(R.layout.qq_dinary_list_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.publish_time = (TextView) convertView.findViewById(R.id.publish_time);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if(position < list.size()) {
			holder.title.setText(list.get(position).title);
			holder.publish_time.setText(list.get(position).publish_time);
		}
		return convertView;
	}

	class Holder{
		TextView title;
		TextView publish_time;
	}
}
