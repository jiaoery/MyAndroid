package jixiang.com.myandroid.adapterview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


class RunRankAdapter extends BaseAdapter {
	ArrayList<HashMap<String, Object>> ls;
	Context mContext;
	LinearLayout linearLayout = null;
	LayoutInflater inflater;
	TextView tex;
	final int VIEW_TYPE = 3;
	final int TYPE_1 = 0;
	final int TYPE_2 = 1;
	final int TYPE_3 = 2;

	public RunRankAdapter(Context context,
			ArrayList<HashMap<String, Object>> list) {
		ls = list;
		mContext = context;
	}

	@Override
	public int getCount() {
		return ls.size();
	}

	@Override
	public Object getItem(int position) {
		return ls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// 每个convert view都会调用此方法，获得当前所需要的view样式
	@Override
	public int getItemViewType(int position) {
		//以数据为驱动 ，也就是根据数据源的类型，来确定显示的类型
		Map map = ls.get(position);
		if(map.get("name").equals("A")) {
			return TYPE_1;
		} else if(map.get("name").equals("B")) {
			return TYPE_2;
		}
		int p = position;
		if (p == 0)
			return TYPE_1;
		else if (p == 1)
			return TYPE_2;
		else
			return TYPE_3;
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder1 holder1 = null;
		ViewHolder2 holder2 = null;
		ViewHolder3 holder3 = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			inflater = LayoutInflater.from(mContext);
			// 按当前所需的样式，确定new的布局
			switch (type) {
			case TYPE_1:
				/*convertView = inflater.inflate(R.layout.item_runrank1,parent, false);
				holder1 = new ViewHolder1();
				holder1.rank1 = (TextView) convertView.findViewById(R.id.tv_list1_rank);
				holder1.time1 = (TextView) convertView.findViewById(R.id.tv_list1_time);*/
				convertView.setTag(holder1);
				break;
			case TYPE_2:
				/*convertView = inflater.inflate(R.layout.item_runrank2, parent, false);
				holder2 = new ViewHolder2();
				holder2.rank2 = (TextView) convertView.findViewById(R.id.tv_list2_rank);
				holder2.time2 = (TextView) convertView.findViewById(R.id.tv_list2_time);*/
				convertView.setTag(holder2);
				break;
			case TYPE_3:
				/*convertView = inflater.inflate(R.layout.item_runrank3,parent, false);
				holder3 = new ViewHolder3();
				holder3.rank3 = (TextView) convertView.findViewById(R.id.tv_list3_rank);
				holder3.time3 = (TextView) convertView.findViewById(R.id.tv_list3_time);
				convertView.setTag(holder3);*/
				break;
			default:
				break;
			}

		} else {
			switch (type) {
			case TYPE_1:
				holder1 = (ViewHolder1) convertView.getTag();
				break;
			case TYPE_2:
				holder2 = (ViewHolder2) convertView.getTag();
				break;
			case TYPE_3:
				holder3 = (ViewHolder3) convertView.getTag();
				break;
			}
		}
		// 设置资源
		switch (type) {
		case TYPE_1:
			holder1.rank1.setText("" + (position + 1));
			holder1.time1.setText(ls.get(position).get("time").toString());
			break;
		case TYPE_2:
			holder2.rank2.setText("" + (position + 1));
			holder2.time2.setText(ls.get(position).get("time").toString());
			break;
		case TYPE_3:
			holder3.rank3.setText("" + (position + 1));
			holder3.time3.setText(ls.get(position).get("time").toString());
			break;
		}

		return convertView;
	}

	public class ViewHolder1 {
		TextView rank1;
		TextView time1;
	}

	public class ViewHolder2 {
		TextView rank2;
		TextView time2;
	}

	public class ViewHolder3 {
		TextView rank3;
		TextView time3;
	}
}
