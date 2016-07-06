package jixiang.com.myandroid.adapterview;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;

import jixiang.com.myandroid.R;

public class TestGallery extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_gallery);
		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		
		//封装数据源
		Integer[] datasource = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
		
		MyGalleryAdapter adapter = new MyGalleryAdapter(this, datasource);
		
		gallery.setAdapter(adapter);
		
		//选中某个项的事件
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				System.out.println("position=" + position);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
