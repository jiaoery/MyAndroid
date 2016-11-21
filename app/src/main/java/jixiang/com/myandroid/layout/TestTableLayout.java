package jixiang.com.myandroid.layout;


import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import jixiang.com.myandroid.R;

public class TestTableLayout extends Activity {
	private TableLayout tableLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_tablelayout);
		// 在布局文件test_tablelayout文件中，查找id为 tablelayout的控件，此控件为 TableLayout
		tableLayout = (TableLayout) findViewById(R.id.tablelayout);
		addData();
	}

	public void addData() {
		for (int i = 0; i < 10; i++) {
			// 新生成一个TableRow控件， 即行控件
			TableRow row = new TableRow(this);
			// 生成布局参数
			TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(
					TableLayout.LayoutParams.MATCH_PARENT,
					TableLayout.LayoutParams.WRAP_CONTENT);
			// 设置上下外间距
			layoutParams.topMargin = 10;
			layoutParams.bottomMargin = 10;
			// 把布局参数设置给TableRow
			row.setLayoutParams(layoutParams);

			// 生成一个TextView
			TextView textView1 = new TextView(this);
			// 生成布局参数
			TableRow.LayoutParams layoutParams2 = new TableRow.LayoutParams(
					TableRow.LayoutParams.WRAP_CONTENT,
					TableRow.LayoutParams.WRAP_CONTENT);
			layoutParams2.weight = 1;
			textView1.setLayoutParams(layoutParams2);
			textView1.setText((222007 + i) + "");
			textView1.setGravity(Gravity.CENTER);
			// 添加到tableRow中
			row.addView(textView1);

			TextView textView2 = new TextView(this);
			TableRow.LayoutParams layoutParams3 = new TableRow.LayoutParams(
					TableRow.LayoutParams.WRAP_CONTENT,
					TableRow.LayoutParams.WRAP_CONTENT);
			layoutParams3.weight = 1;
			textView2.setLayoutParams(layoutParams3);
			textView2.setText("name" + i);////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			textView2.setGravity(Gravity.CENTER);
			row.addView(textView2);

			TextView textView3 = new TextView(this);
			TableRow.LayoutParams layoutParams4 = new TableRow.LayoutParams(
					TableRow.LayoutParams.WRAP_CONTENT,
					TableRow.LayoutParams.WRAP_CONTENT);
			layoutParams4.weight = 1;
			textView3.setLayoutParams(layoutParams4);
			textView3.setText((i + 1) + "");
			textView3.setGravity(Gravity.CENTER);
			row.addView(textView3);

			// 把这一行TableRow添加到TableLayout里面去
			tableLayout.addView(row);
		}
	}
}
