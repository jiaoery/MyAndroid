package jixiang.com.myandroid.adapterview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import jixiang.com.myandroid.R;


public class MyGalleryAdapter extends BaseAdapter{
	Integer[] list;
	LayoutInflater inflater;
	public MyGalleryAdapter(Context context, Integer[] list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		if(list != null) {
			return list.length;}
		return 0;
	}
	@Override
	public Object getItem(int position) {
		if(list != null && position < list.length) {
			return list[position];
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
			convertView = inflater.inflate(R.layout.gallery_item, parent, false);
			holder.icon = (ImageView) convertView.findViewById(R.id.imageview);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if(position < list.length) {
			holder.icon.setBackgroundResource(list[position]);
		}
		return convertView;
	}
	class Holder{
		ImageView icon;
	}

}
