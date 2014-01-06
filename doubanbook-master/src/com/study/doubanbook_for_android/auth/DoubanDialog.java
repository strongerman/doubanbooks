package com.study.doubanbook_for_android.auth;

import com.study.doubanbook_for_android.utils.UriUtils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * 用来显示用户认证界面的dialog，封装了一个webview，通过redirect地址中的参数来获取accesstoken
 */
public class DoubanDialog extends Dialog {

	private DoubanOAuthListener mListener;
	private ProgressDialog mSpinner;
	private WebView mWebView;
	private RelativeLayout webViewContainer;
	private RelativeLayout mContent;

	private final static String TAG = "Douban-WebView";

	private static int theme = android.R.style.Theme_Translucent_NoTitleBar;

	private String authUrl;

	public DoubanDialog(Context context, String authUrl,
			DoubanOAuthListener listener) {
		super(context, theme);
		this.authUrl = authUrl;
		this.mListener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSpinner = new ProgressDialog(getContext());
		mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mSpinner.setMessage("Loading...");
		mSpinner.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				// onBack();
				return false;
			}

		});
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL,
				0);
		mContent = new RelativeLayout(getContext());
		setUpWebView();

		addContentView(mContent, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	protected void onBack() {
		try {
			mSpinner.dismiss();
			if (null != mWebView) {
				mWebView.stopLoading();
				mWebView.destroy();
			}
		} catch (Exception e) {
		}
		dismiss();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setUpWebView() {
		webViewContainer = new RelativeLayout(getContext());
		mWebView = new WebView(getContext());
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new DoubanDialog.DoubanWebViewClient());
		mWebView.loadUrl(authUrl);
		FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		mWebView.setLayoutParams(FILL);
		mWebView.setVisibility(View.INVISIBLE);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		mContent.setBackgroundColor(Color.WHITE);

		webViewContainer.addView(mWebView, lp);
		webViewContainer.setGravity(Gravity.CENTER);
		mContent.addView(webViewContainer, lp);
	}

	private class DoubanWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.d(TAG, "Redirect URL: " + url);
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			mListener.onError(new DoubanDialogError(description, errorCode,
					failingUrl));
			DoubanDialog.this.dismiss();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.d(TAG, "onPageStarted URL: " + url);
			if (url.startsWith(Douban.redirecturl)) {
				handleRedirectUrl(view, url);
				view.stopLoading();
				DoubanDialog.this.dismiss();
				return;
			}
			super.onPageStarted(view, url, favicon);
			mSpinner.show();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			Log.d(TAG, "onPageFinished URL: " + url);
			super.onPageFinished(view, url);
			if (mSpinner.isShowing()) {
				mSpinner.dismiss();
			}
			mWebView.setVisibility(View.VISIBLE);
		}

		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			handler.proceed();
		}

	}

	private void handleRedirectUrl(WebView view, String url) {
		Bundle values = UriUtils.parseUrl(url);

		String error = values.getString("error");
		String error_code = values.getString("error_code");
		if (error == null && error_code == null) {
			final String autorization_code = values.getString("code");
			Log.d(TAG, "the autorization_code :" + autorization_code);

			new Thread() {
				@Override
				public void run() {
					try {
						DoubanParameters data = new DoubanParameters();
						data.add("client_id", Douban.app_key);
						data.add("client_secret", Douban.secret);
						data.add("redirect_uri", Douban.redirecturl);
						data.add("grant_type", "authorization_code");
						data.add("code", autorization_code);
						Token token = DoubanRequest.requestAccessToken(data);
						if (token != null) {
							mListener.onComplete(token);
						} else {
							Log.d(TAG, "Get access token failure!");
						}
					} catch (Exception e) {
						Log.e(TAG, "Get access token failure :", e);
					}
				}
			}.start();

		} else if (error.equals("access_denied")) {
			// 用户或授权服务器拒绝授予数据访问权限
			mListener.onCancel();
		} else {
			if (error_code == null) {
				mListener.onDoubanException(new DoubanException(error, 0));
			} else {
				mListener.onDoubanException(new DoubanException(error, Integer
						.parseInt(error_code)));
			}

		}
	}

}
