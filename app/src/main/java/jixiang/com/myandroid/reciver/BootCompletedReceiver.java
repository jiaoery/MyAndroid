package jixiang.com.myandroid.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;

import jixiang.com.myandroid.MainActivity;

/**
 * Created by 周念 on 2016/12/6.
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d("BootCompleted!!!");
        //do something you want here
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
