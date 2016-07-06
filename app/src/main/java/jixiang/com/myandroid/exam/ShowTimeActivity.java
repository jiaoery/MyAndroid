package jixiang.com.myandroid.exam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.dialog.CustomAlertDialog;


public class ShowTimeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exam_show_time);
	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		showExitDialog();
	}
	
	
	public void showExitDialog(){
		AlertDialog.Builder builder = new CustomAlertDialog.Builder(this);
		builder.setTitle("提示！");
		builder.setIcon(R.drawable.a005);
		builder.setMessage("是否确认退出？");
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		builder.create().show();
	}
}
