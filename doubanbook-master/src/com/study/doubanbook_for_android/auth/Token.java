/*
 * Token.java
 * classes : org.kevin.douban.auth.Token 
 * Created at : 2013-5-9 下午10:00:50
 */
package com.study.doubanbook_for_android.auth;

import com.google.gson.annotations.SerializedName;

/**
 * accesstoken
 */
public class Token {
	@SerializedName("access_token")
	public String access_token;
	@SerializedName("refresh_token")
	public String refresh_token;
	@SerializedName("expires_in")
	public long expires_in;
	@SerializedName("douban_user_id")
	public String douban_user_id;
}
