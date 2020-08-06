package jixiang.com.myandroid.databaseexam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import jixiang.com.myandroid.R;


public class QQDinaryWrite extends FragmentActivity implements OnClickListener{
	EditText mEtTitle;
	EditText mEtContent;
	boolean isModify = false; //标志位，决定是写日志，还是修改日志
	DinaryBean bean ;// 修改日志的数据
	SQLiteDatabase database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qq_dinary_write);
		QQHelper helper = new QQHelper(this);
		database = helper.getWritableDatabase();
		if(getIntent() != null && getIntent().hasExtra("isModify")) {
			isModify = getIntent().getBooleanExtra("isModify", false);
			//获取到修改日志的数据
			bean = (DinaryBean) getIntent().getSerializableExtra("bean");
		}
		mEtTitle = (EditText) findViewById(R.id.dianry_title);
		mEtContent = (EditText) findViewById(R.id.dinary_content);
		findViewById(R.id.publish).setOnClickListener(this);
		findViewById(R.id.cancel).setOnClickListener(this);
		if(isModify && bean != null) {
			TextView title = (TextView) findViewById(R.id.write_title);
			title.setText("修改日志");
			Button button = (Button) findViewById(R.id.publish);
			button.setText("修改日志");
			//自动填充数据
			mEtTitle.setText(bean.title);
			mEtContent.setText(bean.content);
		}
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.publish:
			publishOrModify(isModify);
			break;
		case R.id.cancel:
			finish();
			break;
		}
	}
	
	public boolean isCheck(){
		String title = mEtTitle.getText().toString();
		if(title != null && !title.equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	public void publishOrModify(boolean isModify){
		if(isCheck()) { //验证通过
			 //保存数据
			String title = mEtTitle.getText().toString();
			String content = mEtContent.getText().toString();
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
			String publishTime = dateFormat.format(date);
			
			if(isModify) { //更改日志
				//组装数据
				ContentValues values = new ContentValues();
				values.put("title", title);
				values.put("content", content);
				values.put("publish_time", publishTime);
				long id = database.update(QQHelper.TABLE_NAME, values, " id= ?", new String[] {new String(bean.id +"")});
				if(id >0) {//修改成功
					finish();
				} else {
					
				}
			} else { //新建日志
				//组装数据
				ContentValues values = new ContentValues();
				values.put("title", title);
				values.put("content", content);
				values.put("publish_time", publishTime);
				long id = database.insert(QQHelper.TABLE_NAME, null, values);
				if(id >0) {//数据已经保存了
					finish();
				} else {
					
				}
			}
		} else {//验证未通过
			
		}
	}
}
