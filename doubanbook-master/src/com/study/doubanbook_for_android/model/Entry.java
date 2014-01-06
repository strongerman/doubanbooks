package com.study.doubanbook_for_android.model;

import java.io.Serializable;


public class Entry implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8232394162475460017L;
	
	private String id;
	private String title;
	private Author author;
	private String published;
	private String updated;
	private String summay;
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getSummay() {
		return summay;
	}

	public void setSummay(String summay) {
		this.summay = summay;
	}

	@Override
	public String toString() {
		return "Entry [id=" + id + ", title=" + title + ", author=" + author
				+ ", published=" + published + ", updated=" + updated
				+ ", summay=" + summay + "]";
	}

}
