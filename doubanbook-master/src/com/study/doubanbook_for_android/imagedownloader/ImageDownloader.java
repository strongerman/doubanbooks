package com.study.doubanbook_for_android.imagedownloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * 先在内存缓存里面找，如果没有则在文件缓存里找，如果没有则下载，下载之后保存到文件缓存。
 * 
 * @author litingchang TODO 结构有点乱，整理
 */
public class ImageDownloader implements INetImageDownloader {

	private static final String	LOG_TAG	= ImageDownloader.class.getSimpleName();

	//	private ImageCache imageCache;
	ImageCache					imageCache;
	FileCache					fileCache;

	public ImageDownloader(Context context) {
		imageCache = new ImageCache();
		fileCache = new FileCache( context );
	}

	public ImageDownloader(Context context, File cacheDir) {
		imageCache = new ImageCache();
		fileCache = new FileCache( cacheDir );
	}

	/**
	 * Download the specified image from the Internet and binds it to the
	 * provided ImageView. The binding is immediate if the image is found in the
	 * cache and will be done asynchronously otherwise. A null bitmap will be
	 * associated to the ImageView if an error occurs.
	 * 
	 * @param url
	 *            The URL of the image to download.
	 * @param imageView
	 *            The ImageView to bind the downloaded image to.
	 */
	@Override
	public void download(String url, ImageView imageView, ImageDownloaderListener downloadListener) {
		Log.d( LOG_TAG, "Download images from : " + url );
		if (url == null || url == "")
			return;
		
		if(imageView == null)
			return;

		imageCache.resetPurgeTimer();
		Bitmap bitmap = imageCache.getBitmapFromCache( url );
		if (bitmap == null) {
			forceDownload( url, imageView, downloadListener );
		} else {
			cancelPotentialDownload( url, imageView );
			onDownloadFinished(imageView, bitmap, downloadListener);
		}
	}

	/**
	 * Same as download but the image is always downloaded and the cache is not
	 * used. Kept private at the moment as its interest is not clear.
	 * 
	 * @param downloadListener
	 */
	private void forceDownload(String url, ImageView imageView, ImageDownloaderListener downloadListener) {
		if (url == null) {
			//imageView.setImageDrawable(null);   //TODO 更改为默认图片
			return;
		}
		if (cancelPotentialDownload( url, imageView )) {
			BitmapDownloaderTask task = new BitmapDownloaderTask( imageView, downloadListener );
			@SuppressWarnings("unused")
			DownloadedDrawable dd = new DownloadedDrawable( task );
			// TODO Need enhance，这里会把imageView设置为黑色。需要改变机制。
			// 可用imageView.setTag()来标示BitmapDownloaderTask。
			imageView.setTag( task );
			// imageView.setImageDrawable( dd );
			task.execute( url );
		}
	}

	/**
	 * 取消潜在的下载
	 * 
	 * @param url
	 * @param imageView
	 * @return
	 */
	private static boolean cancelPotentialDownload(String url, ImageView imageView) {
		BitmapDownloaderTask bdt = getBitmapDownloaderTask( imageView );

		if (bdt != null) {
			String bitmapUrl = bdt.url;
			// 如果imageView正在下载并且url发生变化，则终止下载。
			if ((bitmapUrl == null) || (!bitmapUrl.equals( url ))) {
				bdt.cancel( true );
			} else {
				// The same URL is already being downloaded.
				return false;
			}
		}
		return true;
	}

	/**
	 * @param url
	 * @param downloadListener 
	 * @return
	 */
	private Bitmap downloadBitmap(String url, ImageDownloaderListener downloadListener) {

		//try to get image from file cache
		File file = fileCache.getFromFileCache( url );
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream( new FlushedInputStream( new FileInputStream( file ) ) );
		} catch (FileNotFoundException e1) {
			bitmap = null;
			if (downloadListener != null)
				downloadListener.onFailure( e1 );
		}
		if (bitmap != null) {
			return bitmap;
		}
		//end of try

		HttpClient httpClient = AndroidHttpClient.newInstance( "image_downloader" );
		//		final HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet( url );
		if(httpClient == null)
			Log.d( LOG_TAG, "错误: httpClient = null" );
		
		try {
			HttpResponse httpResponse = httpClient.execute( getRequest );
			final int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w( LOG_TAG, "Error " + statusCode + " while retrieving bitmap from " + url );
				return null;
			}

			final HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				InputStream inputStream = null;
				try {
					inputStream = httpEntity.getContent();

					//save file to file cache
					fileCache.addToFileCache( url, inputStream );
					//end of save
					//TODO 
					return BitmapFactory.decodeStream( new FlushedInputStream( new FlushedInputStream( new FileInputStream( file ) ) ) );
				} catch (Exception e) {
					e.printStackTrace();
					if (downloadListener != null)
						downloadListener.onFailure( e );
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					httpEntity.consumeContent();
				}
			}
		} catch (IOException e) {
			getRequest.abort();
			if (downloadListener != null)
				downloadListener.onFailure( e );
			Log.w( LOG_TAG, "I/O error while retrieving bitmap from " + url, e );
		} catch (IllegalStateException e) {
			getRequest.abort();
			if (downloadListener != null)
				downloadListener.onFailure( e );
			Log.w( LOG_TAG, "Incorrect URL: " + url );
		} catch (Exception e) {
			getRequest.abort();
			if (downloadListener != null)
				downloadListener.onFailure( e );
			Log.w( LOG_TAG, "Error while retrieving bitmap from " + url, e );
		} finally {
			if ((httpClient instanceof AndroidHttpClient)) {
				((AndroidHttpClient) httpClient).close();
			}
		}
		return null;
	} // end of downloadBitmap

	/*
	 * An InputStream that skips the exact number of bytes provided, unless it
	 * reaches EOF.
	 */
	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super( inputStream );
		}

		@Override
		public long skip(long n) throws IOException {
			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip( n - totalBytesSkipped );
				if (bytesSkipped == 0L) {
					int b = read();
					if (b < 0) {
						break; // we reached EOF
					} else {
						bytesSkipped = 1; // we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	} // end of FlushedInputStream

	/**
	 * @param imageView
	 *            Any imageView
	 * @return Retrieve the currently active download task (if any) associated
	 *         with this imageView. null if there is no such task.
	 */
	private static BitmapDownloaderTask getBitmapDownloaderTask(ImageView imageView) {
		return (BitmapDownloaderTask) imageView.getTag();		
	}

	class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

		private String						url;
		private WeakReference<ImageView>	wr;
		ImageDownloaderListener				downloadListener;

		public BitmapDownloaderTask(ImageView imageView, ImageDownloaderListener downloadListener) {
			wr = new WeakReference<ImageView>( imageView );
			this.downloadListener = downloadListener;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			url = params[0];
			return downloadBitmap( url, downloadListener );
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				bitmap = null;
			}

			//add bitmap to cache
			imageCache.addBitmapToCache( url, bitmap );

			if (wr != null) {
				ImageView imageView = wr.get();
				if(imageView != null){
					BitmapDownloaderTask bitmapDownloaderTask = (BitmapDownloaderTask) imageView.getTag();
					if (this == bitmapDownloaderTask) {
						onDownloadFinished(imageView, bitmap, downloadListener);
					}
				}
			}
		}
	} // end of BitmapDownloaderTask

	
	protected void onDownloadFinished(ImageView imageView, Bitmap bitmap, ImageDownloaderListener downloadListener ) {
		if (downloadListener != null) {
			if (!downloadListener.onDownloadFinished(imageView, bitmap )){
				if(bitmap != null)
					setImageBitmap(imageView, bitmap);					
			}
		} else {
			if(bitmap != null)
				setImageBitmap(imageView, bitmap);		
		}
	}
	
	
	
	
	
	
	
	protected void setImageBitmap(ImageView imageView, Bitmap bitmap) {
		imageView.setImageBitmap( bitmap );
		imageView.setVisibility( View.VISIBLE );
	}

	/**
	 * A fake Drawable that will be attached to the imageView while the download
	 * is in progress.
	 * 
	 * <p>
	 * Contains a reference to the actual download task, so that a download task
	 * can be stopped if a new binding is required, and makes sure that only the
	 * last started download process can bind its result, independently of the
	 * download finish order.
	 * </p>
	 */
	static class DownloadedDrawable extends ColorDrawable {
		private final WeakReference<BitmapDownloaderTask>	bitmapDownloaderTaskReference;

		public DownloadedDrawable(BitmapDownloaderTask bitmapDownloaderTask) {
			super( Color.TRANSPARENT );
			bitmapDownloaderTaskReference =
					new WeakReference<BitmapDownloaderTask>( bitmapDownloaderTask );
		}

		public BitmapDownloaderTask getBitmapDownloaderTask() {
			return bitmapDownloaderTaskReference.get();
		}
	} // end of DownloadedDrawable

}
