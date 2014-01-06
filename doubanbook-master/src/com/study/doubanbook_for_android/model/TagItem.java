package com.study.doubanbook_for_android.model;

import java.io.Serializable;

/**
 * subclass of book item
 * 
 * @author tezuka-pc
 * 
 */
public class TagItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9069698144219203836L;
	private int count;// :2416,"
	private String name;// ":"小王子
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
