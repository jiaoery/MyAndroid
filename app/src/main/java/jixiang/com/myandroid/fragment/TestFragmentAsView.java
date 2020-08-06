package jixiang.com.myandroid.fragment;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import jixiang.com.myandroid.R;


public class TestFragmentAsView extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getFragmentManager().beginTransaction().add(android.R.id.content, new FragmentAsView()).commit();
		//setContentView(R.layout.test_fragment_asview);
		View view = getLayoutInflater().inflate(R.layout.test_fragment_asview, null);
		ViewGroup topView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
		topView.addView(view);
	}
}
