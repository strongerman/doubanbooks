package com.study.doubanbook_for_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.study.doubanbook_for_android.R;
import com.study.doubanbook_for_android.adapter.BookAdapter;
import com.study.doubanbook_for_android.api.WrongMsg;
import com.study.doubanbook_for_android.business.DoubanBusiness;
import com.study.doubanbook_for_android.callback.AsynCallback;
import com.study.doubanbook_for_android.model.BookItem;
import com.study.doubanbook_for_android.model.GeneralResult;
import com.study.doubanbook_for_android.utils.DebugUtils;
import com.study.doubanbook_for_android.utils.ShowErrorUtils;

public class BookListsActivity extends BaseP2RActivity<BookItem> {

	String searchContent = "";
	GeneralResult result;
	DoubanBusiness db = new DoubanBusiness(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DebugUtils.e("CLASS", getClass().getName());
		setContentView(R.layout.f_comment_list);
		adapter = new BookAdapter(dataList, this);
		initP2RLvAndThread();
		searchContent = getIntent().getStringExtra("searchContent");
		setNavagator("'"+searchContent+"' 匹配的所有搜索结果");
		fetchData();
	}

	@Override
	public void selfHandleMsg(Message msg) {
		int arg1 = msg.arg1;
		switch (arg1) {
		case SUCCESS:
			result = (GeneralResult) (msg.obj);
			addData(result.getBooks());
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
		db.getSearchList(searchContent, pageIndex * PAGE_COUNT,
				new AsynCallback<GeneralResult>() {
					public void onSuccess(GeneralResult data) {
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
		if (pageIndex < pageIndex * PAGE_COUNT) {
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
		Intent intent = new Intent(this, BookDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("bookItem", dataList.get(position - 1));
		intent.putExtras(bundle);
		startActivityForResult(intent, REQUEST_CODE_CHANGED);
	}
}