package com.study.doubanbook_for_android.auth;

import com.study.doubanbook_for_android.utils.UriUtils;

import android.Manifest;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * douban for basic oath
 */
public class Douban {

	private static Douban mDoubanInstance = null;
	public static String URL_OAUTH2_ACCESS_AUTHORIZE = "https://www.douban.com/service/auth2/auth";
	public static String URL_OAUTH2_ACCESS_TOKEN = "https://www.douban.com/service/auth2/token";
	public static String app_key = "012716b9167db8c30375442b43792a49";// 第三方应用的appkey
	public static String secret = "0c3826641a1fd905";// 第三方应用的appkey
	public static String redirecturl = "http://developers.douban.com/";// 重定向url
	public static String scope = "book_basic_r,book_basic_w,douban_basic_common";
	public static final String KEY_TOKEN = "access_token";
	public static final String KEY_EXPIRES = "expires_in";
	public static final String KEY_REFRESHTOKEN = "refresh_token";
	public static final String KEY_USERID = "douban_user_id";
	public static final String KEY_GRANT_TYPE = "grant_type";
	public static final String KEY_CLIENT_SECRET = "client_secret";

	public AccessToken accessToken = null;// AccessToken实例

	private Douban() {
	}

	/**
	 * 
	 * @param appKey
	 *            第三方应用的appkey
	 * @param redirectUrl
	 *            第三方应用的回调页
	 * @return Douban的实例
	 */
	public synchronized static Douban getInstance(String appKey,
			String clientSecret, String redirectUrl, String doubanScope) {
		if (mDoubanInstance == null) {
			mDoubanInstance = new Douban();
		}
		app_key = appKey;
		secret = clientSecret;
		redirecturl = redirectUrl;
		scope = doubanScope;
		return mDoubanInstance;
	}

	public synchronized static Douban getInstance() {
		if (mDoubanInstance == null) {
			mDoubanInstance = new Douban();
		}
		return mDoubanInstance;
	}

	/**
	 * 设定第三方使用者的appkey和重定向url
	 * 
	 * @param appKey
	 *            第三方应用的appkey
	 * @param redirectUrl
	 *            第三方应用的回调页
	 */
	public void setupConsumerConfig(String appKey, String clientSecret,
			String redirectUrl, String doubanScope) {
		app_key = appKey;
		secret = clientSecret;
		redirecturl = redirectUrl;
		scope = doubanScope;
	}

	/**
	 * 
	 * 进行Douban认证
	 * 
	 * @param activity
	 *            调用认证功能的Context实例
	 * @param listener
	 *            DoubanOAuthListener Douban认证的回调接口
	 */
	public void authorize(Context context, DoubanOAuthListener listener) {
		startAuthDialog(context, listener);
	}

	public void startAuthDialog(final Context context,
			final DoubanOAuthListener listener) {

		showDialog(context, new DoubanOAuthListener() {
			@Override
			public void onComplete(Token token) {
				// ensure any cookies set by the dialog are saved
				if (null == accessToken) {
					accessToken = new AccessToken();
				}
				accessToken.setToken(token.access_token);
				accessToken.setExpiresIn(String.valueOf(token.expires_in));
				accessToken.setRefreshToken(token.refresh_token);
				accessToken.setDoubanUserId(token.douban_user_id);
				if (accessToken.isSessionValid()) {
					Log.d("Douban-authorize", "Login Success! access_token="
							+ accessToken.getToken() + " expires="
							+ accessToken.getExpiresTime() + " refresh_token="
							+ accessToken.getRefreshToken());
					KeepToken.keepAccessToken(context, accessToken);
					listener.onComplete(token);
				} else {
					Log.d("Douban-authorize", "Failed to receive access token");
					listener.onDoubanException(new DoubanException(
							"Failed to receive access token."));
				}
			}

			@Override
			public void onError(DoubanDialogError error) {
				Log.d("Douban-authorize", "Login failed: " + error);
				listener.onError(error);
			}

			@Override
			public void onDoubanException(DoubanException error) {
				Log.d("Douban-authorize", "Login failed: " + error);
				listener.onDoubanException(error);
			}

			@Override
			public void onCancel() {
				Log.d("Douban-authorize", "Login canceled");
				listener.onCancel();
			}
		});
	}

	public void showDialog(final Context context,
			final DoubanOAuthListener listener) {
		if (context.checkCallingOrSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
			showAlert(context, "Error",
					"Application requires permission to access the Internet");
			return;
		}
		accessToken = KeepToken.readAccessToken(context);
		DoubanParameters params = new DoubanParameters();
		params.add("client_id", app_key);
		params.add("redirect_uri", redirecturl);

		if (accessToken != null && accessToken.isSessionValid()) {
			params.add(KEY_REFRESHTOKEN, accessToken.getRefreshToken());
			params.add(KEY_GRANT_TYPE, "refresh_token");
			params.add(KEY_CLIENT_SECRET, secret);
			exchangeToken(context, params, listener);
		} else {
			params.add("response_type", "code");
			params.add("scope", scope);
			String authUrl = URL_OAUTH2_ACCESS_AUTHORIZE + "?"
					+ UriUtils.encodeUrl(params);
			new DoubanDialog(context, authUrl, listener).show();
		}
	}

	public void exchangeToken(final Context context,
			final DoubanParameters params, final DoubanOAuthListener listener) {
		new Thread() {
			public void run() {
				Token token = DoubanRequest.requestAccessToken(params);
				if (token != null) {
					listener.onComplete(token);
				} else {
					listener.onDoubanException(new DoubanException(
							"request failure !"));
				}
			}
		}.start();
	}

	public void showAlert(Context context, String title, String text) {
		Builder alertBuilder = new Builder(context);
		alertBuilder.setTitle(title);
		alertBuilder.setMessage(text);
		alertBuilder.create().show();
	}

}
