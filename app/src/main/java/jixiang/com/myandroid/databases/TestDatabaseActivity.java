package jixiang.com.myandroid.databases;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import jixiang.com.myandroid.R;

/**
 * 测试数据库
 */
public class TestDatabaseActivity extends FragmentActivity implements OnClickListener{
	
	SQLiteDatabase database;
	ListView listView;
	int rowId = 0; //最大的ID值
	List<StudentBean> list = new ArrayList<StudentBean>(30);
	MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyOpenHelper helper = new MyOpenHelper(this);
		database = helper.getWritableDatabase(); //获取到database对象
		setContentView(R.layout.test_database);
		listView = (ListView) findViewById(R.id.student_list);
		getMaxRowId();
		findViewById(R.id.insert).setOnClickListener(this);
		findViewById(R.id.delete).setOnClickListener(this);
		findViewById(R.id.update).setOnClickListener(this);
		findViewById(R.id.query).setOnClickListener(this);
		adapter = new MyAdapter(this, list);
		listView.setAdapter(adapter);
		queryData();
		//观察者对象
        ContentObserver contentObserver = new ContentObserver(new Handler()) {
			
			@Override
			public void onChange(boolean selfChange) {
				super.onChange(selfChange);
				System.out.println("onChange(boolean selfChange)");
			}
			
			@Override
			public void onChange(boolean selfChange, Uri uri) {
				System.out.println("onChange(boolean selfChange, Uri uri)");
				queryData(); // 更新数据
			}
		};
		Uri uri = StudentProvider.STUDENT_URI;
		//注册观察者
		getContentResolver().registerContentObserver(uri, true, contentObserver);
	}

	//获取最大行id
	public void getMaxRowId(){
		Cursor cursor = database.query(MyOpenHelper.TABEL_STUDENT, new String[]{" max(sid) as max_sid"}, null, null, null, null, null);
		if(cursor != null && cursor.getCount() > 0) {
			try{
				int cloumnCount = cursor.getColumnCount();
				System.out.println("cloumnCount=" + cloumnCount);
				String[] columnNames = cursor.getColumnNames();
				if(columnNames != null && columnNames.length > 0) {
					for(int i=0; i< columnNames.length; i++) {
						System.out.println(columnNames[i]);
					}
				}
				//cursor.moveToPrevious(); //移动到
				cursor.moveToNext(); //移动到下一行数据,此处是移动到第一行数据
				int cloumIndex = cursor.getColumnIndex("max_sid");
				rowId = cursor.getInt(cloumIndex);
				System.out.println("rowId=" + rowId);
			} catch (CursorIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		cursor.close(); //关闭游标
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.insert:
			insertData();
			break;
		case R.id.delete:
			deleteData();
			break;
		case R.id.update:
			update();
			break;
		case R.id.query:
			break;
		}
		queryData();
	}
	
	
	public void queryData(){
		List<StudentBean> myList = new ArrayList<StudentBean>();
		/*Cursor cursor = database.query(MyOpenHelper.TABEL_STUDENT, null, null, null, null, null, null);
		if(cursor != null && cursor.getCount() > 0) {
			//cursor.moveToPrevious(); //移动到上一条记录, 在这里执行的时候，是没有用的,因为已经在表头了
			cursor.moveToFirst(); //将游标移动到第一条记录处
			cursor.moveToPrevious();
			while(cursor.moveToNext()) {
				StudentBean bean = new StudentBean();
				bean.sid = cursor.getInt(cursor.getColumnIndex("sid"));
				bean.name = cursor.getString(cursor.getColumnIndex("name"));
				bean.age = cursor.getInt(cursor.getColumnIndex("age"));
				myList.add(bean);
			}
		}
		cursor.close(); //关闭游标*/
		Uri uri = StudentProvider.STUDENT_URI;
		String MIMEString = getContentResolver().getType(uri); //vnd.android.cursor.dir/jixiang.com.myandroid
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		if(cursor != null && cursor.getCount() > 0) {
			//cursor.moveToPrevious(); //移动到上一条记录, 在这里执行的时候，是没有用的,因为已经在表头了
			cursor.moveToFirst(); //将游标移动到第一条记录处
			cursor.moveToPrevious();
			while(cursor.moveToNext()) {
				StudentBean bean = new StudentBean();
				bean.sid = cursor.getInt(cursor.getColumnIndex("sid"));
				bean.name = cursor.getString(cursor.getColumnIndex("name"));
				bean.age = cursor.getInt(cursor.getColumnIndex("age"));
				myList.add(bean);
			}
		}
		cursor.close();
		list.clear(); //清空以前的数据
		list.addAll(myList); //使用新的数据
		adapter.notifyDataSetChanged(); //通知数据发生改变
	}
	
	public void insertData(){
		/*for(int i=rowId; i<10; i++) {
			ContentValues values = new ContentValues();
			values.put("name", "zhangsan" + i);
			values.put("age", i+20);
			//新记录的行id
			long id = database.insert(MyOpenHelper.TABEL_STUDENT, null, values);
		}*/
		for(int i=rowId; i<10; i++) {
			ContentValues values = new ContentValues();
			values.put("name", "zhangsan" + i);
			values.put("age", i+20);
			//新记录的行id
			//long id = database.insert(MyOpenHelper.TABEL_STUDENT, null, values);
			Uri uri = getContentResolver().insert(StudentProvider.STUDENT_URI, values);
		}
	}
	
	
	public void deleteData(){
		//rows 删除的行数
		//int rows = database.delete(MyOpenHelper.TABEL_STUDENT, null, null);
		int rows = getContentResolver().delete(StudentProvider.STUDENT_URI, null, null);
	}
	
	public void update(){
		ContentValues values = new ContentValues();
		values.put("name", "Lisi");
		//更新的记录的条数
		//int rows = database.update(MyOpenHelper.TABEL_STUDENT, values, null, null);
		int rows = getContentResolver().update(StudentProvider.STUDENT_URI, values, null, null);
	}
	
	class StudentBean{
		public int sid;
		public String name;
		public int age;
		
		public StudentBean() {
		}
		public StudentBean(int sid, String name, int age) {
			this.sid  =sid;
			this.name = name;
			this.age = age;
		}
	}
	
	class MyAdapter extends BaseAdapter{
		List<StudentBean> list;
		LayoutInflater inflater;
		public MyAdapter(Context context, List<StudentBean> list) {
			this.list = list;
			inflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return list.size();
		}
		@Override
		public Object getItem(int position) {
			return list.get(position);
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
				convertView = inflater.inflate(R.layout.student_item, null);
				holder.sid = (TextView) convertView.findViewById(R.id.sid);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.age = (TextView) convertView.findViewById(R.id.age);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			if(position < list.size()) {
				holder.sid.setText(list.get(position).sid + "");
				holder.name.setText(list.get(position).name );
				holder.age.setText(list.get(position).age + "");
			}
			return convertView;
		}
		class Holder{
			TextView sid;
			TextView name;
			TextView age;
		}
	}
	
}
