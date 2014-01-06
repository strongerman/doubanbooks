package com.study.doubanbook_for_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.study.doubanbook_for_android.R;
import com.study.doubanbook_for_android.auth.AccessToken;
import com.study.doubanbook_for_android.auth.KeepToken;
import com.study.doubanbook_for_android.model.Entry;
import com.study.doubanbook_for_android.utils.DebugUtils;

public class CommentDetailActivity extends BaseActivity {

	Entry entryItem;
	private TextView summary;
	private TextView userName;
	private TextView updatedTime;
	private TextView commentTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_comment_detail);
		setNavagator("评论详情");
		initDatas();
		DebugUtils.d("NET", entryItem.toString());
		AccessToken ac = KeepToken.readAccessToken(this);
		if (ac.getDoubanUserId().equals(
				getUserId(entryItem.getAuthor().getUri())))
			setRightButton();
		findViews();
		initWidgets();
	}
	@Override
	void initRightListener() {
		super.initRightListener();
		Intent intent = new Intent(this,CommentEditActivity.class);
		intent.putExtra("entryItem", entryItem);
		startActivity(intent);
	}

	@Override
	void initDatas() {
		super.initDatas();
		entryItem = (Entry) getIntent().getSerializableExtra("entryItem");
	}

	@Override
	void findViews() {
		super.findViews();
		commentTitle = (TextView) findViewById(R.id.cDetailTitle_tv);
		summary = (TextView) findViewById(R.id.summary_tv);
		userName = (TextView) findViewById(R.id.userName_tv);
		updatedTime = (TextView) findViewById(R.id.updatedTime_tv);
	}

	@Override
	void initWidgets() {
		super.initWidgets();
		commentTitle.setText(entryItem.getTitle());
		summary.setText(entryItem.getSummay());
		userName.setText(entryItem.getAuthor().getName());
		updatedTime.setText(entryItem.getUpdated());
	}

	String getUserId(String s) {
		int index = s.lastIndexOf("/");
		System.out.println(s.substring(index));
		return s.substring(index+1);
	}
}
