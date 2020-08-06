package jixiang.com.myandroid.viewpager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import jixiang.com.myandroid.R;


public class TestViewPager extends Activity{
	ViewPager viewPager;
	List<View> list = new ArrayList<View>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_view_pager);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		View view = getLayoutInflater().inflate(R.layout.steps_relativelayout, null);
		View view1 = getLayoutInflater().inflate(R.layout.test_linearlayout, null);
		View view2 = getLayoutInflater().inflate(R.layout.test_button, null);
		list.add(view);
		list.add(view1);
		list.add(view2);
		MyPagerAdapter adapter = new MyPagerAdapter(list);
		viewPager.setAdapter(adapter);
	}
	class MyPagerAdapter extends PagerAdapter{
		List<View> list;
		public MyPagerAdapter(List<View> list) {
			this.list = list;
		}
		//返回多少页
		@Override
		public int getCount() {
			return list.size();
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}
		/*@Override
		public CharSequence getPageTitle(int position) {
			return super.getPageTitle(position);
		}*/
		//相当于adapterView里面的getView方法
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list.get(position));
			return list.get(position);
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(list.get(position));
		}
		
	}
}
