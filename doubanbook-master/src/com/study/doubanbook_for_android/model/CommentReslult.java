package com.study.doubanbook_for_android.model;

import java.util.Arrays;
import java.util.List;

/**
 * <?xml version="1.0" encoding="UTF-8"?> <feed
 * xmlns="http://www.w3.org/2005/Atom" xmlns:db="http://www.douban.com/xmlns/"
 * xmlns:gd="http://schemas.google.com/g/2005"
 * xmlns:openSearch="http://a9.com/-/spec/opensearchrss/1.0/"
 * xmlns:opensearch="http://a9.com/-/spec/opensearchrss/1.0/"> <title>史蒂夫·乔布斯传
 * 的评论</title> <link href="http://book.douban.com/subject/6798611/reviews"
 * rel="alternate"/> <opensearch:startIndex>1</opensearch:startIndex>
 * <opensearch:totalResults>607</opensearch:totalResults> <entry>
 * <id>http://api.douban.com/review/5142031</id>
 * <title>我不是为了成为第一名而存在于这个世界。</title> <author> <link
 * href="http://api.douban.com/people/16249062" rel="self"/> <link
 * href="http://www.douban.com/people/ChannaDisco/" rel="alternate"/> <link
 * href="http://img3.douban.com/icon/u16249062-625.jpg" rel="icon"/>
 * <name>夏奈君</name> <uri>http://api.douban.com/people/16249062</uri> </author>
 * <published>2011-10-24T16:58:11+08:00</published>
 * <updated>2013-12-20T12:09:31+08:00</updated> <link
 * href="http://api.douban.com/review/5142031" rel="self"/> <link
 * href="http://www.douban.com/review/5142031/" rel="alternate"/> <link
 * href="http://api.douban.com/book/subject/6798611"
 * rel="http://www.douban.com/2007#subject"/>
 * <summary>开完一周例会，Boss突发奇想要我们讨论“乔布斯现象
 * “这个话题。身旁的小女生嘟嘟囔囔地说了一句：”我觉得乔布斯的死只是这世上诸多事情中的一个，跟...</summary> <db:comments
 * value="204"/> <db:useless value="106"/> <db:votes value="1139"/> <gd:rating
 * max="5" min="1" value="5"/> </entry> <entry>
 * <id>http://api.douban.com/review/5151852</id> <title>现实扭曲力场</title> <author>
 * <link href="http://api.douban.com/people/3011430" rel="self"/> <link
 * href="http://www.douban.com/people/liye1984/" rel="alternate"/> <link
 * href="http://img3.douban.com/icon/u3011430-14.jpg" rel="icon"/>
 * <name>烨子</name> <uri>http://api.douban.com/people/3011430</uri> </author>
 * <published>2011-11-02T13:05:52+08:00</published>
 * <updated>2013-11-10T11:13:25+08:00</updated> <link
 * href="http://api.douban.com/review/5151852" rel="self"/> <link
 * href="http://www.douban.com/review/5151852/" rel="alternate"/> <link
 * href="http://api.douban.com/book/subject/6798611"
 * rel="http://www.douban.com/2007#subject"/>
 * <summary>1.现实扭曲力场，看完整本书，这个词在我脑中挥之不去
 * 。这种强大到逆天的能力可以说是乔布斯改变世界的一个重要武器，力场一开，黑的就变成白的，假的变...</summary> <db:comments
 * value="87"/> <db:useless value="33"/> <db:votes value="260"/> <gd:rating
 * max="5" min="1" value="5"/> </entry>
 * <opensearch:itemsPerPage>2</opensearch:itemsPerPage> </feed>
 * 
 * 
 * @author tezuka-pc
 * 
 */
public class CommentReslult {

	// <title>史蒂夫·乔布斯传 的评论</title>
	// <link href="http://book.douban.com/subject/ /reviews"
	// rel="alternate"/>
	// <opensearch:startIndex>1</opensearch:startIndex>
	// <opensearch:totalResults>607</opensearch:totalResults>
	// <opensearch:itemsPerPage>2</opensearch:itemsPerPage>
	private String title;
	private int start;
	private int total;
	private int count;
	private List<Entry> entry;
	private Link link;

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public void add(Entry en) {
		entry.add(en);
	}

	public CommentReslult() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int addItem(Entry item) {
		entry.add(item);
		return entry.size();
	}

	public CommentReslult(String name, Entry... stu) {
		this.entry = Arrays.asList(stu);
	}

	public List<Entry> getEntry() {
		return entry;
	}

	public void setEntry(List<Entry> entry) {
		this.entry = entry;
	}

	@Override
	public String toString() {
		return "Result [ entry=" + entry + "]";
	}
}
