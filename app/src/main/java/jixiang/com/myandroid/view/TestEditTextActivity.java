package jixiang.com.myandroid.view;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import jixiang.com.myandroid.R;

public class TestEditTextActivity extends Activity{
	EditText editText;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_edittext);
		editText = (EditText) findViewById(R.id.edittext);
		//添加输入框编辑动作的监听
		editText.setOnEditorActionListener(new OnEditorActionListener() {
			
			/**
			 * TextView 输入控件
			 * actionId 动作的Id， 也就是EditorInfo.IME_ACTION_NONE的对应的常量
			 * If triggered by an enter key, this is the event; otherwise, this is null.
			 * 如果给这个key设置了触发器，才会有事件，否则，为空
			 */
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				switch(actionId) {
				case EditorInfo.IME_ACTION_GO:
					//执行相应的动作响应事件
					System.out.println("AAAAAAAAAAAA");
					
					break;
				}
				return false;
			}
		});
		//给editText设置一个字符改变监听
		editText.addTextChangedListener(new TextWatcher() {
			/**
			 * 当字符串改变的时候回调此方法
			 * CharSequence 改变的字符串
			 * start 改变的开始位置
			 * before 偏移，也就是说前面的第几个
			 * count 字符串的改变个数
			 */
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				System.out.println("onTextChanged s=" + s + "====start=" + start + ",before=" + before + ", count=" + count);
				CharSequence charSequence = s.subSequence(start, start+ count);
				String str = charSequence.toString();
				Log.d("EditText", "onTextChanged s=" + s + "====start=" + start + ",before=" + before + ", count=" + count);
				if(str.contains("a")) {
					System.out.println("str=" + str);
					//字符串的任何操作，都会生成一个新的字符串,而原字符串并没有改变
					str = str.replaceAll("a", "*");
					//在设置之前需要去掉字符监听
					editText.removeTextChangedListener(this);
					//替换字符
					editText.getText().replace(start, start+ count, str);
					//重新设置监听
					editText.addTextChangedListener(this);
				}
			}
			/**
			 * 在字符串改变前回调此方法
			 * CharSequence 改变前的字符串
			 * start  改变的开始位置
			 * count 字符串的改变个数
			 * after 偏移量也就是后面第几个位置
			 */
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				System.out.println("beforeTextChanged s=" + s + "====start=" + start + ",after=" + after + ", count=" + count);
			}
			/**
			 * 字符改变完成
			 * 
			 */
			@Override
			public void afterTextChanged(Editable s) {
				System.out.println("afterTextChanged s=" + s);
			}
		});
	}
}
