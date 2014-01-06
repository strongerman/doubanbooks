package com.study.doubanbook_for_android.auth;


public interface DoubanOAuthListener {
	/**
     * 认证结束后将调用此方法
     * 
     * @param values
     *            Key-value string pairs extracted from the response.
     *            从responsetext中获取的键值对，键值包括"access_token"，"expires_in"，“refresh_token”
     */
    public void onComplete(Token values);

    /**
     * 当认证过程中捕获到HttpException时调用
     * @param e HttpException
     * 
     */
    public void onDoubanException(DoubanException e);

    /**
     * Oauth2.0认证过程中，当认证对话框中的webview接收数据出现错误时调用此方法
     * @param e DoubanDialogError
     * 
     */
    public void onError(DoubanDialogError e);

    /**
     * Oauth2.0认证过程中，如果认证窗口被关闭或认证取消时调用
     * 
     * 
     */
    public void onCancel();
}
