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

public class QQFriendAdapter extends BaseAdapter{

	//适配器对数据源的引用
	List<QQFriendBean> list;
	//布局加载器，作用是将一个xml的布局文件，转换成java代码能够识别的View对象
	LayoutInflater inflater;
	Context context;
	int selectedId = -1;
	
	public QQFriendAdapter(Context context, List<QQFriendBean> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	
	/**
	 * 返回需要显示的数据项的总数
	 */
	@Override
	public int getCount() {
		if(list != null) {
			return list.size();
		}
		return 0;
	}
	
	public void setSelection(int selectedId) {
		this.selectedId = selectedId;
		notifyDataSetChanged();
	}

	/**
	 * 根据指定的position位置信息，从数据集合里面拿到这个位置项的对象
	 */
	@Override
	public Object getItem(int position) {
		if(list != null && position < list.size()) {
			return list.get(position);
		}
		return null;
	}

	/**
	 * 根据指定的position位置信息，返回当前位置项的行的ID
	 * 注意  position 和 ID 是有区别的 (指当给listView设置了header 和footer时)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 根据指定的position位置信息，从数据集合里面获取到这个位置的数据对象，然后生成一个View（显示项）
	 * 并将获取到的数据绑定到这个view上面
	 * position 位置
	 * convertView 缓存的View
	 * ViewGroup 父控件
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//对象引用保留
		Holder holder;
		
		if(convertView == null) {//未使用缓存的View
			System.out.println("未使用缓存  position=" + position);
			holder = new Holder();//初始化一个对象引用保留
			//新生成一个数据项的View
			convertView = inflater.inflate(R.layout.qq_friend_item, null);
			//初始化对象保留类里面的属于引用
			holder.icon = (ImageView) convertView.findViewById(R.id.header_icon);
			//从convertView这个视图树里面去查找id为name的控件，这个控件为一个TextView，最后将控件的引用赋值给对象保留器里面的name这个对象
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.sign = (TextView) convertView.findViewById(R.id.sign);
			//给convertView设置标签,标签就是这个数据项的对象引用保留
			convertView.setTag(holder);
		} else {//使用缓存的View
			System.out.println("使用缓存  position=" + position);
			//从convertView里面去获取到tag标签，这个标签就是这个View里面的控件的对象引用保留
			holder = (Holder) convertView.getTag();
		}
		
		if(position == selectedId) {
			convertView.setBackgroundResource(R.drawable.list_item_pressed);
		} else {
			convertView.setBackgroundResource(R.drawable.list_item_selector);
		}
		
		
		//绑定数据，也就是根据当前position位置从数据源里面取出这个位置的数据对象，然后将数据绑定在当前的View上。
		if(position < list.size()) {
			//设置头像
			holder.icon.setBackgroundResource(list.get(position).header_icon);
			//设置姓名
			holder.name.setText(list.get(position).name);
			//设置签名
			holder.sign.setText(list.get(position).sign);
		}
		//返回当前项的视图
		return convertView;
	}

	/**
	 * 对每个数据项里面的控件的引用
	 * 也是每个数据项里面的控件的保存
	 * 使用他可以在getView方法里面当convertView不为空，也就是这个数据项是使用缓存的View的时候，
	 * 他可以快捷的访问到View里面的控件的引用
	 */
	class Holder{
		ImageView icon;
		TextView name;
		TextView sign;
	}
}
