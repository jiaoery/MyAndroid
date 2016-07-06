package jixiang.com.myandroid.fragment;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

public class DetailsActivity extends Activity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
        	//如果是横屏，就直接结束掉自己
        	//也就是说，这个activity只能在竖屏的时候使用
            finish();
            return;
        }

        if (savedInstanceState == null) {
        	//setContentView(R.layout.shakspear_detail);
            // During initial setup, plug in the details fragment.
        	//新生成一个文章内容的碎片
            DetailsFragment details = new DetailsFragment();
            //设置这个碎片的参数，这个参数就是需要显示的文章的内容的下标
            details.setArguments(getIntent().getExtras());
            //将这个碎片添加到当前的activity之上
            View decorView = getWindow().getDecorView(); //获取到窗口的顶级ViewGroup容器
            View view = decorView.findViewById(android.R.id.content);
            if(view == null) {
            	System.out.println("AAAAAAAAAAAA");
            }else {
            	System.out.println("CCCCCCCCCCCCCCC");
            }
            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }

}
