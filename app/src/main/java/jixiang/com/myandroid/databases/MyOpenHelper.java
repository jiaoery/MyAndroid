package jixiang.com.myandroid.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper{
	public static final String DATABASE_STUDENT = "student_database"; //数据库的名称
	public static final String TABEL_STUDENT = "student"; //学生表的名称
	public static final int VERSION = 2; //版本号

	public MyOpenHelper(Context context) {
		super(context, DATABASE_STUDENT, null, VERSION);
	}

	//创建数据库的时候调用, 如果数据库已经存在，则不会调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建表的sql语句
		String create_student = "CREATE TABLE `student` (" +
								 "sid  INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL," +
								 "name char(32) NOT NULL," +
								 "age INT NOT NULL" +
								 ");";
		System.out.println("create_student=" + create_student);
		db.execSQL(create_student); //创建表
	}

	//数据库版本更新的时候，调用这个
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists student "); //删掉以前的表
		onCreate(db); //再重建
	}

}
