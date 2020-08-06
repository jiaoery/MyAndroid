package jixiang.com.myandroid.network;


import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.widget.ImageView;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.networkbitmap.BitmapUtil;

public class TestBitmapUtil extends FragmentActivity{
	ImageView imageView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_down_image);
		imageView = (ImageView) findViewById(R.id.image); 
		//下载并显示图片
		BitmapUtil.getInstance().download("http://h.hiphotos.baidu.com/", "image/pic/item/9a504fc2d5628535aabed46795ef76c6a7ef631d.jpg", imageView);
	}
}
