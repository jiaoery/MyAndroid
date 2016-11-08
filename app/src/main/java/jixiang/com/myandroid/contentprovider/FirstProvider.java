package jixiang.com.myandroid.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/11/8.
 */
public class FirstProvider extends ContentProvider{
    @Override
    public boolean onCreate() {
        System.out.println("===Oncreate 方法被调用====");
        return true;
    }

    @Nullable
    //实现查询的方法，该方法返回查询得到的Cursor
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        System.out.println(uri+"===query() 方法被调用====");
        System.out.println("where 的参数为"+selection);
        return null;
    }

    @Nullable
    //改方法的返回值代表了该ContentProvider所提供的数据MIME类型
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    //实现插入的方法，该方法应该返回新插入的记录Uri
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        System.out.println(uri+"===insert()方法被调用===");
        System.out.println("values的值为："+values);
        return null;
    }

    //实现删除的方法，该方法返回被删除的记录条数
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        System.out.println(uri+"===delete()方法被调用===");
        System.out.println("where参数为："+selection);
        return 0;
    }

    //实现更新的方法，该方法应该返回被更新的记录条数
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        System.out.println(uri+"===update()方法被调用===");
        System.out.println("where的参数为："+selection+",values参数为："+values);
        return 0;
    }
}
