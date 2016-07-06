package jixiang.com.myandroid.databaseexam;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import jixiang.com.myandroid.R;

public class QQDinaryHome extends FragmentActivity implements OnClickListener{
	Button mBtwriteDianry; //写日志的按钮
	ListView listView;
	TextView emptyView;
	QQDinaryAdapter adapter;
	List<DinaryBean> list = new ArrayList<DinaryBean>();
	SQLiteDatabase database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		QQHelper helper = new QQHelper(this);
		database = helper.getWritableDatabase();
		setContentView(R.layout.qq_dinary_home);
		mBtwriteDianry = (Button) findViewById(R.id.write_dinary);
		listView = (ListView) findViewById(R.id.list);
		emptyView = (TextView) findViewById(R.id.empty_view);
		listView.setEmptyView(emptyView);
		adapter = new QQDinaryAdapter(this, list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new MyItemClickListener());
		listView.setOnItemLongClickListener(new MyItemLongClickListener());
		mBtwriteDianry.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.write_dinary:
			//跳到写日志界面
			Intent intent = new Intent(this, QQDinaryWrite.class);
			startActivity(intent);
			break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateInterface();
	}
	
	/**
	 * 刷新界面
	 */
	public void updateInterface(){
		ArrayList<DinaryBean> mList = new ArrayList<DinaryBean>();
		Cursor cursor = database.query(QQHelper.TABLE_NAME, null, null, null, null, null, " id desc ");
		if(cursor != null && cursor.getCount() > 0) {
			while(cursor.moveToNext()) {
				DinaryBean bean = new DinaryBean();
				bean.id = cursor.getLong(cursor.getColumnIndex("id"));
				bean.title = cursor.getString(cursor.getColumnIndex("title"));
				bean.content = cursor.getString(cursor.getColumnIndex("content"));
				bean.publish_time = cursor.getString(cursor.getColumnIndex("publish_time"));
				mList.add(bean);
			}
			cursor.close();
			cursor = null;
		}
		list.clear();
		list.addAll(mList); //添加新的数据源
		adapter.notifyDataSetChanged();//通知数据源发送改变,更新界面
	}
	
	class MyItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
			DinaryBean bean = list.get(position); //
			Intent intent = new  Intent(QQDinaryHome.this, QQDinaryView.class);
			intent.putExtra("bean", bean);
			startActivity(intent);
		}
		
	}
	
	class MyItemLongClickListener implements OnItemLongClickListener{

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			final DinaryBean bean = list.get(position); //
			showPopupWindow(bean, view);
			return true;
		}
		
	}
	
	public void showPopupWindow(DinaryBean bean, View view){
		PopupWindow popupWindow = new PopupWindow(this);
		popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);//设置可点击
		popupWindow.setFocusable(true); //可聚焦
		popupWindow.setOutsideTouchable(true);//设置外部可点击
		ColorDrawable colorDrawable = new ColorDrawable(0x66FFFFFF);
		popupWindow.setBackgroundDrawable(colorDrawable); //设置背景
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View viewMenu = inflater.inflate(R.layout.qq_dinary_menu, null);
		TextView modifyTv = (TextView) viewMenu.findViewById(R.id.modify);
		TextView deleteTv = (TextView) viewMenu.findViewById(R.id.delete);
		modifyTv.setOnClickListener(new MyMenuClickListener(bean, popupWindow));
		deleteTv.setOnClickListener(new MyMenuClickListener(bean, popupWindow));
		popupWindow.setContentView(viewMenu);
		popupWindow.showAsDropDown(view, 100, 0);
	}
	
	
	class MyMenuClickListener implements OnClickListener {

		DinaryBean bean;
		PopupWindow popupWindow;
		
		public MyMenuClickListener(DinaryBean bean, PopupWindow popupWindow) {
			this.bean = bean;
			this.popupWindow = popupWindow;
		}
		
		@Override
		public void onClick(View v) {
			popupWindow.dismiss();
			switch(v.getId()){
			case R.id.modify:
				Intent intent = new Intent(QQDinaryHome.this, QQDinaryWrite.class);
				intent.putExtra("isModify", true);
				intent.putExtra("bean", bean);
				startActivity(intent);
				break;
			case R.id.delete:
				int count = database.delete(QQHelper.TABLE_NAME, " id = ? ", new String[] {bean.id + ""});
				if(count >0) {
					updateInterface();
				}
				break;
			}
		}
		
	}
}
