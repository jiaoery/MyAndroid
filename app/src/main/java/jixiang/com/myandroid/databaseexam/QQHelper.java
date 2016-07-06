package jixiang.com.myandroid.databaseexam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QQHelper extends SQLiteOpenHelper{
	public static final String DATABASE_NAME = "dinary";
	public static final String TABLE_NAME= "qq_dinary";
	public static final int VERSION = 1;

	public QQHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table  "+ TABLE_NAME + " ( " +
				"id integer primary key autoincrement not null," +
				"title text not null," +
				"content text," +
				"publish_time char(32) not null" +
				");";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table  if exists " + TABLE_NAME + ";");
		onCreate(db);
	}

}
