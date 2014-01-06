package com.study.doubanbook_for_android.model;

import java.io.Serializable;
import java.util.List;

public class GeneralResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7564376856699944118L;
	private int count;// :1,
	private int start;// ":0,
	private int total;// ":1227731,
	private List<BookItem> books;

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

	public List<BookItem> getBooks() {
		return books;
	}

	public void setBooks(List<BookItem> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "GeneralResult [count=" + count + ", start=" + start
				+ ", total=" + total + ", books=" + books + "]";
	}

}
