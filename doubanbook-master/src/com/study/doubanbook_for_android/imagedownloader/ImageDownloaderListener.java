package com.study.doubanbook_for_android.imagedownloader;

import android.graphics.Bitmap;
import android.view.View;

public interface ImageDownloaderListener {
	public void onFailure(Exception e);
	/**
	 * 图像下载完成，如果返回false，会把下载的图像默认设置到ImageView。否则，不作处理。
	 * @param view   为其下载完图像的Viwe
	 * @param bm  下载的图像
	 * @return
	 */
	public boolean onDownloadFinished(View view, Bitmap bitmap);
}
