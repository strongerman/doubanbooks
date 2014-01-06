package com.study.doubanbook_for_android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.study.doubanbook_for_android.R;
import com.study.doubanbook_for_android.api.WrongMsg;
import com.study.doubanbook_for_android.business.DoubanBusiness;
import com.study.doubanbook_for_android.callback.AsynCallback;
import com.study.doubanbook_for_android.model.BookItem;
import com.study.doubanbook_for_android.model.CommentCallBackMsg;
import com.study.doubanbook_for_android.model.Entry;
import com.study.doubanbook_for_android.utils.ShowErrorUtils;

public class CommentEditActivity extends BaseActivity {

	private static final int COMMENT_SUCCESS = 0;
	private static final int DEL_COMMENT_SUCCESS = 1;
	private static final int FAILURE = 2;

	EditText comment_et;
	Button ok, cancel;
	BookItem bookItem;
	Entry entryItem;
	DoubanBusiness doubanBusiness = new DoubanBusiness(this);
	private EditText commentTitle_et;

	@Override
	void setResultThenFinish() {
		super.setResultThenFinish();
		Intent intent = new Intent();
		intent.putExtra("isChanged", true);
		setResult(Activity.RESULT_OK, intent);
	}

	@Override
	public void baseSelfHandleMsg(Message msg) {
		super.baseSelfHandleMsg(msg);
		int status = msg.arg1;
		switch (status) {
		case COMMENT_SUCCESS:
			toast("评论发表成功");
			setResultThenFinish();
			break;
		case DEL_COMMENT_SUCCESS:
			toast("评论删除成功");
			setResultThenFinish();
			break;
		case FAILURE:
			ShowErrorUtils.showWrongMsg(this, msg);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_write_comment);
		initDatas();
		findViews();
		if (entryItem != null)
			setNavagator("修改评论");
		else
			setNavagator("给" + bookItem.getTitle() + "写评论");
		initWidgets();
		initListners();
	}

	@Override
	void initDatas() {
		super.initDatas();
		entryItem = (Entry) getIntent().getSerializableExtra("entryItem");
		bookItem = (BookItem) getIntent().getSerializableExtra("bookItem");
	}

	@Override
	void findViews() {
		super.findViews();
		ok = (Button) findViewById(R.id.ok_btn);
		cancel = (Button) findViewById(R.id.cancel_btn);
		commentTitle_et = (EditText) findViewById(R.id.commentTitle_et);
		comment_et = (EditText) findViewById(R.id.commentContent_et);
	}

	@Override
	void initWidgets() {
		super.initWidgets();
		if (bookItem != null)
			cancel.setVisibility(View.GONE);
		if (entryItem != null) {
			commentTitle_et.setText(entryItem.getTitle());
			comment_et.setText(entryItem.getSummay());
		}
	}

	@Override
	void initListners() {
		super.initListners();
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String content = getText(comment_et);
				String title = getText(commentTitle_et);
				if (!notNull(title)) {
					toast("标题不能为空");
					return;
				}
				if (!notNull(content)) {
					toast("内容不能为空");
					return;
				}

				if (bookItem != null) {
					doubanBusiness.writeComment(
							String.valueOf(bookItem.getId()), title, content,
							new AsynCallback<CommentCallBackMsg>() {
								@Override
								public void onSuccess(CommentCallBackMsg data) {
									super.onSuccess(data);
									System.out.println(data);
									baseSendMessage(data, COMMENT_SUCCESS);
								}

								@Override
								public void onFailure(WrongMsg caught) {
									super.onFailure(caught);
									System.out.println(caught.getMsg());
									baseSendMessage(caught, FAILURE);
								}
							});
				} else {
					doubanBusiness.editComment(
							String.valueOf(getUserId(entryItem.getId())), title, content,
							new AsynCallback<CommentCallBackMsg>() {
								@Override
								public void onSuccess(CommentCallBackMsg data) {
									super.onSuccess(data);
									System.out.println(data);
									baseSendMessage(data, COMMENT_SUCCESS);
								}

								@Override
								public void onFailure(WrongMsg caught) {
									super.onFailure(caught);
									System.out.println(caught.getMsg());
									baseSendMessage(caught, FAILURE);
								}
							});
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String commentId = getUserId(entryItem.getId());
				doubanBusiness.delComment(commentId,
						new AsynCallback<String>() {
							@Override
							public void onSuccess(String data) {
								super.onSuccess(data);

							}

							@Override
							public void onFailure(WrongMsg caught) {
								super.onFailure(caught);

							}
						});
			}
		});
	}

	String getUserId(String s) {
		int index = s.lastIndexOf("/");
		System.out.println(s.substring(index + 1));
		return s.substring(index + 1);
	}
}
