/*
 * SimpleDoubanOAuthListener.java
 * classes : org.kevin.douban.auth.SimpleDoubanOAuthListener 
 * Created at : 2013-5-9 下午5:31:06
 */
package com.study.doubanbook_for_android.auth;

/**
 * A convenient class to extend when you only want to listen for a subset of all
 * the DoubanOAuth events. This implements all methods in the
 * {@link DoubanOAuthListener} but does nothing.
 * 
 */
public class SimpleDoubanOAuthListener implements DoubanOAuthListener {

	@Override
	public void onComplete(Token token) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDoubanException(DoubanException e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(DoubanDialogError e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}

}
