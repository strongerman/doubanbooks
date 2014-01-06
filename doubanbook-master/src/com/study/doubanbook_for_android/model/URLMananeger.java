package com.study.doubanbook_for_android.model;

public class URLMananeger {
	// root url
	public static final String ROOT_ULR = "https://api.douban.com";
	// user detail url得到当前登录用户的信息
	public static final String USER_DETAIL = "/v2/user/~me";
	// book writer search url
	public static final String BOOK_WRITER_SEARCHR_URL = "/v2/book/search";
	// book detail by id
	public static final String BOOK_DETAIL_URL = "/v2/book/:id";
	// book note url
	public static final String BOOK_NOTE_LIST_URL = "/v2/book/:id/annotations";
	// book note write by user
	public static final String BOOK_NOTE_WRITE_URL = "/v2/book/:id/annotations";
	// book note edit by user
	public static final String BOOK_NOTE_EDIT_URL = "/v2/book/annotation/:id";
	// book note delete by user
	public static final String BOOK_NOTE_DELETE_URL = "/v2/book/annotation/:id";
	// user all note about book
	public static final String USER_ALL_NOTE_URL = "/v2/book/user/:name/annotations";
	// collect book url
	public static final String BOOK_COLLECT_URL = "/v2/book/:id/collection";
	// delete collect book url
	public static final String DEL_BOOK_COL_URL = "/v2/book/:id/collection";
	// get the user's collection url
	public static final String USER_COLLECTION_URL = "/v2/book/user/:name/collections";
	// user search url
	public static final String USER_SEARCH_URL = "/v2/user";
	//add new comment url
	public static final String WRITE_COMMENT_URL = "/v2/book/reviews";
	//delete comment and modify url 			 
	public static final String DEL_COMMENT_URL = "/v2/book/review/:id";
	 

}
