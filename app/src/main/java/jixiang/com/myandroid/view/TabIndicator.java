package jixiang.com.myandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import jixiang.com.myandroid.R;


public class TabIndicator extends LinearLayout implements OnClickListener{
	View view;
	LinearLayout tabLayout1;
	LinearLayout tabLayout2;
	LinearLayout tabLayout3;
	LinearLayout tabLayout4;
	OnTabClickListener tabClickListener;
	public static final int TAB1  =0;
	public static final int TAB2  = 1;
	public static final int TAB3  = 3;
	public static final int TAB4  = 4;
	

	/**
	 * 一个参数的构造器，适用于java代码动态生成控件时调用
	 * @param context 
	 */
	public TabIndicator(Context context) {
		this(context, null);
	}
	
	/**
	 * 两个参数的构造器是在xml中定义的布局，在setContentView(R.layout.XXX)之后
	 * 系统自动调用此构造器
	 * @param context 上下文
	 * @param attrs xml中控件的属性集合
	 */
	public TabIndicator(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * 三个参数的构造器是在xml中定义的布局，且传递了一个defStyle的一个样式资源
	 * @param context 上下文
	 * @param attrs xml中控件的属性集合
	 * @param defStyle 样式的资源ID
	 */
	public TabIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.tab_layout, this);
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA");
		tabLayout1 = (LinearLayout) view.findViewById(R.id.tablayout1);
		tabLayout2 = (LinearLayout) view.findViewById(R.id.tablayout2);
		tabLayout3 = (LinearLayout) view.findViewById(R.id.tablayout3);
		tabLayout4 = (LinearLayout) view.findViewById(R.id.tablayout4);
		tabLayout1.setOnClickListener(this);
		tabLayout2.setOnClickListener(this);
		tabLayout3.setOnClickListener(this);
		tabLayout4.setOnClickListener(this);
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	public void setOnTabClickListener(OnTabClickListener tabClickListener){
		this.tabClickListener = tabClickListener;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.tablayout1:
			tabLayout1.setBackgroundResource(R.drawable.tab_selected);
			tabLayout2.setBackgroundResource(R.drawable.tab_normal); 
			tabLayout3.setBackgroundResource(R.drawable.tab_normal); 
			tabLayout4.setBackgroundResource(R.drawable.tab_normal); 
			if(tabClickListener != null) {
				tabClickListener.onTabClick(tabLayout1, TAB1);
			}
			break;
		case R.id.tablayout2:
			tabLayout1.setBackgroundResource(R.drawable.tab_normal);
			tabLayout2.setBackgroundResource(R.drawable.tab_selected); 
			tabLayout3.setBackgroundResource(R.drawable.tab_normal); 
			tabLayout4.setBackgroundResource(R.drawable.tab_normal); 
			if(tabClickListener != null) {
				tabClickListener.onTabClick(tabLayout2, TAB2);
			}
			break;
		case R.id.tablayout3:
			tabLayout1.setBackgroundResource(R.drawable.tab_normal);
			tabLayout2.setBackgroundResource(R.drawable.tab_normal); 
			tabLayout3.setBackgroundResource(R.drawable.tab_selected); 
			tabLayout4.setBackgroundResource(R.drawable.tab_normal); 
			if(tabClickListener != null) {
				tabClickListener.onTabClick(tabLayout3,TAB3);
			}
			break;
		case R.id.tablayout4:
			tabLayout1.setBackgroundResource(R.drawable.tab_normal);
			tabLayout2.setBackgroundResource(R.drawable.tab_normal); 
			tabLayout3.setBackgroundResource(R.drawable.tab_normal); 
			tabLayout4.setBackgroundResource(R.drawable.tab_selected); 
			if(tabClickListener != null) {
				tabClickListener.onTabClick(tabLayout4,TAB4);
			}
			break;
		}
	}
	
}
