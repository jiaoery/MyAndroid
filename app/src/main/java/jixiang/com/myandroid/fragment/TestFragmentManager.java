package jixiang.com.myandroid.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jixiang.com.myandroid.R;


public class TestFragmentManager extends FragmentActivity implements QQBottomFragment.OnQQBottomClickListener {
	QQBottomFragment bottomFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_fragment_layout);
		Fragment fragment = new ChatFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("age", 20);
		fragment.setArguments(bundle);
		getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
		bottomFragment = (QQBottomFragment) getSupportFragmentManager().findFragmentById(R.id.bottom);
		bottomFragment.setQQBottomClickListener(this);
	}
	@Override
	public void onBottomClick(View v, int position) {
		switch(position) {
		case QQBottomFragment.CHAT_POSITION:
			Fragment fragment = new ChatFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("age", 20);
			fragment.setArguments(bundle);
			getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
			break;
		case QQBottomFragment.FRIEND_POSITION:
			getSupportFragmentManager().beginTransaction().replace(R.id.content, new FragmentAsView()).commit();
			break;
		case QQBottomFragment.GROUP_POSITION:
			getSupportFragmentManager().beginTransaction().replace(R.id.content, new GroupFragment()).commit();
			break;
		case QQBottomFragment.DYNAMIC_POSITION:
			getSupportFragmentManager().beginTransaction().replace(R.id.content, new DynamicFragment()).commit();
			break;
		}
	}
	public static class ChatFragment extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			System.out.println("ChatFragment===========================");
			//int age = getArguments().getInt("age", 0);
			//System.out.println("age=" + age);
			return inflater.inflate(R.layout.steps_relativelayout, null);
		}
	}
	public static class DynamicFragment extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			System.out.println("DynamicFragment===========================");
			return inflater.inflate(R.layout.car_rest_item, null);
		}
	}
}
