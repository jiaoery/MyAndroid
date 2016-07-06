package jixiang.com.myandroid.adapterview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * 可以嵌套使用listview 和ScrollView
 * @author Administrator
 *
 */
public class MyListView extends ListView {
	public MyListView(Context context) {
		this(context, null);
	}

	public MyListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measure = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, measure);
	}
}
