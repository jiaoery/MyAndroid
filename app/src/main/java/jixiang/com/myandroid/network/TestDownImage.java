package jixiang.com.myandroid.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.FragmentActivity;
import android.widget.ImageView;

import jixiang.com.myandroid.R;
import jixiang.com.myandroid.http.DownListener;
import jixiang.com.myandroid.http.DownLoadManager;


public class TestDownImage extends FragmentActivity{
	ImageView imageView;
	String target;
	LayerDrawable layerDrawable;
	ClipDrawable clipDrawable;
	Bitmap bitmap;
	int width; //图像的宽度
	int height; //图像高度
	Bitmap bitmap1;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_down_image);
		imageView = (ImageView) findViewById(R.id.image);
		layerDrawable = (LayerDrawable) imageView.getBackground();
		clipDrawable = (ClipDrawable) layerDrawable.getDrawable(1);
		target = "/sdcard/beauty.jpg";
		String url = "http://h.hiphotos.baidu.com/image/pic/item/9a504fc2d5628535aabed46795ef76c6a7ef631d.jpg";
		new DownLoadManager(url, target, new DownListener() {
			@Override
			public void onStartDownLoad() {
				System.out.println("onStartDownLoad");
			}
			@Override
			public void onDownloadCompleted(String fileName) {
				System.out.println("onDownloadCompleted");
				handler.sendEmptyMessage(123);
			}
			@Override
			public void onCompleteRateChanged(int completeRate) {
				System.out.println("onCompleteRateChanged completeRate=" + completeRate);
				Message message = new Message();
				message.what = 124;
				message.arg1= completeRate;
				handler.sendMessage(message);
			}
		}).singleDownload(); //使用单线程来下载
	}
	Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch(msg.what) {
			case 124:
				if(clipDrawable != null) {
					int progress = msg.arg1;
					clipDrawable.setLevel(progress * 100);
				}
				/*try {
					int progress = msg.arg1;
					InputStream inputStream = new FileInputStream(target);
					int length = inputStream.available();
					System.out.println("length=" + length);
					byte[] buffer = new byte[length];
					inputStream.read(buffer, 0, length);
					//System.out.println(new String(buffer));
					Drawable drawable = imageView.getDrawable();
					if(drawable != null) {
						BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
						bitmap = bitmapDrawable.getBitmap();
					}

					//Bitmap bitmap2 = BitmapFactory.decodeByteArray(buffer, 0, length);
					Rect rect = new Rect();
					rect.left = 0;
					rect.right = 538;
					rect.top =0;
					rect.bottom = (progress * 800) /100; 
					Bitmap bitmap2 = BitmapRegionDecoder.newInstance(target, false).decodeRegion(rect, null);
					if(bitmap2 != null) {
						//System.out.println("bitmap2 != null");
						int width = bitmap2.getWidth();
						int height = bitmap2.getHeight();
						System.out.println("width=" + width + "height=" + height);
						imageView.setImageBitmap(bitmap2);
					}else {
						//System.out.println("bitmap2 == null");
					}
					if(bitmap != null && !bitmap.isRecycled()){
						bitmap.recycle();
						bitmap = null;
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				break;
			case 123:
				//先转换成Bitmap
				bitmap = BitmapFactory.decodeFile(target);
				//imageView.setImageBitmap(bitmap);
				width = bitmap.getWidth();
				height = bitmap.getHeight();
				new MyThread(height).start();
				break;
			case 125:
				Bitmap bitmapTmp= null;
				Drawable drawable = imageView.getDrawable();
				if(drawable != null) {
					BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
					bitmapTmp = bitmapDrawable.getBitmap();
				}
				//设置新的位图
				imageView.setImageBitmap(bitmap1);
				if(bitmapTmp != null && !bitmapTmp.isRecycled()) {
					bitmapTmp.recycle();
					bitmapTmp = null;
					bitmap1 = null;
				}
				break;
			}
			return true;
		}
	});
	
	class MyThread extends Thread{
		int line;
		
		public MyThread(int line) {
			this.line = line;
		}
		
		@Override
		public void run() {
			for(int i=0; i< height; i+=10) {
				//获取一行像素
				int[] pixels = new int[(i+1)*width];
				bitmap.getPixels(pixels, 0, width, 0, 0, width, i+1);
				bitmap1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
				bitmap1.setPixels(pixels, 0, width, 0, 0, width, i);
				handler.sendEmptyMessage(125);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			bitmap1 = bitmap;
			handler.sendEmptyMessage(125);
		}
	}
}
