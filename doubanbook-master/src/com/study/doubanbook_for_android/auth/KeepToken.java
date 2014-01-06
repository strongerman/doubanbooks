/*
 * KeepToken.java
 * classes : org.kevin.douban.auth.KeepToken 
 * Created at : 2013-5-14 上午11:16:20
 */
package com.study.doubanbook_for_android.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * save token to the preference
 * 
 */
public class KeepToken {
	private static final String PREFERENCES_NAME = "douban_android_sdk";

	/**
	 * 保存accesstoken到SharedPreferences
	 * 
	 * @param context
	 *            Activity 上下文环境
	 * @param token
	 *            AccessToken
	 */
	public static void keepAccessToken(Context context, AccessToken token) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putString("access_token", token.getToken());
		editor.putLong("expires_time", token.getExpiresTime());
		editor.putString("refresh_token", token.getRefreshToken());
		editor.putString("douban_user_id", token.getDoubanUserId());
		editor.commit();
	}

	/**
	 * 清空sharepreference
	 * 
	 * @param context
	 */
	public static void clear(Context context) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 从SharedPreferences读取accessstoken
	 * 
	 * @param context
	 * @return AccessToken
	 */
	public static AccessToken readAccessToken(Context context) {
		AccessToken token = new AccessToken();
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_APPEND);
		token.setToken(pref.getString("access_token", ""));
		token.setExpiresTime(pref.getLong("expires_time", 0));
		token.setRefreshToken(pref.getString("refresh_token", ""));
		token.setDoubanUserId(pref.getString("douban_user_id", ""));
		return token;
	}

}
