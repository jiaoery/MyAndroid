package jixiang.com.myandroid.async;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ScrollView;
import android.widget.TextView;

public class TestAsyncTask extends FragmentActivity{

	ScrollView scrollView;
	TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		scrollView = new ScrollView(this);
		textView = new TextView(TestAsyncTask.this);
		scrollView.addView(textView);
		setContentView(scrollView);
		DownloadFilesTask task = new DownloadFilesTask();
		task.setOnDownLoadComplete(new DownloadFilesTask.OnDownLoadComplete() {
			
			@Override
			public void publishProgress(Integer... progress) {
				System.out.println("onProgressUpdate ");
			}
			
			@Override
			public void postResult(String result) {
				System.out.println("onPostExecute result=" + result);
				textView.setText(result);
			}
		});
		task.execute("http://openapk.hk2008.8ahost.com/exam/api/create_exam.php?e_id=16&s_id=222007301");
	}
}
