package jixiang.com.myandroid.drawable;

import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jixiang.com.myandroid.R;

public class TestLevelListDrawable extends AppCompatActivity {

    @Bind(R.id.imageview)
    ImageView imageview;
    @Bind(R.id.btn_plus)
    Button btnPlus;
    @Bind(R.id.btn_minus)
    Button btnMinus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_level_list_drawable);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_plus)
    public void plus(){
        LevelListDrawable levelListDrawable= (LevelListDrawable) imageview.getDrawable();
        int level=(levelListDrawable.getLevel()+10)%40;
        imageview.setImageLevel(level);
    }

    @OnClick(R.id.btn_minus)
    public void minus(){
         LevelListDrawable levelListDrawable= (LevelListDrawable) imageview.getDrawable();
        int level=(levelListDrawable.getLevel()-10)%40;
        imageview.setImageLevel(level);
    }
}
