package jixiang.com.myandroid.adapterview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;

import jixiang.com.myandroid.R;


public class TestSpinnerActivity extends Activity{
	
	public static class FruitBean{
		public String title;
		public int iconId;
	}
	int[] images = {
			R.drawable.fruit_apple,
			R.drawable.fruit_little_apple,
			R.drawable.fruit_big_apple,
			R.drawable.fruit_banana,
			R.drawable.fruit_orange,
			R.drawable.fruit_peach,
			R.drawable.fruit_pear};
	Spinner spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_spinner);
		spinner = (Spinner) findViewById(R.id.spinner);
		String[] fruitTitles = getResources().getStringArray(R.array.fruit_titles);
		List<FruitBean> list = new ArrayList<FruitBean>();
		for(int i=0; i < images.length && i < fruitTitles.length; i++) {
			FruitBean bean = new FruitBean();
			bean.title = fruitTitles[i];
			bean.iconId = images[i];
			list.add(bean);
		}
		SpinnerAdapter adapter = new SpinnerAdapter(this, list);
		spinner.setAdapter(adapter);
		spinner.getSelectedItem();
		Toast.makeText(TestSpinnerActivity.this, "You selected " + spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
	}
}
