package jixiang.com.myandroid.http;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Environment;
 
/**
 * 文件下载管理类
 */
public class DownLoadManager {
	private String path;
	private String targetFile;
	private DownListener downListener;
    /**
     * 每个线程下载的字节数
     */
    static final long unitSize = 1000 * 1024;
    
    private int threadCount;
    
    private long fileSize;
    
    private int oldRate = 0; 
    //下载线程数组
    private DownloadThread[] downloadThreads;
	
	public DownLoadManager(String path, String targetFile, DownListener downListener){
		this.path = path;
		this.targetFile = targetFile;
		this.downListener = downListener;
		File file = new File(Environment.getExternalStorageDirectory().getPath());
		if(file.exists()) {
			
		} else {
			file.mkdirs();
		}
	}
	
	/**
	 * 开启多线程下载
	 */
	public void download(){
		/*if(downListener != null) {
			downListener.onStartDownLoad();
		}
		new GetContentThread().start();*/
		if(downListener != null) {
			downListener.onStartDownLoad();
		}
		new SingleDownloadThread().start();
	}
	
	/**
	 * 单线程下载
	 */
	public void singleDownload(){
		if(downListener != null) {
			downListener.onStartDownLoad();
		}
		new SingleDownloadThread().start();
	}
	
	private class GetContentThread extends Thread {
		@Override
		public void run() {
			if(downListener != null) {
				downListener.onStartDownLoad();
			}
			doDownload(path, targetFile);
		}
	}
	
	/**
	 * 计算文件大小， 并开启线程下载
	 * @param path
	 * @param targetFile
	 */
	private void doDownload(String path, String targetFile){
		try {
			HttpURLConnection httpConnection = (HttpURLConnection) new URL(path).openConnection();
			httpConnection.setRequestMethod("HEAD");
			int responseCode = httpConnection.getResponseCode();
	        if(responseCode >= 400){
	            Logg.out("Web服务器响应错误!");
	            return;
	        }
	        String sHeader;
	        for(int i=1;;i++){
	            sHeader = httpConnection.getHeaderFieldKey(i);
	            if(sHeader != null && sHeader.equals("Content-Length")){
	                Logg.out("文件大小ContentLength:"+httpConnection.getContentLength());
	                fileSize = Long.parseLong(httpConnection.getHeaderField(sHeader));
	                break;
	            }
	        }
	        httpConnection.disconnect();
	        
	        //生成文件
	        File newFile = new File(targetFile);
	        RandomAccessFile raf = new RandomAccessFile(newFile, "rw");
	        raf.setLength(fileSize);
	        raf.close();
	        
	        //计算需要多少个线程，并开启线程下载
	        threadCount = (int) (fileSize / unitSize);
	        
	        Logg.out("共启动 " + (fileSize % unitSize == 0 ? threadCount : threadCount + 1) + " 个线程");
	        
	        if(fileSize > 20 * 1024* 1000) {//如果文件远大于20M， 建议使用单线程下载
	        	new SingleDownloadThread().start();
	        } else {
	        	 long offset = 0;
	 	        if (fileSize <= unitSize) {// 如果远程文件尺寸小于等于unitSize
	 	        	downloadThreads = new DownloadThread[1];
	 	        	downloadThreads[0] = new DownloadThread(path, targetFile, offset, fileSize);
	 	        	downloadThreads[0].start();
	 	        } else {// 如果远程文件尺寸大于unitSize
	 	        	if(fileSize % unitSize != 0) {
	 	        		downloadThreads = new DownloadThread[threadCount + 1];
	 	        	} else {
	 	        		downloadThreads = new DownloadThread[threadCount];
	 	        	}
	 	            for (int i = 0; i < threadCount; i++) {
	 	            	downloadThreads[i] = new DownloadThread( path, targetFile, offset, unitSize);
	 	            	downloadThreads[i].start();
	 	                offset = offset + unitSize;
	 	            }
	 	            if (fileSize % unitSize != 0) {// 如果不能整除，则需要再创建一个线程下载剩余字节
	 	            	downloadThreads[threadCount] = new DownloadThread(path, targetFile, offset, fileSize - unitSize * threadCount);
	 	            	downloadThreads[threadCount].start();
	 	            }
	 	        }
	        }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取下载百分比
	 * @return
	 */
	private int getDownloadRate(){
		long sum = 0;
		for(int i=0; i< downloadThreads.length; i++) {
			sum += downloadThreads[i].downloadedLength;
		}
		int rate = (int) (sum  * 100  / fileSize);
		return rate;
	}

	/**
	 * 判断是否全部下载完成
	 * @return
	 */
	private boolean isAllDownLoadCompleted() {
		boolean flg = true;
		for (int i = 0; i < downloadThreads.length; i++) {
			if (downloadThreads[i].isDownloadCompleted == false) {
				flg = false;
				break;
			}
		}
		return flg;
	}
	
	/**
	 * 单线程下载类
	 */
	private class SingleDownloadThread extends Thread {
		long length = 0;
		@Override
		public void run() {
			try {
				URL url = new URL(path);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(5 * 1000);
				connection.setRequestMethod("GET");
				connection.setRequestProperty(
								"Accept",
								"image/gif, image/jpeg, image/pjpeg, application/x-shockwave-flash," +
								" application/xaml+xml, application/vnd.ms-xpsdocument," +
								" application/x-ms-xbap, application/x-ms-app lication, " +
								"application/cnd.ms- excel, application/vnd.ms-powerpoint, " +
								"application/msword, */*");
				connection.setRequestProperty("Accept-Language", "zh_CN");
				connection.setRequestProperty("Charset", "UTF-8");
				connection.setRequestProperty(
								"User-Agent",
								"Mozilla/4.0(compatible; MSIE 7.0; Windows NT 5.2; " +
								"Trident/4.0; . NET CTR 1.1.4322; .NET CLR 2.0.50727; " +
								".NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; " +
								".NET CLR 3.5.30729)");
				connection.setRequestProperty("Connection", "Keep-Alive");
				connection.connect();
				InputStream inputStream = connection.getInputStream();
				
				//设置文件的长度
				//生成文件
		        File newFile = new File(targetFile);
		        RandomAccessFile raf = new RandomAccessFile(newFile, "rw");
		        raf.setLength(fileSize);
		        for(long i=0; i< fileSize; i++) {
		        	raf.seek(i);
		        	raf.write(0);
		        }
		        raf.close();
				
				
				OutputStream outputStream = new FileOutputStream(targetFile);
				long fileSize = connection.getContentLength();
				Logg.out("fileSize=" + connection.getContentLength());
				
				byte[] buffer = new byte[1024];
				int hasRead = -1;
				while ((hasRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
					outputStream.write(buffer, 0, hasRead);
					outputStream.flush();
					length += hasRead;
					int rate = (int) ((length * 100) / fileSize ) ;
					if(downListener != null && rate - oldRate >= 1) {
						oldRate = rate;
						downListener.onCompleteRateChanged(rate);
					}
				}
				inputStream.close();
				outputStream.close();
				connection.disconnect();
				if(downListener != null) {
					downListener.onDownloadCompleted(targetFile);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
    
    /**
     * 负责文件下载的类
     */
    private class DownloadThread extends Thread {
        /**
         * 待下载的文件
         */
        private String url = null;
     
        /**
         * 本地文件名
         */
        private String fileName = null;
     
        /**
         * 偏移量
         */
        private long offset = 0;
     
        /**
         * 分配给本线程的下载字节数
         */
        private long length = 0;
     
        /**
         * 已下载的字节数
         */
        protected long downloadedLength = 0;
        
        /**
         * 是否已下载完成
         */
        protected boolean isDownloadCompleted = false;
        
        /**
         * @param url 下载文件地址
         * @param fileName 另存文件名
         * @param offset 本线程下载偏移量
         * @param length 本线程下载长度
         *
         * */
        protected DownloadThread(String url, String file, long offset, long length) {
            this.url = url;
            this.fileName = file;
            this.offset = offset;
            this.length = length;
            Logg.out("偏移量=" + offset + ";字节数=" + length);
        }
     
        public void run() {
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) new URL(this.url).openConnection();
                httpConnection.setRequestMethod("GET");
                httpConnection.setRequestProperty("RANGE", "bytes=" + this.offset + "-" + (this.offset + this.length - 1));
                Logg.out("RANGE bytes=" + this.offset + "-" + (this.offset + this.length - 1));
                BufferedInputStream bis = new BufferedInputStream(httpConnection.getInputStream());
                byte[] buff = new byte[1024];
                int bytesRead;
                File newFile = new File(fileName);
                RandomAccessFile raf = new RandomAccessFile(newFile, "rw");
                while ((bytesRead = bis.read(buff, 0, buff.length)) != -1) {
                    raf.seek(this.offset);
                    raf.write(buff, 0, bytesRead);
                    this.offset = this.offset + bytesRead;
                    downloadedLength += bytesRead;
                }
                raf.close();
                bis.close();
                isDownloadCompleted = true;
                httpConnection.disconnect();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            if(downListener != null && getDownloadRate() -oldRate >=1) {
            	oldRate = getDownloadRate();
            	downListener.onCompleteRateChanged(getDownloadRate());
            }
            if(isAllDownLoadCompleted() && downListener != null) {
            	downListener.onDownloadCompleted(targetFile);
            }
        }
     
    }
}