package com.study.doubanbook_for_android.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Annotations implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7536027048811655857L;

	private String chapter;
	private BookItem book;
	private AuthorUser author_user;// ": {
	// "name": "谁也认不出哥",
	// "is_banned": false,
	// "is_suicide": false,
	// "url": "http://api.douban.com/v2/user/2727575",
	// "avatar": "http://img3.douban.com/icon/u2727575-7.jpg",
	// "uid": "hijetaime",
	// "alt": "http://www.douban.com/people/hijetaime/",
	// "type": "user",
	// "id": "2727575",
	// "large_avatar": "http://img3.douban.com/icon/up2727575-7.jpg"
	// },
	private int privacy;// ": 2,表示对所有人可见,1表示仅仅自己可以见
	private String abstract_photo;// 摘要图片": "",
	@SerializedName("abstract")
	private String abstracts;// ": " 我愿意这样，朋友——\r\n
								// 我独自远行，不但没有你，并且再没有别的影在黑暗里。只有我被黑暗沉没，那世界全属于我自己。\r\n
								// ——《野草 影的告别》",
	private String summary;// ": " 我愿意这样，朋友——\r\n
							// 我独自远行，不但没有你，并且再没有别的影在黑暗里。只有我被黑暗沉没，那世界全属于我自己。\r\n
							// ——《野草 影的告别》",
	private String content;// ": " 我愿意这样，朋友——\r\n
							// 我独自远行，不但没有你，并且再没有别的影在黑暗里。只有我被黑暗沉没，那世界全属于我自己。\r\n
							// ——《野草 影的告别》",
	// private String photos;// ": {},
	private String last_photo;// ": 0,
	private int comments_count;// ": 0,
	private String hasmath;// ": false,
	private int book_id;// ": "1442720",
	private String time;// ": "2012-02-15 22:17:25",
	private int author_id;// ": "2727575",
	private int id;// ": "16757515",
	private int page_no;// ": 178

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	 public BookItem getBook() {
	 return book;
	 }

	public void setBook(BookItem book) {
		this.book = book;
 }

	public AuthorUser getAuthor_user() {
		return author_user;
	}

	public void setAuthor_user(AuthorUser author_user) {
		this.author_user = author_user;
	}

	public int getPrivacy() {
		return privacy;
	}

	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}

	public String getAbstract_photo() {
		return abstract_photo;
	}

	public void setAbstract_photo(String abstract_photo) {
		this.abstract_photo = abstract_photo;
	}

	public String getAbstracts() {
		return abstracts;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	// public String getPhotos() {
	// return photos;
	// }
	//
	// public void setPhotos(String photos) {
	// this.photos = photos;
	// }

	public String getLast_photo() {
		return last_photo;
	}

	public void setLast_photo(String last_photo) {
		this.last_photo = last_photo;
	}

	public int getComments_count() {
		return comments_count;
	}

	public void setComments_count(int comments_count) {
		this.comments_count = comments_count;
	}

	public String getHasmath() {
		return hasmath;
	}

	public void setHasmath(String hasmath) {
		this.hasmath = hasmath;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPage_no() {
		return page_no;
	}

	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}

}
