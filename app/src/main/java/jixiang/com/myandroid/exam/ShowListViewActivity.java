package jixiang.com.myandroid.exam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import jixiang.com.myandroid.R;

public class ShowListViewActivity extends Activity implements OnClickListener {
	ListView listView;
	TextView emptyView;
	ArrayList<ListItemBean> list = new ArrayList<ListItemBean>(200);
	ExamListAdapter adapter;
	int start = 0;
	Calendar calendar = Calendar.getInstance(Locale.getDefault());

	public static class ListItemBean implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int iconId;
		public String title;
		public String content;
		public long time;
		public int comments;

		public ListItemBean() {
		}

		public ListItemBean(int iconId, String title, String content,
				long time, int comments) {
			this.iconId = iconId;
			this.title = title;
			this.content = content;
			this.time = time;
			this.comments = comments;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exam_listview);
		listView = (ListView) findViewById(R.id.listview);
		emptyView = (TextView) findViewById(R.id.empty_view);
		listView.setEmptyView(emptyView);
		findViewById(R.id.button).setOnClickListener(this);
		adapter = new ExamListAdapter(this, list);
		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		addData();
	}

	public void addData() {
		for (int i = 0; i < 12; i++) {
			ListItemBean bean = new ListItemBean(R.drawable.a006, "测试"
					+ (start + i), "这是测试数据" + (start + i),
					calendar.getTimeInMillis(), (start + i));
			list.add(bean);
		}
		start+=12;
		adapter.notifyDataSetChanged();
	}

}
