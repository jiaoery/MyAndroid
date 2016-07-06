package jixiang.com.myandroid.service;
import jixiang.com.myandroid.service.OnGetSumListener;
interface MyServiceAIDL {
    //设置结果值的事件监听
    void setOnGetSumListener(com.three.androidlearning.service.OnGetSumListener listener);
    long onGetSum(); 
}