package com.study.doubanbook_for_android.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.study.doubanbook_for_android.R;
import com.study.doubanbook_for_android.api.WrongMsg;
import com.study.doubanbook_for_android.business.DoubanBusiness;
import com.study.doubanbook_for_android.callback.AsynCallback;
import com.study.doubanbook_for_android.model.Annotations;
import com.study.doubanbook_for_android.model.BookItem;
import com.study.doubanbook_for_android.utils.DebugUtils;
import com.study.doubanbook_for_android.utils.ShowErrorUtils;

public class NoteEditActivity extends BaseActivity {

	public static final int NOTE_EDIT_SUCCESS = 0;
	public static final int NOTE_ADD_SUCCESS = 1;
	public static final int NOTE_EDIT_FAILURE = 2;

	Annotations annotations = null;
	BookItem bookItem = null;
	EditText content_et;
	EditText page_et;
	EditText chapName_et;
	CheckBox privacy_cb;
	private Button ok_btn;
	DoubanBusiness doubanBusiness = new DoubanBusiness(this);

	@Override
	public void baseSelfHandleMsg(Message msg) {
		super.baseSelfHandleMsg(msg);
		int status = msg.arg1;
		switch (status) {
		case NOTE_ADD_SUCCESS:
			toast("笔记添加成功");
			//TODO 是否有必要修改成功,在上个页面刷新数据
			finish();
			break;
		case NOTE_EDIT_SUCCESS:
			toast("笔记修改成功");
			finish();
			break;
		case NOTE_EDIT_FAILURE:
			ShowErrorUtils.showWrongMsg(context, msg);
			toast("笔记编辑失败");
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_book_note_edit);
		DebugUtils.e("CLASS", getClass().getName());
		initDatas();
		if (annotations == null)
			setNavagator("添加" + bookItem.getTitle() + "的笔记");
		else
			setNavagator("修改笔记");
		findViews();
		initWidgets();
		initListners();
	}

	@Override
	void findViews() {
		super.findViews();
		content_et = (EditText) findViewById(R.id.content_et);
		page_et = (EditText) findViewById(R.id.page_et);
		chapName_et = (EditText) findViewById(R.id.chapName_et);
		privacy_cb = (CheckBox) findViewById(R.id.privacey_cb);
		ok_btn = (Button) findViewById(R.id.ok_btn);
	}

	@Override
	void initDatas() {
		super.initDatas();
		try {
			annotations = (Annotations) getIntent().getSerializableExtra(
					"annotations");
			bookItem = (BookItem) getIntent().getSerializableExtra("bookItem");
		} catch (Exception e) {
			DebugUtils.d("NET", e.getMessage());
		}
	}

	@Override
	void initWidgets() {
		super.initWidgets();
		if (annotations != null) {
			content_et.setText(annotations.getContent());
			if (notZero(annotations.getPage_no())) {
				page_et.setText(annotations.getPage_no() + "");
			}
			if (notNull(annotations.getChapter()))
				chapName_et.setText(annotations.getChapter());
			// if(annotations.getPrivacy())
		}
	}

	@Override
	void initListners() {
		super.initListners();
		ok_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = getText(content_et);
				String page = getText(page_et);
				String chapName = getText(chapName_et);
				boolean privace = privacy_cb.isChecked();
				if (!notNull(content) || content.length() <= 15) {
					toast("笔记内容不能为空并字数不少于15字");
					return;
				}
				if (!notNull(chapName) && notNull(page)) {
					toast("章节名字和页数不能同时为空");
					return;
				}

				if (annotations != null) {
					doubanBusiness.editNote(
							String.valueOf(annotations.getId()), content, page,
							chapName, privace, new AsynCallback<Annotations>() {
								@Override
								public void onSuccess(Annotations data) {
									super.onSuccess(data);
									baseSendMessage(data, NOTE_EDIT_SUCCESS);
								}

								@Override
								public void onFailure(WrongMsg caught) {
									super.onFailure(caught);
									baseSendMessage(caught, NOTE_EDIT_FAILURE);
								}
							});
				} else {
					doubanBusiness.writeNote(String.valueOf(bookItem.getId()),
							content, page, chapName, privace,
							new AsynCallback<Annotations>() {
								@Override
								public void onSuccess(Annotations data) {
									super.onSuccess(data);
									baseSendMessage(data, NOTE_ADD_SUCCESS);
								}

								@Override
								public void onFailure(WrongMsg caught) {
									super.onFailure(caught);
									baseSendMessage(caught, NOTE_EDIT_FAILURE);
								}
							});
				}
			}

		});
	}
}
