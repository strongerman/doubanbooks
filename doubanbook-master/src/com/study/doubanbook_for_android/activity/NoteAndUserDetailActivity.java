package com.study.doubanbook_for_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.doubanbook_for_android.R;
import com.study.doubanbook_for_android.api.WrongMsg;
import com.study.doubanbook_for_android.auth.KeepToken;
import com.study.doubanbook_for_android.business.DoubanBusiness;
import com.study.doubanbook_for_android.callback.AsynCallback;
import com.study.doubanbook_for_android.model.Annotations;
import com.study.doubanbook_for_android.model.DeleteSuccess;
import com.study.doubanbook_for_android.utils.DebugUtils;
import com.study.doubanbook_for_android.utils.ShowErrorUtils;

public class NoteAndUserDetailActivity extends BaseActivity {

	private final static int DELETE_SUCCESS = 0;
	private final static int DELETE_FAILURE = 1;

	Annotations annotations;
	ImageView bigAvatar_iv;
	TextView userName_tv;
	TextView noteHome_tv;
	Button showQrCode_btn;
	TextView comment_tv;
	private Button modify_btn;
	private Button del_btn;
	private LinearLayout edit_lyt;

	@Override
	public void baseSelfHandleMsg(Message msg) {
		super.baseSelfHandleMsg(msg);
		int status = msg.arg1;
		switch (status) {
		case DELETE_SUCCESS:
			toast("删除笔记成功");
			setResult();
			finish();
			break;
		case DELETE_FAILURE:
			ShowErrorUtils.showWrongMsg(context, msg);
			break;
		default:
			break;
		}
	}

	void setResult() {
		Intent intent = new Intent();
		intent.putExtra("isChanged", true);
		setResult(Activity.RESULT_OK, intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_note_user_detail);
		DebugUtils.e("CLASS", getClass().getName());
		initDatas();
		findViews();
		setNavagator(annotations.getBook().getTitle() + "的评论");
		initWidgets();
		initListners();
	}

	@Override
	void initDatas() {
		super.initDatas();
		annotations = (Annotations) getIntent().getSerializableExtra(
				"annotations");
	}

	@Override
	void findViews() {
		super.findViews();
		bigAvatar_iv = (ImageView) findViewById(R.id.bigAvatar_iv);
		userName_tv = (TextView) findViewById(R.id.userName_tv);
		noteHome_tv = (TextView) findViewById(R.id.noteHome_tv);
		showQrCode_btn = (Button) findViewById(R.id.showQrCode_btn);
		comment_tv = (TextView) findViewById(R.id.comment_tv);
		del_btn = (Button) findViewById(R.id.del_btn);
		modify_btn = (Button) findViewById(R.id.modify_btn);
		edit_lyt = (LinearLayout) findViewById(R.id.edit_lyt);
	}

	@Override
	void initWidgets() {
		super.initWidgets();
		imageDownloader.download(
				annotations.getAuthor_user().getLarge_avatar(), bigAvatar_iv,
				null);
		userName_tv.setText(annotations.getAuthor_user().getName());
		noteHome_tv.setText(annotations.getAuthor_user().getAlt());
		comment_tv.setText(annotations.getContent());
		String curId = String.valueOf(annotations.getAuthor_user().getId());
		String userId = KeepToken.readAccessToken(context).getDoubanUserId();
		if (curId.equals(userId))
			edit_lyt.setVisibility(View.VISIBLE);
		else
			edit_lyt.setVisibility(View.GONE);
	}

	@Override
	void initListners() {
		super.initListners();
		del_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("确定删除?")
						.setCancelable(false)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										toDelete();
										dialog.cancel();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
		modify_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, NoteEditActivity.class);
				intent.putExtra("annotations", annotations);
				startActivity(intent);
			}
		});
	}

	void toDelete() {
		// use api to delete
		DoubanBusiness doubanBusiness = new DoubanBusiness(context);
		doubanBusiness.deleteNote(String.valueOf(annotations.getId()),
				new AsynCallback<DeleteSuccess>() {
					@Override
					public void onSuccess(DeleteSuccess data) {
						super.onSuccess(data);
						baseSendMessage(data, DELETE_SUCCESS);
					}

					@Override
					public void onFailure(WrongMsg caught) {
						super.onFailure(caught);
						baseSendMessage(caught, DELETE_FAILURE);
					}
				});
	}
}
