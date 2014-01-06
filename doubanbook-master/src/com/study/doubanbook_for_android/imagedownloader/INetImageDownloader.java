package com.study.doubanbook_for_android.imagedownloader;

import android.widget.ImageView;

public interface INetImageDownloader {

	void download(String url, ImageView imageView,
			ImageDownloaderListener downloadListener);

}
