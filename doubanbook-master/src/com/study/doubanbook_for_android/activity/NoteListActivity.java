package com.study.doubanbook_for_android.activity;

import android.os.Bundle;

import com.study.doubanbook_for_android.R;
import com.study.doubanbook_for_android.adapter.BookAdapter;
import com.study.doubanbook_for_android.business.DoubanBusiness;
import com.study.doubanbook_for_android.model.BookItem;
import com.study.doubanbook_for_android.model.GeneralResult;
import com.study.doubanbook_for_android.utils.DebugUtils;

public class NoteListActivity extends BaseP2RActivity<BookItem> {

	String searchContent = "";
	GeneralResult result;
	DoubanBusiness db = new DoubanBusiness(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_comment_list);

		DebugUtils.e("CLASS", getClass().getName());
		adapter = new BookAdapter(dataList, this);
		initP2RLvAndThread();
		searchContent = getIntent().getStringExtra("searchContent");
		setNavagator("'"+searchContent+"' 的搜索结果");
		fetchData();
	}

}

