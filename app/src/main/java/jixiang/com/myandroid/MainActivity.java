package jixiang.com.myandroid;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import butterknife.OnItemClick;
import jixiang.com.myandroid.adapter.MainAdapter;
import jixiang.com.myandroid.view.OverScrollListView;

public class MainActivity extends BaseActivity {

    @Bind(R.id.auto_completedtextview)
    AutoCompleteTextView autoCompletedtextview;
    @Bind(R.id.listview)
    OverScrollListView listview;


    Map<String,String> map=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife 用于快速集成xml到activity中
        ButterKnife.bind(this);
        init();
        initDisplayMessage();
    }

    private void init() {
        //获取标题和对应的class信息
        String[] titles=getResources().getStringArray(R.array.main_titles);
        String[] mainclasses=getResources().getStringArray(R.array.main_classes);
        for(int i=0;i<titles.length;i++){
            map.put(titles[i],mainclasses[i]);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.only_textview, titles);
        autoCompletedtextview.setAdapter(arrayAdapter);
        MainAdapter adapter = new MainAdapter(titles, this);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new MyOnItemClickListener());
        autoCompletedtextview.setOnItemClickListener(new MyOnItemClickListener());
    }




    @OnEditorAction(R.id.auto_completedtextview)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        String name = v.getText().toString();
        jump(name);
        return true;
    }


    /**
     * 跳转到指定的activity
     * @param name
     */
    private void jump(String name) {
        String toclass = map.get(name);
        if(name.equals("EncryptDianry")){
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName("com.eibit.securitydinary", "com.eibit.securitydinary.QQDinaryHome");
            intent.setComponent(componentName);
            startActivity(intent);
        } else {
            if(toclass != null && toclass.length() > 0) {
                Class cls;
                try {
                    //反射
                    cls = Class.forName(toclass);
                    Intent intent= new Intent(this, cls);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "控件未找到!请重新搜索", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "控件未找到!请重新搜索", Toast.LENGTH_SHORT).show();
            }
        }
    }



    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if (parent.getAdapter() instanceof MainAdapter) {
                //jumpToOtherActivity(position);
                String toclass = parent.getItemAtPosition(position).toString();
                jump(toclass);
            } else if(parent.getAdapter() instanceof ArrayAdapter) {
                String toclass = parent.getItemAtPosition(position).toString();
                jump(toclass);
            }
        }

    }

    /**
     * 获取使用的手机设备屏幕信息
     */
    private void initDisplayMessage(){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        //获取屏幕的windowsmanager，并将设备屏幕信息储存在displayMetrics下
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Logger.d(displayMetrics.toString());

    }
}
