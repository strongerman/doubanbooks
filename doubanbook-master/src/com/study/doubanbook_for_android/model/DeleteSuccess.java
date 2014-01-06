package com.study.doubanbook_for_android.model;

/**
 * 删除成功承载类 (用于构造204信息)
 * 
 * @author tezuka-pc
 * 
 */
public class DeleteSuccess {
	String delMessage;
	int delCode;
	public String getDelMessage() {
		return delMessage;
	}
	public void setDelMessage(String delMessage) {
		this.delMessage = delMessage;
	}
	public int getDelCode() {
		return delCode;
	}
	public void setDelCode(int delCode) {
		this.delCode = delCode;
	}
 
}
