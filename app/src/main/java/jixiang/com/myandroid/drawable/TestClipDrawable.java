package jixiang.com.myandroid.drawable;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jixiang.com.myandroid.R;

/**
 * ClipDrawable 是对一个Drawable进行剪切操作，
 * 可以控制这个drawable的剪切区域，以及相相对于容器的对齐方式，
 * android中的进度条就是使用一个ClipDrawable实现效果的，
 * 它根据level的属性值，决定剪切区域的大小。
 */
public class TestClipDrawable extends AppCompatActivity {
    @Bind(R.id.iv_clip)
    ImageView clip;
    @Bind(R.id.btn_plus)
    Button btnPlus;
    @Bind(R.id.btn_minus)
    Button btnMinus;
    @Bind(R.id.tv_level)
    TextView tvLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_clip_drawable);
        ButterKnife.bind(this);
        ClipDrawable drawable = (ClipDrawable) clip.getDrawable();
        int level=drawable.getLevel();
        tvLevel.setText("当前level为："+level);
    }

    @OnClick(R.id.btn_plus)
    public void plus(){
        //level最大10000
        ClipDrawable drawable = (ClipDrawable) clip.getDrawable();
        int level=(drawable.getLevel()+1000)%10001;
        clip.setImageLevel(level);
        tvLevel.setText("当前level为："+level);
    }

    @OnClick(R.id.btn_minus)
    public void minus(){
        ClipDrawable drawable = (ClipDrawable) clip.getDrawable();
        int level=(drawable.getLevel()-1000)%10001;
        if(level<0){
            level=10000;
        }
        clip.setImageLevel(level);
        tvLevel.setText("当前level为："+level);
    }
}
