package com.study.doubanbook_for_android.model;

public class CommentCallBackMsg {
	Rating rating;
	String title;
	String updated;
	AuthorUser author;
	BookItem book;
	String cotent;
	/*
	 * {"rating":{"max":5,"value":"4","min":0},"votes":0,
	 * "author":{"name":"wugian","is_banned":false,"is_suicide":false,"url":
	 * "http:\/\/api.douban.com\/v2\/user\/72719211","avatar":"http:\/\/img3.douban.com\/icon\/user_normal.jpg","uid":"72719211","alt":"http:\/\/www.douban.com\/people\/72719211\/","type":"user","id":"72719211","large_avatar":"http:\/\/img3.douban.com\/icon\/user_large.jpg"},
	 * "title":"测试文字中", "updated":"2014-01-04 18:51:18", "comments":0,"content":
	 * "测试文字中测试文字中测试文字中测试文字中测试文字中测试文字中测试文字中测试文字中测试文字中测试文字中测试文字中测试文字中测试文字中测试文字中测试文字中测试文字中测试文字中测试文字中"
	 * , "book":
	 * {"rating":{"max":10,"numRaters":24,"average":"9.2","min":0},"subtitle"
	 * :"","pubdate":"2012-6-1","image":
	 * "http:\/\/img3.douban.com\/mpic\/s24938631.jpg","binding":"平裝","images":{"small":"http:\/\/img3.douban.com\/spic\/s24938631.jpg","large":"http:\/\/img3.douban.com\/lpic\/s24938631.jpg","medium":"http:\/\/img3.douban.com\/mpic\/s24938631.jpg"},"alt":"http:\/\/book.douban.com\/subject\/21328038\/","id":"21328038","title":"求魔1","author_intro":"","tags":[{"count":6,"name":"耳根","title":"耳根"},{"count":5,"name":"玄幻","title":"玄幻"},{"count":4,"name":"网络文学","title":"网络文学"},{"count":3,"name":"网络小说","title":"网络小说"},{"count":2,"name":"仙侠","title":"仙侠"},{"count":1,"name":"小说","title":"小说"},{"count":1,"name":"中国玄幻","title":"中国玄幻"}],"origin_title":"","price":"NTD
	 * 170
	 * ","translator":[],"catalog":"","pages":"","publisher":"小說頻道","isbn10":"
	 * 9863114952
	 * ","isbn13":"9789863114956","alt_title":"","url":"http:\/\/api.douban
	 * .com\/
	 * v2\/book\/21328038","author":["耳根"]},"useless":0,"published":"2014-01-04
	 * 18
	 * :51:18","alt":"http:\/\/book.douban.com\/review\/6492996\/","id":"6492996
	 * "}
	 */
	public Rating getRating() {
		return rating;
	}
	public void setRating(Rating rating) {
		this.rating = rating;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public AuthorUser getAuthor() {
		return author;
	}
	public void setAuthor(AuthorUser author) {
		this.author = author;
	}
	public BookItem getBook() {
		return book;
	}
	public void setBook(BookItem book) {
		this.book = book;
	}
	public String getCotent() {
		return cotent;
	}
	public void setCotent(String cotent) {
		this.cotent = cotent;
	}
	@Override
	public String toString() {
		return "CommentCallBackMsg [rating=" + rating + ", title=" + title
				+ ", updated=" + updated + ", author=" + author + ", book="
				+ book + ", cotent=" + cotent + "]";
	}
	
	
}
