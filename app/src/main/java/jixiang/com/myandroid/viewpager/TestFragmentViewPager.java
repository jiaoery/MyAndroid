package jixiang.com.myandroid.viewpager;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import android.view.View;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.fragment.FragmentAsView;
import jixiang.com.myandroid.fragment.GroupFragment;
import jixiang.com.myandroid.fragment.QQBottomFragment;
import jixiang.com.myandroid.fragment.TestFragmentManager;

public class TestFragmentViewPager extends FragmentActivity implements QQBottomFragment.OnQQBottomClickListener {
	ViewPager viewPager;
	QQBottomFragment bottomFragment;
	List<Fragment> list = new ArrayList<Fragment>();
	Fragment chatFragment;
	Fragment friendFragment;
	Fragment groupFragment;
	Fragment dynamicFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_fragment_view_pager);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		//getSupportFragmentManager  FragmentActivity
		//getFragmentManager Activity android 3.0之后使用
		bottomFragment = (QQBottomFragment) getSupportFragmentManager().findFragmentById(R.id.bottom);
		bottomFragment.setQQBottomClickListener(this);
		chatFragment = new TestFragmentManager.ChatFragment();
		friendFragment = new FragmentAsView();
		groupFragment = new GroupFragment();
		dynamicFragment = new TestFragmentManager.DynamicFragment();
		list.add(chatFragment);
		list.add(friendFragment);
		list.add(groupFragment);
		list.add(dynamicFragment);
		//组装数据源
		MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), list);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			/**
			 * 某页选中的时候回调这个方法
			 */
			@Override
			public void onPageSelected(int position) {
				bottomFragment.setSelected(position);
			}
			
			/*
			 * 在滚动中(non-Javadoc)
			 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
			 * position 位置
			 * positionOffset 位置的偏移量
			 * positionOffsetPixels 位置偏移量的像素
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				
			}
			
			//滚动的状态改变
			@Override
			public void onPageScrollStateChanged(int state) {
				switch(state) {
				case ViewPager.SCROLL_STATE_IDLE: //空闲状态
					break;
				case ViewPager.SCROLL_STATE_DRAGGING: //拖拽状态
					break;
				case ViewPager.SCROLL_STATE_SETTLING: //设置状态
					break;
				}
			}
		});
	}

	@Override
	public void onBottomClick(View v, int position) {
		switch(position) {
		case QQBottomFragment.CHAT_POSITION:
			viewPager.setCurrentItem(0, true);
			break;
		case QQBottomFragment.FRIEND_POSITION:
			viewPager.setCurrentItem(1, true);
			break;
		case QQBottomFragment.GROUP_POSITION:
			viewPager.setCurrentItem(2, true);
			break;
		case QQBottomFragment.DYNAMIC_POSITION:
			viewPager.setCurrentItem(3, true);
			break;
		}
	}
	
	class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		List<Fragment> list;

		public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public Fragment getItem(int position) {
			return list.get(position);
		}

		@Override
		public int getCount() {
			return list.size();
		}
	}

}
