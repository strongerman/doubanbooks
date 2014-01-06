package com.study.doubanbook_for_android.model;

import java.util.List;

public class GeneralCollectionResult {
	private int count;// ":20,
	private int start;// ":0,"
	private int total;// ":14,"
	private List<CollectionItem> collections;

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

	public List<CollectionItem> getCollections() {
		return collections;
	}

	public void setCollections(List<CollectionItem> collections) {
		this.collections = collections;
	}

}
