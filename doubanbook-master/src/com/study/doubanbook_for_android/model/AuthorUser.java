package com.study.doubanbook_for_android.model;

import java.io.Serializable;

/**
 * subclass of annotations recommend the message of user
 * 
 * @author tezuka-pc
 * 
 */
public class AuthorUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4591431265383536507L;

	private int loc_id;// id?
	private String name;// ": "谁也认不出哥",
	private boolean is_banned;// ": false,
	private boolean is_suicide;// ": false,
	private String url;// ": "http://api.douban.com/v2/user/2727575",
	private String avatar;// ": "http://img3.douban.com/icon/u2727575-7.jpg",
	private String uid;// ": "hijetaime",
	private String alt;// home page of note to introduce qr_code or auto jump
						// ": "http://www.douban.com/people/hijetaime/",
	private String type;// ": "user",
	private int id;// ": "2727575",
	private String large_avatar;// ": "http://img3.douban.com/icon/up2727575-7.jpg"

	private String created;// ": "2006-11-05 23:22:43",
	// 个人简介,说明,声明????
	private String desc;// ": "我的新浪微博：http://weibo.com/wutong828\n\n注意！！若网络转载本人文章请注明作者姓名和原文链接地址。\n\n媒体约稿请豆邮或发信至：wutong828@hotmail.com\n\n所有文字未经本人许可不得擅自投稿，或在平面媒体刊登，违者必究！！\n\n\n我的简介：\n\nW小姐，本名吴桐。国家二级婚姻家庭咨询师，毕业于新加坡ACC心理辅导中心。\n\n闲来无事写写字。\n有困扰的朋友可以豆邮给我。信件不宜太长太繁琐，写清楚事件和你要问的问题就好。由于信件较多，不能保证每封都回复。另外，贴出的回信不会删除。若不想自己的回信被贴出，写信时请注明。\n\n\n\n已出版《那些荒谬的往事叫青春》\n\n当当网购买链接：http://product.dangdang.com/main/product.aspx?product_id=23191859\n\n卓越网购买链接：http://www.amazon.cn/gp/product/B00B7YF1U2/ref=s9_simh_gw_p14_d2_i1?pf_rd_m=A1AJ19PSB66TGU&pf_rd_s=center-2&pf_rd_r=1H3QZNZWMB4XKD4Q18Y7&pf_rd_t=101&pf_rd_p=58223152&pf_rd_i=899254051\n\n京东网购买链接：http://book.360buy.com/11179728.html\n",
	private String loc_name;//local city name
	
	

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public int getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(int loc_id) {
		this.loc_id = loc_id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIs_banned() {
		return is_banned;
	}

	public void setIs_banned(boolean is_banned) {
		this.is_banned = is_banned;
	}

	public boolean getIs_suicide() {
		return is_suicide;
	}

	public void setIs_suicide(boolean is_suicide) {
		this.is_suicide = is_suicide;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLarge_avatar() {
		return large_avatar;
	}

	public void setLarge_avatar(String large_avatar) {
		this.large_avatar = large_avatar;
	}

}
