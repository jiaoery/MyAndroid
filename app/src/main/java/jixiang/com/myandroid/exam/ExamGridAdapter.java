package jixiang.com.myandroid.exam;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import jixiang.com.myandroid.R;


public class ExamGridAdapter extends BaseAdapter{
	ArrayList<ShowGridActivity.ImageBean> list;
	LayoutInflater inflater;
	
	public ExamGridAdapter(Context context, ArrayList<ShowGridActivity.ImageBean> list) {
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
			convertView = inflater.inflate(R.layout.exam_grid_item, parent, false);
			holder.imageView = (ImageView) convertView.findViewById(R.id.image);
			holder.textView = (TextView) convertView.findViewById(R.id.description);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if( list != null && position < list.size()) {
			holder.imageView.setBackgroundResource(list.get(position).imageId);
			holder.textView.setText(list.get(position).description);
		}
		return convertView;
	}

	class Holder{
		ImageView imageView;
		TextView textView;
	}
}
