package com.study.doubanbook_for_android.api;

public class WrongMsg extends Throwable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7457964260418110305L;
	// "msg":"uri_not_found",
	// "code":1001,
	// "request":"GET \/v2\/photo\/132"
	private String msg;// wrong msg describe
	private String request;// request url and method
	private int code;// wrong code

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
