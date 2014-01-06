package com.study.doubanbook_for_android.model;

import java.io.Serializable;

/**
 * sub class of userDetail
 * @author tezuka-pc
 *
 */
public class Status implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6211119857858283322L;
	private int bub;// ": 172,
	private int collect;// ": 89,
	private int board;// ": 12

	public int getBub() {
		return bub;
	}

	public void setBub(int bub) {
		this.bub = bub;
	}

	public int getCollect() {
		return collect;
	}

	public void setCollect(int collect) {
		this.collect = collect;
	}

	public int getBoard() {
		return board;
	}

	public void setBoard(int board) {
		this.board = board;
	}

}
