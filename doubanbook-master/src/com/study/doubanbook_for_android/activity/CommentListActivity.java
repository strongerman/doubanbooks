package com.study.doubanbook_for_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.study.doubanbook_for_android.R;
import com.study.doubanbook_for_android.adapter.CommentAdapter;
import com.study.doubanbook_for_android.business.DoubanBusiness;
import com.study.doubanbook_for_android.callback.AsynCallback;
import com.study.doubanbook_for_android.model.BookItem;
import com.study.doubanbook_for_android.model.CommentReslult;
import com.study.doubanbook_for_android.model.Entry;
import com.study.doubanbook_for_android.utils.DebugUtils;
import com.study.doubanbook_for_android.utils.ShowErrorUtils;

public class CommentListActivity extends BaseP2RActivity<Entry> {

	String isbn = "";
	CommentReslult result;
	DoubanBusiness db = new DoubanBusiness(this);
	BookItem bookItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DebugUtils.e("CLASS", getClass().getName());
		setContentView(R.layout.f_comment_list);
		adapter = new CommentAdapter(dataList, this);
		initP2RLvAndThread();
		bookItem = (BookItem) getIntent().getSerializableExtra("bookItem");
		isbn = bookItem.getIsbn10();
		if (bookItem.getCurrent_user_collection() != null) {
			setRightButton();
		}
		setNavagator("'" + bookItem.getTitle() + "' 的评论");
		fetchData();
	}

	@Override
	void initRightListener() {
		super.initRightListener();
		// TODO add lister when the user is authed
	}

	@Override
	public void selfHandleMsg(Message msg) {
		int arg1 = msg.arg1;
		switch (arg1) {
		case SUCCESS:
			result = (CommentReslult) (msg.obj);
			addData(result.getEntry());
			break;
		case FAILURE:
			ShowErrorUtils.showWrongMsg(context, msg);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void fetchData() {
		super.fetchData();
		db.getCommentList(isbn, pageIndex * PAGE_COUNT,
				new AsynCallback<CommentReslult>() {
					public void onSuccess(CommentReslult data) {
						pageIndex++;
						sendMessage(data, SUCCESS);
					};

					public void onFailure(
							com.study.doubanbook_for_android.api.WrongMsg caught) {
						sendMessage(caught, FAILURE);
					};
				});
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		super.onRefresh(refreshView);
		if (pageIndex * PAGE_COUNT < result.getTotal()) {
			fetchData();
			refreshCompleted();
		} else {
			refreshCompleted();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		super.onItemClick(arg0, arg1, position, arg3);
		Intent intent = new Intent(this, CommentDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("entryItem", dataList.get(position - 1));
		intent.putExtras(bundle);
		startActivityForResult(intent, REQUEST_CODE_CHANGED);
	}
}