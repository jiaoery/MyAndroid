package jixiang.com.myandroid.exam;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.dialog.CustomAlertDialog;

public class ShowGridActivity extends Activity {
	GridView gridView;
	ArrayList<ImageBean> list = new ArrayList<ImageBean>();
	ExamGridAdapter adapter;

	public static class ImageBean implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int imageId;
		public String description;

		public ImageBean() {
		}

		public ImageBean(int imageId, String description) {
			this.imageId = imageId;
			this.description = description;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exam_gridview);
		gridView = (GridView) findViewById(R.id.exam_gridview);
		for (int i = 0; i < 20; i++) {
			ImageBean bean = null;
			if (i % 3 + 1 == 1) {
				bean = new ImageBean(R.drawable.image1, "这是一张图片" + i);
			} else if (i % 3 + 1 == 2) {
				bean = new ImageBean(R.drawable.image2, "这是一张图片" + i);
			} else if (i % 3 + 1 == 3) {
				bean = new ImageBean(R.drawable.image3, "这是一张图片" + i);
			}
			list.add(bean);
		}
		adapter = new ExamGridAdapter(this, list);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new MyItemClickListener());
	}

	class MyItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			ImageBean bean = list.get(position);
			showImageDialog(bean);
		}
	}

	public void showImageDialog(final ImageBean bean) {
		CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(this);
		builder.setTitle("查看图片");
		builder.setIcon(R.drawable.a005);
		builder.setCancelable(true);
		ImageView imageView = new ImageView(this);
		imageView.setBackgroundResource(bean.imageId);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(ShowGridActivity.this, bean.description,
						Toast.LENGTH_SHORT).show();
			}
		});
		builder.setView(imageView);
		builder.setPositiveButton("确定", null);
		builder.setNegativeButton("取消", null);
		builder.setNeutralButton("中间", null);
		builder.show();
	}
}
