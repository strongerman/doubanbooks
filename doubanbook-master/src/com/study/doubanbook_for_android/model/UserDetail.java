package com.study.doubanbook_for_android.model;

import java.io.Serializable;

public class UserDetail implements Serializable {

			/**
		 * 
		 */
		private static final long serialVersionUID = -4928465242591300058L;
		private int id;//": "1000001",
	    private String uid;//": "ahbei",
	    private String title;//": "阿北",
	    private String homepage;//": "http://www.douban.com/people/ahbei/",
	    private String  icon;//": "http://img3.douban.com/icon/ul1868783-24.jpg",
	    private Status stats;///": {
//	        "bub": 172,
//	        "collect": 89,
//	        "board": 12
//	    },
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getHomepage() {
			return homepage;
		}
		public void setHomepage(String homepage) {
			this.homepage = homepage;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public Status getStats() {
			return stats;
		}
		public void setStats(Status stats) {
			this.stats = stats;
		}
	    
	    
	    
}
