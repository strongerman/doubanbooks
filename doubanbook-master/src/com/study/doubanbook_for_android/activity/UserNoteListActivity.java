package com.study.doubanbook_for_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.study.doubanbook_for_android.R;
import com.study.doubanbook_for_android.adapter.UserNoteAdapter;
import com.study.doubanbook_for_android.business.DoubanBusiness;
import com.study.doubanbook_for_android.callback.AsynCallback;
import com.study.doubanbook_for_android.model.Annotations;
import com.study.doubanbook_for_android.model.AuthorUser;
import com.study.doubanbook_for_android.model.GeneralNoteResult;
import com.study.doubanbook_for_android.utils.DebugUtils;
import com.study.doubanbook_for_android.utils.ShowErrorUtils;

public class UserNoteListActivity extends BaseP2RActivity<Annotations> {

	String userName = null;
	AuthorUser authorUser = null;
	GeneralNoteResult result;
	DoubanBusiness db = new DoubanBusiness(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_comment_list);
		DebugUtils.e("CLASS", getClass().getName());
		adapter = new UserNoteAdapter(dataList, context);
		initP2RLvAndThread();
		userName = getIntent().getStringExtra("userName");
		authorUser = (AuthorUser) getIntent()
				.getSerializableExtra("authorUser");
		fetchData();
		setNavagator(authorUser.getName() + " 的笔记");
	}

	@Override
	public void selfHandleMsg(Message msg) {
		int arg1 = msg.arg1;
		switch (arg1) {
		case SUCCESS:
			result = (GeneralNoteResult) (msg.obj);
			if (result.getAnnotations().size() == 0)
				toast("本书还没有笔记");
			addData(result.getAnnotations());
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
		db.getUserNotes(String.valueOf(authorUser.getId()), pageIndex
				* PAGE_COUNT + 1, new AsynCallback<GeneralNoteResult>() {
			public void onSuccess(GeneralNoteResult data) {
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
		Intent intent = new Intent(this, NoteAndUserDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("annotations", dataList.get(position - 1));
		intent.putExtras(bundle);
		startActivityForResult(intent, REQUEST_CODE_CHANGED);
	}
}