package jixiang.com.myandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import jixiang.com.myandroid.R;

/**
 * Created by Administrator on 2016/7/5.
 */
public class MainAdapter extends BaseAdapter {
    private String[] titles;
    private LayoutInflater inflater;

    //构造方法
    public MainAdapter(String[] title, Context context) {
        this.titles = title;
        inflater = LayoutInflater.from(context);
    }

    //适配器有多少个数据
    @Override
    public int getCount() {
        if(titles != null) {
            return titles.length;
        }
        return 0;
    }

    //根据指定的位置,返回当前的项
    @Override
    public Object getItem(int position) {
        if(position >=0 && position < titles.length) {
            return titles[position];
        }
        return null;
    }

    //根据指定的位置,返回当前位置项的id
    @Override
    public long getItemId(int position) {
        return position;
    }

    //根据指定的position绑定数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.top_list_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if(position < titles.length) {
            //绑定数据
            holder.textView.setText(titles[position]);
        }
        return convertView;
    }

    class Holder{
        TextView textView;
    }
}
