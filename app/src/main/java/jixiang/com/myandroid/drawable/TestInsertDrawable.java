package jixiang.com.myandroid.drawable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jixiang.com.myandroid.R;

/**
 * InsetDrawable 表示一个drawable嵌入到另外一个drawable内部，
 * 并且在内部留一些间距，这一点很像drawable的padding属性，
 * 区别在于 padding表示drawable的内容与drawable本身的边距，
 * insetDrawable表示两个drawable和容器之间的边距。
 * 当控件需要的背景比实际的边框小的时候比较适合使用InsetDrawable。
 */
public class TestInsertDrawable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_insert_drawable);
    }
}
