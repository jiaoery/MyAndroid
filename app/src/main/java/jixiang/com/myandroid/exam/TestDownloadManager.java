package jixiang.com.myandroid.exam;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.utils.ApkUtils;

public class TestDownloadManager extends AppCompatActivity {

    DownLoadCompleteReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_download_manager);
        //注册receiver
        receiver = new DownLoadCompleteReceiver();
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, filter);

    }

    public void checkForUpdate(View view) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk"));
        //设置在什么网络情况下进行下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("下载");
        request.setDescription("今日头条正在下载");
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, Environment.getExternalStorageDirectory().getPath());
        //打开系统的下载服务
        DownloadManager downManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        //开始下载任务
        Long id = downManager.enqueue(request);
    }


    //下载完毕时触发通知
    private class DownLoadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Toast.makeText(TestDownloadManager.this, "编号：" + id + "的下载任务已经完成！", Toast.LENGTH_SHORT).show();
                DownloadManager downManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                String path = downManager.getUriForDownloadedFile(id).getEncodedPath();
                ApkUtils.install(TestDownloadManager.this, new File(path));
            } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                Toast.makeText(TestDownloadManager.this, "通知栏被点击", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
