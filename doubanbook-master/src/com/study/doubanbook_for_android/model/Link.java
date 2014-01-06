package com.study.doubanbook_for_android.model;

import java.io.Serializable;

public class Link implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7448385990743510474L;
	private String href;
	private String rel;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	@Override
	public String toString() {
		return "Link [href=" + href + ", rel=" + rel + "]";
	}

}
