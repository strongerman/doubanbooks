package com.study.doubanbook_for_android.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CollectSuccessResult implements Serializable{

	private String status;
	private String updatated;
	private int user_id;
	private BookItem book;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdatated() {
		return updatated;
	}
	public void setUpdatated(String updatated) {
		this.updatated = updatated;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public BookItem getBook() {
		return book;
	}
	public void setBook(BookItem book) {
		this.book = book;
	}
	
	
	
}
