package jixiang.com.myandroid.contentprovider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import jixiang.com.myandroid.R;

public class TestProvider extends Activity {

    ContentResolver contentResolver;

    Uri uri=Uri.parse("content://jixiang.com.myandroid.first/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_provider);
        //获取系统默认的ContentResolver
        contentResolver=getContentResolver();
    }

    //查询
    public void query(View view){
        //调用ContentResolver的queryy方法,实际是调用Uri对应的ContentProvider的quere()的返回值
        Cursor cursor=contentResolver.query(uri,null,"query_where",null,null);
        Toast.makeText(this,"远程ContentProvider的返回Cursor为："+cursor,Toast.LENGTH_SHORT).show();
    }

    //插入
    public void insert(View view) {
        ContentValues values=new ContentValues();
        values.put("name","jixiang");
        //调用ContentResolver的insert方法,实际是调用Uri对应的ContentProvider的insert()的返回值
        Uri newUrl=contentResolver.insert(uri,values);
        Toast.makeText(this,"远程ContentProvider的新插入记录Uri为："+newUrl,Toast.LENGTH_SHORT).show();
    }

    //更新
    public void update(View view) {
        ContentValues values=new ContentValues();
        values.put("name","android");
        //调用ContentResolver的update方法,实际是调用Uri对应的ContentProvider的update()的返回值
        int count=contentResolver.update(uri,values,"update_where",null);
        Toast.makeText(this,"远程ContentProvider的更新记录数目为："+count,Toast.LENGTH_SHORT).show();

    }

    //删除
    public void delete(View view) {
        //调用ContentResolver的delete方法,实际是调用Uri对应的ContentProvider的delete()的返回值
        int count=contentResolver.delete(uri,"delete_where",null);
        Toast.makeText(this,"远程ContentProvider的删除记录数为："+count,Toast.LENGTH_SHORT).show();
    }
}
