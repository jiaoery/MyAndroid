package jixiang.com.myandroid.adapterview;

import java.util.ArrayList;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.view.CircleView;

public class CarAdapter extends BaseAdapter{
	ArrayList<CarBean> list;
	LayoutInflater inflater;
	public CarAdapter(Context context, ArrayList<CarBean> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			convertView = inflater.inflate(R.layout.car_rest_item, parent, false);
			holder.circleView = (CircleView) convertView.findViewById(R.id.circle_view);
			holder.rest = (TextView) convertView.findViewById(R.id.rest);
			holder.max = (TextView) convertView.findViewById(R.id.max);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.distance = (TextView) convertView.findViewById(R.id.distance);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if(position < list.size()) {
			holder.circleView.setRestAndMax(list.get(position).rest, list.get(position).max);
			holder.rest.setText(list.get(position).rest + "");
			holder.max.setText(list.get(position).max + "");
			holder.title.setText(list.get(position).title);
			holder.address.setText(list.get(position).address);
			holder.distance.setText(list.get(position).distance + "m");
		}
		return convertView;
	}

	class Holder{
		CircleView circleView;
		TextView rest;
		TextView max;
		TextView title;
		TextView address;
		TextView distance;
	}
}
