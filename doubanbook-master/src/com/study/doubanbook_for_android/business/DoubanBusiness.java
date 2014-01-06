package com.study.doubanbook_for_android.business;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.study.doubanbook_for_android.activity.BaseActivity;
import com.study.doubanbook_for_android.api.NetUtils;
import com.study.doubanbook_for_android.api.WrongMsg;
import com.study.doubanbook_for_android.auth.Douban;
import com.study.doubanbook_for_android.auth.SimpleDoubanOAuthListener;
import com.study.doubanbook_for_android.callback.AsynCallback;
import com.study.doubanbook_for_android.model.Annotations;
import com.study.doubanbook_for_android.model.AuthorUser;
import com.study.doubanbook_for_android.model.BookItem;
import com.study.doubanbook_for_android.model.CollectBookMsg;
import com.study.doubanbook_for_android.model.CollectSuccessResult;
import com.study.doubanbook_for_android.model.CommentCallBackMsg;
import com.study.doubanbook_for_android.model.CommentReslult;
import com.study.doubanbook_for_android.model.DeleteSuccess;
import com.study.doubanbook_for_android.model.GeneralCollectionResult;
import com.study.doubanbook_for_android.model.GeneralNoteResult;
import com.study.doubanbook_for_android.model.GeneralResult;
import com.study.doubanbook_for_android.model.GeneralUserResult;
import com.study.doubanbook_for_android.model.URLMananeger;
import com.study.doubanbook_for_android.utils.DebugUtils;
import com.study.doubanbook_for_android.utils.XMLReader;


public class DoubanBusiness {
	
	private Context context;
	
	
	public DoubanBusiness(Context context) {
		super();
		this.context = context;
	}
	
	//--------------auth  userDetail-----------------------
	public void auth(){
		Douban douban = Douban.getInstance();
		douban.authorize(context, new SimpleDoubanOAuthListener());
	}
	/**
	 * GET http://api.douban.com/labs/bubbler/user/ahbei  ahbei is user id
	 * @param id
	 * @param callback
	 */
	public void getUserDetail(final String id,final AsynCallback<AuthorUser> callback){
		new Thread() {
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				AuthorUser result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();
				callback.onStart();
				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.USER_DETAIL),
						NetUtils.GET, keys, values, context);
				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
					DebugUtils.e("NET", "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
					DebugUtils.d("NET", "right model");
					result = gson.fromJson(s, new TypeToken<AuthorUser>() {
					}.getType());
					callback.onSuccess(result);
				}
				callback.onDone();
			};
		}.start();
	}

	//---------------book 相关---------------------
	// TODO 修改整个方法,利用线程,回调,得到 DONE
	// 得到字符串,转化成MODEL时没有TRY CATCH,怎样判断是否是错误信息,先变成WRONGMSG
	// MODEL?判断CODE?添加TAG,CODE==0转化成对应模型否则转化成WRONGMSG
	/**
	 * 	q 		查询关键字 			q和tag必传其一
	 *  tag 	查询的tag 		q和tag必传其一 
	 *  start 	取结果的offset 	默认为0 
	 *  count 	取结果的条数 * 		默认为20，最大为100
	 * 
	 * @param q
	 * @param start
	 * @param callback
	 */
	public void getSearchList(final String q, final int start,
			final AsynCallback<GeneralResult> callback) {
		new Thread() {
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				GeneralResult result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();

				// init keys
				keys.add("q");
				keys.add("start");
				keys.add("count");
				// init values
				try {
					// change the encode when necessary
					values.add(URLEncoder.encode(q, "UTF-8"));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				values.add(start + "");
				values.add(BaseActivity.PAGE_COUNT + "");

				callback.onStart();
				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.BOOK_WRITER_SEARCHR_URL),
						NetUtils.GET, keys, values, context);
				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
					DebugUtils.e("NET", "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
					DebugUtils.d("NET", "right model");
					result = gson.fromJson(s, new TypeToken<GeneralResult>() {
					}.getType());
					callback.onSuccess(result);
				}
				callback.onDone();
			};
		}.start();
	}
	
	/**
	 * GET  https://api.douban.com/v2/book/:id
	 * @param bookid
	 * @param callback
	 */
	public void getBookDetailById(final String bookid,final AsynCallback<BookItem> callback){
		new Thread(){
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				BookItem result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();

				callback.onStart();
				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.BOOK_DETAIL_URL.replace(
								":id", bookid)),
						NetUtils.GET, keys, values, context);
				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
					DebugUtils.e("NET", "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
					DebugUtils.d("NET", "right model");
					result = gson.fromJson(s, new TypeToken<BookItem>() {
					}.getType());
					callback.onSuccess(result);
				}
				callback.onDone();
			};
		}.start();
	}

	
	
	//---------------note 相关---------------------
	/**
	 * format 	返回content字段格式 	选填（编辑伪标签格式：text, HTML格式：html），默认为text 
	 * order 	排序 * 				选填（最新笔记：collect, 按有用程度：rank, 按页码先后：page），默认为rank 
	 * page 	按页码过滤				 选填
	 * 
	 * @param bookid
	 * @param start
	 * @param callback
	 */
	public void getNoteList(final String bookid, final int start,
			final AsynCallback<GeneralNoteResult> callback) {
		new Thread() {
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				GeneralNoteResult result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();

				// init keys
				keys.add("start");
				keys.add("count");
				// init values
				values.add(start + "");
				values.add(BaseActivity.PAGE_COUNT + "");
				callback.onStart();
				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.BOOK_NOTE_LIST_URL.replace(
								":id", bookid)), NetUtils.GET, keys, values,
						null);
				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
						DebugUtils.d("NET",  "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
						DebugUtils.d("NET",  "right model");
					result = gson.fromJson(s,
							new TypeToken<GeneralNoteResult>() {
							}.getType());
					callback.onSuccess(result);
				}
				callback.onDone();
			};
		}.start();
	}
 
	/**
	 * 得到用户的所有笔记
	 * GET  https://api.douban.com/v2/book/user/:name/annotations
	 * @param userId
	 * @param start
	 * @param callback
	 */
	public void getUserNotes(final String userId, final int start,
			final AsynCallback<GeneralNoteResult> callback) {
		new Thread() {
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				GeneralNoteResult result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();

				// init keys
				keys.add("start");
				keys.add("count");
				// init values
				values.add(start + "");
				values.add(BaseActivity.PAGE_COUNT + "");
				callback.onStart();
				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.USER_ALL_NOTE_URL.replace(
								":name", userId)), NetUtils.GET, keys, values,
						context);
				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
						DebugUtils.d("NET",  "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
						DebugUtils.d("NET",  "right model");
					result = gson.fromJson(s,
							new TypeToken<GeneralNoteResult>() {
							}.getType());
					callback.onSuccess(result);
				}
				callback.onDone();
			};
		}.start();
	}
	
 
	/**
	 * POST  https://api.douban.com/v2/book/:id/annotations
		参数			意义			备注
		content		笔记内容		必填，需多于15字
		page		页码			页码或章节名选填其一，最多6位正整数
		chapter		章节名		页码或章节名选填其一，最多100字
		privacy		隐私设置		选填，值为'private'为设置成仅自己可见，其他默认为公开
	 * 
	 * @param bookId
	 * @param content
	 * @param page
	 * @param chapName
	 * @param privace
	 * @param callback
	 */
	public void writeNote(final String bookId,final String content,final String page,final String chapName,final boolean privace, 
			final AsynCallback<Annotations> callback) {
		new Thread() {
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				Annotations result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();

				// init keys and values
				keys.add("content");
				values.add(content);
				if(page!=null){
					keys.add("page");
					values.add(String.valueOf(page));
				}
				if(chapName!=null){
					keys.add("chapter");
					values.add(chapName);
				}
				if(privace){
					values.add("private");
					keys.add("privacy");
				}
				 
				callback.onStart();
				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.BOOK_NOTE_WRITE_URL.replace(
								":id", bookId)), NetUtils.POST, keys, values,
						context);
				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
						DebugUtils.d("NET",  "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
						DebugUtils.d("NET",  "right model");
					result = gson.fromJson(s,
							new TypeToken<Annotations>() {
							}.getType());
					callback.onSuccess(result);
				}
				callback.onDone();
			};
		}.start();
	}
	
	/**
	 * PUT  https://api.douban.com/v2/book/annotation/:id
		参数			意义		备注
		content		笔记内容	必填，需多于15字
		page		页码		页码或章节名选填其一，最多6位正整数
		chapter		章节名	页码或章节名选填其一，最多100字
		privacy		隐私设置	选填，值为'private'为设置成仅自己可见，其他默认为公开

	 * @param noteId
	 * @param content
	 * @param page
	 * @param chapName
	 * @param privace
	 * @param callback
	 */
	public void editNote(final String noteId,final String content,final String page,final String chapName,final boolean privace, 
			final AsynCallback<Annotations> callback) {
		new Thread() {
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				Annotations result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();

				// init keys and values
				keys.add("content");
				values.add(content);
				if(page!=null){
					keys.add("page");
					values.add(String.valueOf(page));
				}
				if(chapName!=null){
					keys.add("chapter");
					values.add(chapName);
				}
				if(privace){
					values.add("private");
					keys.add("privacy");
				}
				 
				callback.onStart();
				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.BOOK_NOTE_EDIT_URL.replace(
								":id", noteId)), NetUtils.PUT, keys, values,
						context);
				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
						DebugUtils.d("NET",  "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
						DebugUtils.d("NET",  "right model");
					result = gson.fromJson(s,
							new TypeToken<Annotations>() {
							}.getType());
					callback.onSuccess(result);
				}
				callback.onDone();
			};
		}.start();
	}
	
	/**
	 * DELETE  https://api.douban.com/v2/book/:id/annotations
	 * @param noteId
	 * @param callback
	 */
	public void deleteNote(final String noteId,final AsynCallback<DeleteSuccess> callback) {
		new Thread() {
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				DeleteSuccess result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();
				 
				callback.onStart();
				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.BOOK_NOTE_EDIT_URL.replace(
								":id", noteId)), NetUtils.DELETE, keys, values,
						context);
				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
						DebugUtils.d("NET",  "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
						DebugUtils.d("NET",  "right model");
					result = gson.fromJson(s,
							new TypeToken<DeleteSuccess>() {
							}.getType());
					callback.onSuccess(result);
				}
				callback.onDone();
			};
		}.start();
	}
	
	
	
	//---------------收藏 相关---------------------
	/**
	 * 
	 * 用户收藏图书或者修改收藏状态
	 * 
	 *  POST  https://api.douban.com/v2/book/:id/collection
		参数		意义			备注
		status	收藏状态		必填（想读：wish 在读：reading 或 doing 读过：read 或 done）
		tags	收藏标签字符串	选填，用空格分隔
		comment	短评文本		选填，最多350字
		privacy	隐私设置		选填，值为'private'为设置成仅自己可见，其他默认为公开
		rating	星评			选填，数字1～5为合法值，其他信息默认为不评星
	 * @param bookid
	 * @param start
	 * @param callback
	 */
	//TODO NEED TEST when success show toast done
	public void collectBook(final String bookid,final CollectBookMsg collectmsg,final boolean hasCollect,
			final AsynCallback<CollectSuccessResult> callback) {
		new Thread() {
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				CollectSuccessResult result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();

				keys.add("status");//NOT NULL
				values.add(collectmsg.getStatus());
				//TODO NOT USE YET
				//keys.add("tags");
				if(collectmsg.getComment()!=null){
					values.add(collectmsg.getComment());
					keys.add("comment");
				}
				if(collectmsg.getPrivacy()){
					keys.add("privacy");
					values.add("private");
				}
				if(collectmsg.getRating()>=1&&collectmsg.getRating()<=5){
					keys.add("rating");
					values.add(String.valueOf(collectmsg.getRating()));
				}
				 
				callback.onStart();
				
				
				//根据文档要求,当当前用户收藏时用PUT,否则使用POSTE
				int method = NetUtils.POST;
				if(hasCollect)
					method = NetUtils.PUT;
				
				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.BOOK_COLLECT_URL.replace(
								":id", bookid)), method, keys, values,
						context);
				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
						DebugUtils.d("NET",  "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
						DebugUtils.d("NET",  "right model");
					result = gson.fromJson(s,
							new TypeToken<CollectSuccessResult>() {
							}.getType());
					callback.onSuccess(result);
				}
				callback.onDone();
			};
		}.start();
	}
	
	
	/**
	 * 
	 * DELETE  https://api.douban.com/v2/book/:id/collection
	       返回: status = 204 无返回信息
	 * 取消对基本图书的收藏
	 * @param bookid
	 * @param callback
	 */
	public void deleteCollectBook(final String bookid,
			final AsynCallback<DeleteSuccess> callback) {
		new Thread() {
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				DeleteSuccess result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();
				
				callback.onStart();
				
				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.DEL_BOOK_COL_URL.replace(
								":id", bookid)), NetUtils.DELETE, keys, values,
						context);
				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
						DebugUtils.d("NET",  "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
						DebugUtils.d("NET",  "right model");
					result = gson.fromJson(s,
							new TypeToken<DeleteSuccess>() {
							}.getType());
					callback.onSuccess(result);
				}
				callback.onDone();
			};
		}.start();
	}
	/**
	 * 
		GET  https://api.douban.com/v2/book/user/:name/collections name 是userid而不是	USERNAME
		参数			意义						备注
		status		收藏状态					选填（想读：wish 在读：reading 读过：read）默认为所有状态
		tag			收藏标签					选填
		from		按收藏更新时间过滤的起始时间	选填，格式为符合rfc3339的字符串，例如"2012-10-19T17:14:11"，其他信息默认为不传此项
		to			按收藏更新时间过滤的结束时间	同上
		rating	星评	选填，数字1～5为合法值，其他信息默认为不区分星评
	 * @param  
	 * @param  
	 * @param callback
	 */
	public void getUserCollections( final String username,final String status ,
			final AsynCallback<GeneralCollectionResult> callback) {
		new Thread() {
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				GeneralCollectionResult result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();
 
				keys.add("status");
				values.add(status);
				 
				 
				callback.onStart();
				
				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.USER_COLLECTION_URL.replace(
								":name", username)), NetUtils.GET, keys, values,
						context);
				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
						DebugUtils.e("NET",  "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
						DebugUtils.d("NET",  "right model");
					result = gson.fromJson(s,
							new TypeToken<GeneralCollectionResult>() {
							}.getType());
					callback.onSuccess(result);
				}
				callback.onDone();
			};
		}.start();
	}
	
	

	//---------------用户 相关---------------------
	//TODO NOT FIND TEST BY POSTMAN DONE BY 2013-12-20-20:39
	/**
	 * GET  https://api.douban.com/v2/user
		参数	意义	备注
		q	查询关键字	q和tag必传其一
		tag	查询的tag	q和tag必传其一
		start	取结果的offset	默认为0
		count	取结果的条数	默认为20，最大为100
 	
	 * @param userName
	 * @param collectmsg
	 * @param start
	 * @param callback
	 */
	
	public void searchUser(final String userName,final int start,
			final AsynCallback<GeneralUserResult> callback) {
		new Thread() {
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				GeneralUserResult result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();
				// init keys
				keys.add("q");
				keys.add("start");
				keys.add("count");
				// init values
				try {
					// change the encode when necessary
					values.add(URLEncoder.encode(userName, "UTF-8"));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				values.add(start + "");
				values.add(BaseActivity.PAGE_COUNT + "");

				callback.onStart();
				
				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.USER_SEARCH_URL),
						NetUtils.GET, keys, values, null);
				
				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
						DebugUtils.d("NET",  "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
						DebugUtils.d("NET",  "right model");
					result = gson.fromJson(s, new TypeToken<GeneralUserResult>() {
					}.getType());
					
					callback.onSuccess(result);
				}
				
				callback.onDone();
			};
		}.start();
	}
	
	
	
	
	//---------------评论 相关---------------------
	String xml_url = "http://api.douban.com/book/subject/isbn/:isbn/reviews";
	
	//----------comment in api v1
	//http://api.douban.com/book/subject/isbn/7508630068/reviews?start-index=1&max-results=3
	// http://api.douban.com/book/subject/isbn/9863114952/reviews 
	public void getCommentList(final String isbn,final int start,final AsynCallback<CommentReslult> callback){
		new Thread() { 
			public void run() {
				CommentReslult result = null;
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();
				keys.add("start-index");
				keys.add("max-results");
				
				values.add(String.valueOf(start));
				values.add(String.valueOf(BaseActivity.PAGE_COUNT));
				 
				callback.onStart();
				
				s = NetUtils.getHttpEntity(xml_url.replace(":isbn", isbn),
				NetUtils.GET, keys, values, null);
				
				InputStream input = new ByteArrayInputStream(s.getBytes());
				 
				try {
					result = XMLReader.parseXml(input);
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
					
				callback.onSuccess(result);
				callback.onDone();
			};
		}.start();
	}
	
	/**
	 * POST  https://api.douban.com/v2/book/reviews
		参数	意义	备注
		book	评论所针对的book id	必传
		title	评论头	必传
		content	评论内容	必传，且多于150字
		rating	打分	非必传，数字1～5为合法值，其他信息默认为不打分
返回： 返回status=201， 图书评论Review信息

注意：此处的Review肯定是当前用户的书评，所以其返回的不是summary（摘要），而是content（全文）
	 */
	
	public void writeComment(final String bookId, final String title,
			final String content,
			final AsynCallback<CommentCallBackMsg> callback) {

		new Thread() {
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				CommentCallBackMsg result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();
				// init keys
				keys.add("book");
				keys.add("title");
				keys.add("content");
				// init values
				values.add(bookId);
				values.add(title);
				values.add(content);

				callback.onStart();

				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.WRITE_COMMENT_URL),
						NetUtils.POST, keys, values, context);

				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
					DebugUtils.d("NET", "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
					DebugUtils.d("NET", "right model");
					result = gson.fromJson(s,
							new TypeToken<CommentCallBackMsg>() {
							}.getType());

					callback.onSuccess(result);
				}

				callback.onDone();
			};
		}.start();
	}
	
	/**
	 * 修改评论
PUT  https://api.douban.com/v2/book/review/:id
参数	意义	备注
title	评论头	必传
content	评论内容	必传，且多于150字
rating	打分	非必传，数字1～5为合法值，其他信息默认为不打分
返回： status = 202， 图书评论Review信息

注意：此处的Review肯定是当前用户的书评，所以其返回的不是summary（摘要），而是content（全文）
	 * @param commentId
	 * @param title
	 * @param content
	 * @param callback
	 */
	public void editComment(final String commentId, final String title,
			final String content,
			final AsynCallback<CommentCallBackMsg> callback) {

		new Thread() {
			public void run() {
				WrongMsg wrongMsg = new WrongMsg();
				CommentCallBackMsg result = null;
				Gson gson = new Gson();
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();
				// init keys
				keys.add("title");
				keys.add("content");
				// init values
				values.add(title);
				values.add(content);

				callback.onStart();

				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.DEL_COMMENT_URL.replace(":id", commentId)),
						NetUtils.PUT, keys, values, context);

				wrongMsg = gson.fromJson(s, new TypeToken<WrongMsg>() {
				}.getType());
				if (wrongMsg.getCode() != 0) {
					DebugUtils.d("NET", "wrongmsg model");
					callback.onFailure(wrongMsg);
				} else {
					DebugUtils.d("NET", "right model");
					result = gson.fromJson(s,
							new TypeToken<CommentCallBackMsg>() {
							}.getType());

					callback.onSuccess(result);
				}

				callback.onDone();
			};
		}.start();
	}
	/**
	 * 
	 * @param commentId
	 * @param callback
	 */
	public void delComment(final String commentId,
			final AsynCallback<String> callback) {
		new Thread() {
			public void run() {
				String s = "";
				List<String> keys = new ArrayList<String>();
				List<String> values = new ArrayList<String>();

				callback.onStart();

				s = NetUtils.getHttpEntity(
						getBasicUrl(URLMananeger.DEL_COMMENT_URL.replace(":id",
								commentId)), NetUtils.DELETE, keys, values,
						context);

				if (s.equals("OK")) {
					DebugUtils.d("NET", "right model");
					callback.onSuccess(s);
				}
				callback.onDone();
			};
		}.start();
	}
	/**
	 * get request url
	 * 
	 * @param url
	 * @return
	 */
	private static String getBasicUrl(String url) {
		return URLMananeger.ROOT_ULR + url;
	}

}
