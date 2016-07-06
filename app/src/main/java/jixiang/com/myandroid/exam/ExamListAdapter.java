package jixiang.com.myandroid.exam;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import jixiang.com.myandroid.R;

public class ExamListAdapter extends BaseAdapter{
	ArrayList<ShowListViewActivity.ListItemBean> list;
	LayoutInflater inflater;
	
	public ExamListAdapter(Context context, ArrayList<ShowListViewActivity.ListItemBean> list) {
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
			convertView = inflater.inflate(R.layout.exam_list_item, parent, false);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.comments = (TextView) convertView.findViewById(R.id.commnets);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if(list != null && position < list.size()) {
			holder.icon.setBackgroundResource(list.get(position).iconId);
			holder.title.setText(list.get(position).title);
			holder.content.setText(list.get(position).content);
			holder.comments.setText(list.get(position).comments + "");
			Date date = new Date(list.get(position).time);
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			holder.time.setText(formater.format(date));
		}
		return convertView;
	}

	class Holder{
		ImageView icon;
		TextView title;
		TextView content;
		TextView time;
		TextView comments;
	}
}
