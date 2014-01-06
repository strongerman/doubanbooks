package com.study.doubanbook_for_android.model;

import java.io.Serializable;

public class Author implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4962015958780135346L;
	private String name;
	private String uri;

	public String getName() {
		return name;
		
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
