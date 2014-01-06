package com.study.doubanbook_for_android.model;

import java.io.Serializable;

public class CurrenUserCollection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6118583398582823347L;
	private String status;// ":"read",
	private Rating rating;
	private String updated;// ":"2012-11-2012:08:04",
	private int user_id;// ":"33388491",
	private int book_id;// ":"6548683",
	private int id;// ":605519800

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
