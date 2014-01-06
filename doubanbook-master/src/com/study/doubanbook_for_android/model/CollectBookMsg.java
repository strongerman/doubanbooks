package com.study.doubanbook_for_android.model;

import java.io.Serializable;

public class CollectBookMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5263072570721071206L;
	
		private String	status;	//	收藏状态	必填（想读：wish 在读：reading 或 doing 读过：read 或 done）
		private String tags;	//	收藏标签字符串	选填，用空格分隔
		private String	comment;//	短评文本	选填，最多350字
		private boolean	privacy;//	隐私设置	选填，值为'private'为设置成仅自己可见，其他默认为公开
		private int	rating;		//	星评	选填，数字1～5为合法值，其他信息默认为不评星
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getTags() {
			return tags;
		}
		public void setTags(String tags) {
			this.tags = tags;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public boolean getPrivacy() {
			return privacy;
		}
		public void setPrivacy(boolean privacy) {
			this.privacy = privacy;
		}
		public int getRating() {
			return rating;
		}
		public void setRating(int rating) {
			this.rating = rating;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		
}
