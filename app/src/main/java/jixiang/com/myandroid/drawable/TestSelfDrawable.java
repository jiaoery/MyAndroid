package jixiang.com.myandroid.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import jixiang.com.myandroid.R;

/**
 * 自己定义drawable的shader实现相应的部分功能
 */
public class TestSelfDrawable extends AppCompatActivity {

    @Bind(R.id.iv_self)
    ImageView ivSelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_self_drawable);
        ButterKnife.bind(this);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.dog);
        ivSelf.setImageDrawable(new CircleDrawable(bitmap));
    }
}
