package jixiang.com.myandroid.drawable;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jixiang.com.myandroid.R;

/**
 *  在使用AnimationDrawable的时，需要主动调用startTransition方法启动两个层之间的切换动画，也可以调用reverseTransition方法启动逆向切换动画，它们都可以接受一个毫秒

 数，作为动画的持续时间
 */
public class TestTransitionDrawable extends AppCompatActivity {

    @BindView(R.id.iv_transition)
    ImageView ivTransition;
    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_transition_drawable);
        ButterKnife.bind(this);
        final TransitionDrawable drawable=(TransitionDrawable)(ivTransition.getBackground());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               drawable.startTransition(1000);
            }
        },1000);
    }
}
