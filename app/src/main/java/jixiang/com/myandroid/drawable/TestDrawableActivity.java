package jixiang.com.myandroid.drawable;

import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import jixiang.com.myandroid.R;

/**
 * 各种drawable讲解
 */

public class TestDrawableActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    @Bind(R.id.lv_drawable)
    ListView lvDrawable;
    List<Map<String,String>> data=new ArrayList<>();

    String[] functions={"BitmapDrawable","LayerDrawable","StateListDrawable","LevelListDrawable","TransitionDrawable"
    ,"InsertDrawable", "ClipDrawable","SelfDrawable"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_drawable);
        ButterKnife.bind(this);
        initData();
        SimpleAdapter adapter=new SimpleAdapter(this,data,R.layout.only_textview, new String[]{"function"},new int[]{R.id.tv_textview});
        lvDrawable.setAdapter(adapter);
        lvDrawable.setOnItemClickListener(this);

    }

    private void initData() {
        for (int i = 0; i < functions.length; i++) {
            HashMap<String,String> map=new HashMap<>();
            map.put("function",functions[i]);
            data.add(map);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=null;
        switch (functions[position]){
            case "BitmapDrawable"://图像
                intent=new Intent(this,TestBitmapDrawable.class);
                break;
            case "LayerDrawable"://图层drawable
                intent=new Intent(this,TestLayerDrawable.class);
                break;
            case "StateListDrawable"://状态drawable
                intent=new Intent(this,TestStateListDrawable.class);
                break;
            case "LevelListDrawable"://等级drawable
                intent=new Intent(this,TestLevelListDrawable.class);
                break;
            case "TransitionDrawable"://带透明度的图层drawable
                intent=new Intent(this,TestTransitionDrawable.class);
                break;
            case "InsertDrawable"://将一个drawable嵌入到另一个drawable内
                intent=new Intent(this,TestInsertDrawable.class);
                break;
            case "ClipDrawable"://对一个drawable进行裁剪
                intent=new Intent(this,TestClipDrawable.class);
                break;
            case "SelfDrawable"://自定义drawable
                intent=new Intent(this,TestSelfDrawable.class);
                break;

        }
        if(intent!=null){
            startActivity(intent);
        }
    }
}
