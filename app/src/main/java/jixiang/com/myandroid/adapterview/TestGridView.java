package jixiang.com.myandroid.adapterview;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import jixiang.com.myandroid.R;

public class TestGridView extends Activity{
	
	int[] images = {
			R.drawable.fruit_apple,
			R.drawable.fruit_little_apple,
			R.drawable.fruit_big_apple,
			R.drawable.fruit_banana,
			R.drawable.fruit_orange,
			R.drawable.fruit_peach,
			R.drawable.fruit_pear};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_gridview);
		
		GridView gridView = (GridView) findViewById(R.id.gridview);
		
		String[] fruitTitles = getResources().getStringArray(R.array.fruit_titles);
		List<TestSpinnerActivity.FruitBean> list = new ArrayList<TestSpinnerActivity.FruitBean>();
		for(int i=0; i < images.length && i < fruitTitles.length; i++) {
			TestSpinnerActivity.FruitBean bean = new TestSpinnerActivity.FruitBean();
			bean.title = fruitTitles[i];
			bean.iconId = images[i];
			list.add(bean);
		}
		
		FruitAdapter adapter = new FruitAdapter(this, list);
		gridView.setAdapter(adapter);
		
	}
	
}
