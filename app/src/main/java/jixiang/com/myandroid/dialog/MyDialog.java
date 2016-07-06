package jixiang.com.myandroid.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import jixiang.com.myandroid.R;

public class MyDialog extends Dialog implements View.OnClickListener{
	ImageView icon;
	TextView title;
	ImageView divider;
	TextView message;
	FrameLayout custom;
	Button button1;
	Button button3;
	Button button2;
	
	LinearLayout buttonPanel;
	
	boolean isButton1Show = false;
	boolean isButton2Show  =false;
	boolean isButton3Show = false;
	
	Context context;
	
	OnClickListener onClickListener1;
	OnClickListener onClickListener3;
	OnClickListener onClickListener2;
	
	int iconRes = 0;
	Drawable iconDrawable;
	CharSequence titleText;
	CharSequence messageText;
	CharSequence button1Text;
	CharSequence button3Text;
	CharSequence button2Text;
	
	public MyDialog(Context context) {
		this(context, R.style.MyDialog);
	}

	public MyDialog(Context context, int theme) {
		super(context, theme);
		this.context = context; 
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*Window dialogWindow = this.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER);
		lp.verticalMargin = 0;
		lp.horizontalMargin = 30;
		dialogWindow.setAttributes(lp);*/
		setContentView(R.layout.dialog);
		icon = (ImageView) findViewById(R.id.icon);
		title = (TextView) findViewById(R.id.alertTitle);
		divider = (ImageView) findViewById(R.id.titleDivider);
		message = (TextView) findViewById(R.id.message);
		custom = (FrameLayout) findViewById(R.id.custom);
		button1 = (Button) findViewById(R.id.button1);
		button3 = (Button) findViewById(R.id.button3);
		button2 = (Button) findViewById(R.id.button2);
		buttonPanel = (LinearLayout) findViewById(R.id.buttonPanel);
		//buttonPanel.setVisibility(View.GONE);
		button1.setVisibility(View.GONE);
		button3.setVisibility(View.GONE);
		button2.setVisibility(View.GONE);
		button1.setOnClickListener(this);
		button3.setOnClickListener(this);
		button2.setOnClickListener(this);
		if(iconRes != 0) {
			icon.setBackgroundResource(iconRes);
		}
		if(iconDrawable != null) {
			icon.setBackgroundDrawable(iconDrawable);
		}
		if(titleText != null) {
			title.setText(titleText);
		}
		if(messageText != null) {
			message.setText(messageText);
		}
		if(button1Text != null) {
			button1.setText(button1Text);
			button1.setVisibility(View.VISIBLE);
		}
		if(button3Text != null) {
			button3.setText(button3Text);
			button3.setVisibility(View.VISIBLE);
		}
		if(button2Text != null) {
			button2.setText(button2Text);
			button2.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button1:
			if(this.onClickListener1 != null) {
				onClickListener1.onClick(this, DialogInterface.BUTTON_NEGATIVE);
				this.dismiss();
			}
			break;
		case R.id.button3:
			if(this.onClickListener3 != null) {
				onClickListener3.onClick(this, DialogInterface.BUTTON_POSITIVE);
				this.dismiss();
			}
			break;
		case R.id.button2:
			if(this.onClickListener2 != null) {
				onClickListener2.onClick(this, DialogInterface.BUTTON_NEUTRAL);
				this.dismiss();
			}
			break;
		}
	}
	
	public void setIcon(int resId){
		//icon.setBackgroundResource(resId);
		iconRes = resId;
	}
	
	@SuppressWarnings("deprecation")
	public void setIcon(Drawable drawable) {
		//icon.setBackgroundDrawable(drawable);
		iconDrawable = drawable;
	}
	
	public void setTitle(int resId) {
		//title.setText(context.getResources().getString(resId));
		setTitle(context.getResources().getString(resId));
	}
	
	public void setTitle(CharSequence charSequence) {
		//title.setText(charSequence);
		titleText = charSequence;
	}

	public void setMessage(CharSequence charSequence){
		//message.setText(charSequence);
		messageText = charSequence;
	}
	
	public void setMessage(int resId) {
		setMessage(context.getResources().getString(resId));
	}

	public void setNegativeButton(CharSequence text, OnClickListener onClickListener){
		isButton1Show = true;
		//buttonPanel.setVisibility(View.VISIBLE);
		//button1.setVisibility(View.VISIBLE);
		//button1.setText(text);
		button1Text = text;
		if(onClickListener != null) {
			this.onClickListener1 = onClickListener;
		}
	}
	
	public void setNegativeButton(int  resId, OnClickListener onClickListener){
		setNegativeButton(context.getResources().getString(resId), onClickListener);
	}
	

	public void setPositiveButton(CharSequence text, OnClickListener onClickListener){
		isButton3Show = true;
		//buttonPanel.setVisibility(View.VISIBLE);
		//button3.setVisibility(View.VISIBLE);
		//button3.setText(text);
		button3Text = text;
		if(onClickListener != null) {
			this.onClickListener3 = onClickListener;
		}
	}
	
	public void setPositiveButton(int resId, OnClickListener onClickListener){
		setPositiveButton(context.getResources().getString(resId), onClickListener);
	}
	
	public void setNeutralButton(CharSequence text, OnClickListener onClickListener){
		isButton2Show = true;
		//buttonPanel.setVisibility(View.VISIBLE);
		//button2.setVisibility(View.VISIBLE);
		//button2.setText(text);
		button2Text = text;
		if(onClickListener != null) {
			this.onClickListener2 = onClickListener;
		}
	}

	public void setNeutralButton(int resId, OnClickListener onClickListener){
		setNeutralButton(context.getResources().getString(resId), onClickListener);
	}
	


}
