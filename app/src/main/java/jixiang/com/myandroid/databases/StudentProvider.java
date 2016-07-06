package jixiang.com.myandroid.databases;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class StudentProvider extends ContentProvider{
	public static final String AUTHORITY = "com.three.androidlearning.student";
	private MyOpenHelper helper; //创建数据库
	private SQLiteDatabase studentDb; //数据库
	
	public static final Uri STUDENT_URI = Uri.parse("content://" + AUTHORITY + "/student");
	
	//定义uriMathcer的识别码
	public static final int STUDENTS = 1; //匹配student表
	public static final int STUDENT_ID = 2; //匹配特定的某个学生记录
	
	private static final UriMatcher uriMatcher; //
	
	static{
		
		// content://com.three.androidlearning.student/student
		// content://com.three.androidlearning.student/student/1
		// content://com.three.androidlearning.student/student/12
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		//AUTHORITY授权， student 路径， STUDENTS 匹配码
		uriMatcher.addURI(AUTHORITY, "student", STUDENTS); //添加表student的匹配码
		uriMatcher.addURI(AUTHORITY, "student/#", STUDENT_ID);// 匹配student表里面的特定id的记录的匹配码
	}

	//初始化ContentProvider的时候，创建的数据库，并创建表
	@Override
	public boolean onCreate() {
		helper = new MyOpenHelper(getContext()); //创建helper对象
		studentDb = helper.getWritableDatabase(); //创建数据库和表
		if(studentDb != null) {
			return true;
		}
		return false;
	}

	//返回此数据的MIME类型
	@Override
	public String getType(Uri uri) {
		// content://com.three.androidlearning.student/student  
		// content://com.three.androidlearning.student/student/1
		// content://com.three.androidlearning.student/student/12
		// uriMatcher.match(Uri.parse("content://com.three.androidlearning.student/student")) ==1 (STUDENTS)
		// uriMatcher.match(Uri.parse("content://com.three.androidlearning.student/student/12"))  ==2 (STUDENT_ID)
		switch(uriMatcher.match(uri)) {
		case STUDENTS:
			return "vnd.android.cursor.dir/com.three.androidlearning"; //自定义的一个MIME类型，表明是一张数据表
		case STUDENT_ID:
			return "vnd.android.cursor.item/com.three.androidlearning"; //表明是一条记录
		}
		return null;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		System.out.println("uri=" + uri);
		switch(uriMatcher.match(uri)) {
		case STUDENTS:
			cursor = studentDb.query(MyOpenHelper.TABEL_STUDENT, projection, selection, selectionArgs, null, null, sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), uri); //通知表的变化
			break;
		case STUDENT_ID:
			// content://com.three.androidlearning.student/student/12
			// ContentUris.parseId(Uri.parse("content://com.three.androidlearning.student/student/12")) ==12
			long id = ContentUris.parseId(uri);
			String where;
			String[] whereArgs;
			if(selection == null) {
				where = " sid= ?" ;
				whereArgs = new String[] {new String(id + "")};
			} else {
				where = selection +  " and sid= ?" ;
				if(selectionArgs == null) {
					whereArgs = new String[] {new String(id + "")};
				} else {
					whereArgs = new String[selectionArgs.length +1];
					for(int i=0; i< selectionArgs.length ; i++) {
						whereArgs[i] = selectionArgs[i];
					}
					whereArgs[whereArgs.length -1] = new String(id + "");
				}
			}
			System.out.println("where=" + where);
			System.out.println("whereArgs=" + whereArgs[0]);
			cursor = studentDb.query(MyOpenHelper.TABEL_STUDENT, projection, where, whereArgs, null, null, sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			break;
		default:
			
			System.out.println("default" + uriMatcher.match(uri));
			break;
		}
		return cursor;
	}

	

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long id = -1;
		switch(uriMatcher.match(uri)) {
		case STUDENTS:
			id = studentDb.insert(MyOpenHelper.TABEL_STUDENT, null, values);
			break;
		case STUDENT_ID:
			long rowId = ContentUris.parseId(uri);
			if(values != null) {
				values.put("sid", rowId);
			}
			id = studentDb.insert(MyOpenHelper.TABEL_STUDENT, null, values);
			break;
		}
		if(id != -1) {
			uri = ContentUris.withAppendedId(uri, id); //重新组装URI
			getContext().getContentResolver().notifyChange(uri, null); //通知数据改变
			return uri;
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;//受影响的记录的条数
		switch(uriMatcher.match(uri)) {
		case STUDENTS:
			count = studentDb.delete(MyOpenHelper.TABEL_STUDENT, selection, selectionArgs);
			break;
		case STUDENT_ID:
			long id = ContentUris.parseId(uri);
			String where;
			String[] whereArgs;
			if(selection == null) {
				where = " sid= ?" ;
				whereArgs = new String[] {new String(id + "")};
			} else {
				where = selection +  " and sid= ?" ;
				if(selectionArgs == null) {
					whereArgs = new String[] {new String(id + "")};
				} else {
					whereArgs = new String[selectionArgs.length +1];
					for(int i=0; i< selectionArgs.length ; i++) {
						whereArgs[i] = selectionArgs[i];
					}
					whereArgs[whereArgs.length -1] = new String(id + "");
				}
			}
			System.out.println("where=" + where);
			System.out.println("whereArgs=" + whereArgs[0]);
			count = studentDb.delete(MyOpenHelper.TABEL_STUDENT, where, whereArgs);
			break;
		}
		if(count > 0) { //受影响的记录，就是删除的记录条数是大于0的
			getContext().getContentResolver().notifyChange(uri, null); //通知数据改变
		}
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {
		int count = 0;
		switch(uriMatcher.match(uri)) {
		case STUDENTS:
			count = studentDb.update(MyOpenHelper.TABEL_STUDENT, values, selection, selectionArgs);
			break;
		case STUDENT_ID:
			long id = ContentUris.parseId(uri);
			String where;
			String[] whereArgs;
			if(selection == null) {
				where = " sid= ?" ;
				whereArgs = new String[] {new String(id + "")};
			} else {
				where = selection +  " and sid= ?" ;
				if(selectionArgs == null) {
					whereArgs = new String[] {new String(id + "")};
				} else {
					whereArgs = new String[selectionArgs.length +1];
					for(int i=0; i< selectionArgs.length ; i++) {
						whereArgs[i] = selectionArgs[i];
					}
					whereArgs[whereArgs.length -1] = new String(id + "");
				}
			}
			System.out.println("where=" + where);
			System.out.println("whereArgs=" + whereArgs[0]);
			count = studentDb.update(MyOpenHelper.TABEL_STUDENT, values, where, whereArgs);
			break;
		}
		if(count > 0) { //记录有修改过
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

}
