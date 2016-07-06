package jixiang.com.myandroid.menu;


import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import jixiang.com.myandroid.R;

public class TestMenuActivity extends Activity {
	TextView textView ;
	ClipboardManager clipboardManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//系统的剪切板
		clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		LinearLayout layout = new LinearLayout(this);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(layoutParams);
		
		textView= new TextView(this);
		textView.setText("点击我，生成上下文菜单");
		textView.setLayoutParams(layoutParams);
		textView.setTag(new Integer(1));
		textView.setTextIsSelectable(true); //全局可选跟上下文菜单有事件冲突
		//上下文菜单的优先级要高些
		layout.addView(textView);
		setContentView(layout);
		
		//注册上下文菜单
		registerForContextMenu(textView);
	}

	boolean[] genderChecked = { false, false };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		System.out.println("onCreateOptionsMenu");
		/*
		 * menu.add("喜欢的颜色"); menu.add("你的性别");
		 */
		/**
		 * groupId The group identifier that this item should be part of. This
		 * can be used to define groups of items for batch state changes.
		 * Normally use NONE if an item should not be in a group. itemId Unique
		 * item ID. Use NONE if you do not need a unique ID. order The order for
		 * the item. Use NONE if you do not care about the order. See
		 * MenuItem.getOrder(). title The text to display for the item.
		 */
		/*menu.add(0, 1, 2, "喜欢的颜色");
		// MenuItem genderItem = menu.add(0, 2, 0, "你的性别");
		SubMenu subMenu = menu.addSubMenu(0, 2, 0, "你的性别").setHeaderIcon(
				R.drawable.a005);
		subMenu.add(1, 21, 0, "男").setChecked(genderChecked[0]);
		subMenu.add(1, 22, 0, "女").setChecked(genderChecked[1]);
		subMenu.setGroupCheckable(1, true, false);
		menu.add(0, 3, 1, "欢喜的车");*/
		
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.getItem(1).setEnabled(false);
		System.out.println("onPrepareOptionsMenu");
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		//MenuItem item = menu.getItem(0);
		//SubMenu subMenu = item.getSubMenu();
		//subMenu.removeGroup(R.id.group_id);
		System.out.println("onOptionsMenuClosed");
		super.onOptionsMenuClosed(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("onOptionsItemSelected");
		switch (item.getItemId()) {
		case 1:
			System.out.println("color");
			break;
		case 2:
			System.out.println("gender");
			break;
		case 3:
			System.out.println("car");
			break;
		case R.id.gender_male:
			if (item.isChecked()) {
				item.setChecked(false);
				genderChecked[0] = false;
			} else {
				item.setChecked(true);
				genderChecked[0] = true;
			}
			break;
		case R.id.gender_female:
			if (item.isChecked()) {
				item.setChecked(false);
				genderChecked[1] = false;
			} else {
				item.setChecked(true);
				genderChecked[1] = true;
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
//	public boolean onKeyUp(int keyCode, KeyEvent event) {
//		return super.onKeyDown(keyCode, event);
//	}

	PopupWindow popupWindow;
	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.out.println("menu click");
		if (keyCode == KeyEvent.KEYCODE_MENU) { // 拦截menu键
			if(popupWindow != null && popupWindow.isShowing()) {
				popupWindow.dismiss();
			} else {
				showPopMenu();
			}
		} 
		return super.onKeyDown(keyCode, event);
	}*/

	public void showPopMenu() {
		if(popupWindow == null) {
			popupWindow = new PopupWindow(this);
			popupWindow.setWindowLayoutMode(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			popupWindow.setTouchable(true);// 设置可点击
			popupWindow.setFocusable(true); // 可聚焦
			popupWindow.setOutsideTouchable(true);// 设置外部可点击
			ColorDrawable colorDrawable = new ColorDrawable(0x66FFFFFF);
			popupWindow.setBackgroundDrawable(colorDrawable); // 设置背景

			// 设置弹窗的内容
			LayoutInflater inflater = LayoutInflater.from(this);
			View view = inflater.inflate(R.layout.test_linearlayout, null);
			view.findViewById(R.id.button_1).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							System.out.println("Click Button1");
						}
					});
			popupWindow.setContentView(view);
		}
		// 显示弹窗
		// popupWindow.showAsDropDown(findViewById(R.id.show_notification), 100,
		// 200);
		popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER | Gravity.BOTTOM,0, 0);
	}
	
	//创建上下文菜单
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		switch( (Integer)v.getTag() ) {
		case 1:
			menu.setHeaderTitle("操作");
			menu.setHeaderIcon(R.drawable.a006);
			menu.add(0, 1, 0, "复制");
			menu.add(0, 2, 0, "粘贴");
			menu.add(0, 3, 0, "剪切");
			SubMenu subMenu = menu.addSubMenu(0,4, 0,"选择性别");
			subMenu.add(1, 21, 0, "男").setChecked(genderChecked[0]);
			subMenu.add(1, 22, 0, "女").setChecked(genderChecked[1]);
			subMenu.setGroupCheckable(1, true, false);
			break;
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	//上下文菜单的点击事件
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case 1: //点击复制
			//剪切板
			String text = textView.getText().toString();
			clipboardManager.setText(text); //写入到剪切板
			break;
		case 2: //点击粘贴
			String str = clipboardManager.getText().toString();
			textView.append(str);
			break;
		case 3: //点击剪切
			String text1 = textView.getText().toString();
			clipboardManager.setText(text1); //写入到剪切板
			textView.setText("******");
			break;
		case 4: //点击选择操作
			break;
		}
		return super.onContextItemSelected(item);
	}
	//上下文菜单关闭事件
	@Override
	public void onContextMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		super.onContextMenuClosed(menu);
	}
}
