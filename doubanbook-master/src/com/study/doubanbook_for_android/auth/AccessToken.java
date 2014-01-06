package com.study.doubanbook_for_android.auth;

import android.text.TextUtils;

/**
 * for charge the token is vailed
 * @author tezuka-pc
 *
 */
public class AccessToken {
	private String mAccessToken = "";
	private String mRefreshToken = "";
	private long mExpiresTime = 0;
	private String doubanUserId = "";
	public AccessToken() {
		
	}
	/**
	 * Oauth2AccessToken的构造函数，根据accessToken 和expires_in 生成AccessToken实例
	 * @param accessToken  访问令牌
	 * @param expiresIn 有效期，单位：毫秒；仅当从服务器获取到expires_in时适用，表示距离超过认证时间还有多少秒
	 */
	public AccessToken(String accessToken, String expiresIn) {
		mAccessToken = accessToken;
		mExpiresTime = System.currentTimeMillis() + Long.parseLong(expiresIn)*1000;
	}
	/**
	 *  AccessToken是否有效,如果accessToken为空或者expiresTime过期，返回false，否则返回true
	 *  @return 如果accessToken为空或者expiresTime过期，返回false，否则返回true
	 */
	public boolean isSessionValid() {
		return (!TextUtils.isEmpty(mAccessToken) && (mExpiresTime == 0 || (System
				.currentTimeMillis() < mExpiresTime)));
	}
	/**
	 * 获取accessToken
	 */
	public String getToken() {
		return this.mAccessToken;
	}
	/**
     * 获取refreshToken
     */
	public String getRefreshToken() {
		return mRefreshToken;
	}
	/**
	 * 设置refreshToken
	 * @param mRefreshToken
	 */
	public void setRefreshToken(String mRefreshToken) {
		this.mRefreshToken = mRefreshToken;
	}
	/**
	 * 获取超时时间，单位: 毫秒，表示从格林威治时间1970年01月01日00时00分00秒起至现在的总 毫秒数
	 */
	public long getExpiresTime() {
		return mExpiresTime;
	}

	/**
	 * 设置过期时间长度值，仅当从服务器获取到数据时使用此方法
	 *            
	 */
	public void setExpiresIn(String expiresIn) {
		if (expiresIn != null && !expiresIn.equals("0")) {
			setExpiresTime(System.currentTimeMillis() + Long.parseLong(expiresIn) * 1000);
		}
	}

	/**
	 * 设置过期时刻点 时间值
	 * @param mExpiresTime 单位：毫秒，表示从格林威治时间1970年01月01日00时00分00秒起至现在的总 毫秒数
	 *            
	 */
	public void setExpiresTime(long mExpiresTime) {
		this.mExpiresTime = mExpiresTime;
	}
	/**
	 * 设置accessToken
	 * @param mToken
	 */
	public void setToken(String mToken) {
		this.mAccessToken = mToken;
	}
	public String getDoubanUserId() {
		return doubanUserId;
	}
	public void setDoubanUserId(String doubanUserId) {
		this.doubanUserId = doubanUserId;
	}
	
}
