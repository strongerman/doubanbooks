package com.study.doubanbook_for_android.model;

import java.io.Serializable;
import java.util.List;

public class GeneralUserResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5136634313346448041L;
	private int count;// :1,
	private int start;// ":0,
	private int total;// ":1227731,
	private List<AuthorUser> users;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<AuthorUser> getUsers() {
		return users;
	}

	public void setUsers(List<AuthorUser> users) {
		this.users = users;
	}

}
