package com.study.doubanbook_for_android.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.study.doubanbook_for_android.R;
import com.study.doubanbook_for_android.api.WrongMsg;
import com.study.doubanbook_for_android.auth.AccessToken;
import com.study.doubanbook_for_android.auth.KeepToken;
import com.study.doubanbook_for_android.business.DoubanBusiness;
import com.study.doubanbook_for_android.callback.AsynCallback;
import com.study.doubanbook_for_android.model.BookItem;
import com.study.doubanbook_for_android.model.CollectBookMsg;
import com.study.doubanbook_for_android.model.CollectSuccessResult;
import com.study.doubanbook_for_android.model.DeleteSuccess;
import com.study.doubanbook_for_android.utils.DebugUtils;
import com.study.doubanbook_for_android.utils.ShowErrorUtils;

//TODO add slid menu to show book note then can't show the comment list so cancel
//TODO add comment api in v1
public class BookDetailActivity extends BaseActivity {

	private static final int COLLETE_DELETE_SUCCESS = 0;// 取消收藏成功
	private static final int COLLETE_DELETE_FAILURE = 3;// 取消收藏成功
	private static final int COLLETE_SUCCESS = 1;// 收藏成功
	private static final int COLLETE_FAILUR = 2;// 收藏失败

	BookItem bookItem = null;

	DoubanBusiness doubanBusiness = new DoubanBusiness(this);
	private boolean hasCollectFirst = false;
	private boolean hasCollecte = false;// 标记当前图书是否被收藏
	private String status = "";

	ImageView bookImg;
	TextView authorSumary_tv;
	TextView bookSumary;
	TextView title;
	TextView author;
	TextView price;
	TextView publisher;
	TextView grade;
	Button comment;
	private String bookid;
	private Button wish;
	private Button reading;
	private Button done;
	private Button delCollect;
	private PopupWindow popwindow;
	private LinearLayout bookDetail;
	private LinearLayout collet_lyt;

	TextView pageTitle_tv;
	Button comment_btn;

	private String popTitleStr = "";// 弹出窗口的标题

	private MessageHandler msgHandler;// 消息处理器
	private Button writeNote_btn;
	private Button writeComment_btn;

	class MessageHandler extends Handler {
		public MessageHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
			case COLLETE_DELETE_SUCCESS:
				showDelete();
				resetTextColor(wish, reading, done);
				wish.setTextColor(Color.GRAY);
				toast("取消收藏图书成功");
				break;
			case COLLETE_DELETE_FAILURE:
				WrongMsg wrongmsg = (WrongMsg) msg.obj;
				toast("取消收藏图书失败" + wrongmsg.getMsg());
				break;
			case COLLETE_SUCCESS:
				// 想读：wish 在读：reading 或 doing 读过：read 或 done）
				if (status.equals("wish"))
					resetTextColor(wish, reading, done);
				else if (status.equals("reading"))
					resetTextColor(reading, wish, done);
				else if (status.equals("read"))
					resetTextColor(done, reading, wish);
				showDelete();
				toast("收藏图书成功");
				break;
			case COLLETE_FAILUR:
				showDelete();
				toast("收藏图书失败");
				ShowErrorUtils.showWrongMsg(context, msg);
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_book_detail);
		DebugUtils.e("CLASS", getClass().getName());
		findViews();
		setRightButton();
		initDatas();
		setNavagator(bookItem.getTitle());
		initWidgets();
		initListners();
		initPopWindow();
	}

	@Override
	void initRightListener() {
		super.initRightListener();
		Intent intent = new Intent(context, BookNoteListActivity.class);
		// TODO 添加传递BOOKITEM 或者添加传递BOOK NAME 参数 done
		intent.putExtra("bookid", bookid);
		intent.putExtra("bookItem", bookItem);

		startActivity(intent);
	}

	@Override
	void setResultThenFinish() {
		super.setResultThenFinish();
		setResult();
		finish();
	}

	/**
	 * 初始化对话框
	 */
	@SuppressWarnings("deprecation")
	private void initPopWindow() {
		LayoutInflater factory = LayoutInflater.from(this);
		View view = factory.inflate(R.layout.a_collect_detail, null);

		popwindow = new PopupWindow(view,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT);

		TextView popTitle_tv = (TextView) view.findViewById(R.id.popTitle_tv);
		final EditText tag_et = (EditText) view.findViewById(R.id.tag_et);
		final RatingBar ratingBar_rb = (RatingBar) view
				.findViewById(R.id.ratingBar_rb);
		final EditText comment_et = (EditText) view
				.findViewById(R.id.commentContent_et);

		Button cancle_btn = (Button) view.findViewById(R.id.cancle_btn);
		Button ok_btn = (Button) view.findViewById(R.id.ok_btn);

		popTitle_tv.setText(popTitleStr);
		ok_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int rating = (int) ratingBar_rb.getRating();
				String s = getText(tag_et);
				String comment = getText(comment_et);
				dissmissPop();
				collectBook(rating, s, comment);
			}
		});
		cancle_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dissmissPop();
			}
		});
		/* 点其他地方可消失 */
		popwindow.setFocusable(true);
		popwindow.setBackgroundDrawable(new BitmapDrawable());
	}

	/**
	 * 收藏图书
	 * 
	 * @param rating
	 * @param title
	 * @param comment
	 */
	protected void collectBook(int rating, String title, String comment) {
		CollectBookMsg collectBookMsg = new CollectBookMsg();
		collectBookMsg.setStatus(status);
		if (notZero(rating))
			collectBookMsg.setRating(rating);
		if (notNull(title))
			collectBookMsg.setTags(title);
		if (notNull(comment))
			collectBookMsg.setComment(comment);

		doubanBusiness.collectBook(bookid, collectBookMsg, hasCollecte,
				new AsynCallback<CollectSuccessResult>() {
					@Override
					public void onSuccess(CollectSuccessResult data) {
						super.onSuccess(data);
						hasCollecte = true;
						Message msg = new Message();
						msg.arg1 = COLLETE_SUCCESS;
						msgHandler.sendMessage(msg);
					}

					@Override
					public void onFailure(WrongMsg caught) {
						super.onFailure(caught);
						Message msg = new Message();
						msg.arg1 = COLLETE_FAILUR;
						msgHandler.sendMessage(msg);
					}
				});

	}

	/**
	 * 隐藏对话框
	 */
	private void dissmissPop() {
		if (popwindow.isShowing())
			popwindow.dismiss();
	}

	/**
	 * 显示对话框
	 */
	private void showPop() {
		if (popwindow != null) {
			popwindow.showAtLocation(bookDetail, Gravity.CENTER_VERTICAL
					| Gravity.CENTER_HORIZONTAL, 0, 0);
		}
	}

	@Override
	void findViews() {
		super.findViews();

		bookDetail = (LinearLayout) findViewById(R.id.bookDetail_lyt);
		collet_lyt = (LinearLayout) findViewById(R.id.collet_lyt);
		bookImg = (ImageView) findViewById(R.id.bookImg_iv);
		authorSumary_tv = (TextView) findViewById(R.id.authorSumary_tv);
		bookSumary = (TextView) findViewById(R.id.bookSumary_tv);
		title = (TextView) findViewById(R.id.bookName_tv);
		author = (TextView) findViewById(R.id.bookAuthor_tv);
		price = (TextView) findViewById(R.id.bookPrice_tv);
		publisher = (TextView) findViewById(R.id.bookPublisher_tv);
		grade = (TextView) findViewById(R.id.bookGrade_tv);
		wish = (Button) findViewById(R.id.wish_btn);
		reading = (Button) findViewById(R.id.reading_btn);
		done = (Button) findViewById(R.id.read_btn);
		delCollect = (Button) findViewById(R.id.delCollect_btn);
		comment_btn = (Button) findViewById(R.id.comment_btn);

		writeNote_btn = (Button) findViewById(R.id.writeNote_btn);
		writeComment_btn = (Button)findViewById(R.id.writeComment_btn);
	}

	@Override
	void initDatas() {
		super.initDatas();
		// initial msgHandler

		Looper looper = Looper.myLooper();
		msgHandler = new MessageHandler(looper);

		bookItem = (BookItem) getIntent().getSerializableExtra("bookItem");
		if (bookItem == null) {
			DebugUtils.d("TTT", "BOOKITEM IS NULL");
		}
		bookid = String.valueOf(bookItem.getId());
	}

	@Override
	void initWidgets() {
		super.initWidgets();

		imageDownloader.download(bookItem.getImages().getMedium(), bookImg,
				null);
		authorSumary_tv.setText("作者简介:" + bookItem.getAuthor_intro());
		bookSumary.setText("图书简介:" + bookItem.getSummary());

		title.setText(bookItem.getTitle());
		price.setText(bookItem.getPrice());
		publisher
				.setText(bookItem.getPublisher() + " " + bookItem.getPubdate());
		if (bookItem.getRating() != null)
			grade.setText(bookItem.getRating().getAverage() + "分 "
					+ bookItem.getRating().getNumRaters() + "人已评论");
		StringBuffer stringBuffer = new StringBuffer();
		for (String s : bookItem.getAuthor()) {
			stringBuffer.append(s);
			stringBuffer.append(" ");

		}
		author.setText(stringBuffer.toString());
		// get accessToken
		AccessToken accessToken = KeepToken.readAccessToken(context);
		// if not login do not show collect and wish status
		if (!notNull(accessToken.getToken())) {
			collet_lyt.setVisibility(View.GONE);
		} else {
			if (bookItem.getCurrent_user_collection() == null) {
				resetTextColor(wish, reading, done);
				wish.setTextColor(Color.GRAY);
			} else {
				String status = bookItem.getCurrent_user_collection()
						.getStatus();
				// 想读：wish 在读：reading 或 doing 读过：read 或 done）
				if (status.equals("wish")) {
					resetTextColor(wish, reading, done);
					hasCollecte = true;
				} else if (status.equals("reading") || status.equals("doing")) {
					resetTextColor(reading, wish, done);
					hasCollecte = true;
				} else if (status.equals("done") || status.equals("read")) {
					resetTextColor(done, wish, reading);
					hasCollecte = true;
				}
				hasCollectFirst = hasCollecte;
			}
			showDelete();
		}
	}

	/**
	 * 根据hasCollect 判断是否显示删除收藏按键
	 */
	void showDelete() {
		if (!hasCollecte)
			delCollect.setVisibility(View.GONE);
		else
			delCollect.setVisibility(View.VISIBLE);
	}

	// 将其他两个文字颜色为灰色
	void resetTextColor(Button btn1, Button btn2, Button btn3) {
		btn1.setTextColor(Color.BLACK);
		btn2.setTextColor(Color.GRAY);
		btn3.setTextColor(Color.GRAY);
	}

	@Override
	void initListners() {
		super.initListners();

		wish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popTitleStr = getPopTitle("想读");
				status = "wish";
				showPop();
			}
		});
		reading.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popTitleStr = getPopTitle("在读");
				status = "reading";
				showPop();
			}
		});
		done.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popTitleStr = getPopTitle("在读");
				status = "read";
				showPop();
			}
		});
		delCollect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 想读：wish 在读：reading 或 doing 读过：read 或 done）
				doubanBusiness.deleteCollectBook(bookid,
						new AsynCallback<DeleteSuccess>() {
							@Override
							public void onSuccess(DeleteSuccess data) {
								super.onSuccess(data);
								hasCollecte = false;
								Message msg = new Message();
								msg.arg1 = COLLETE_DELETE_SUCCESS;
								msgHandler.sendMessage(msg);
							}

							@Override
							public void onFailure(WrongMsg caught) {
								super.onFailure(caught);
								Message msg = new Message();
								msg.arg1 = COLLETE_DELETE_FAILURE;
								msg.obj = caught;
								msgHandler.sendMessage(msg);
							}
						});
			}
		});
		comment_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO add write comment activity and xml
				// here as test
				Intent intent = new Intent(context, CommentListActivity.class);
				intent.putExtra("bookItem", bookItem);
				startActivity(intent);
			}
		});
		writeNote_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, NoteEditActivity.class);
				intent.putExtra("bookItem", bookItem);
				startActivity(intent);
			}
		});
		
		writeComment_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CommentEditActivity.class);
				intent.putExtra("bookItem", bookItem);
				startActivity(intent);
			}
		});
	}

	String getPopTitle(String fix) {
		return fix + bookItem.getTitle();
	}

	void setResult() {
		if (hasCollectFirst != hasCollecte) {
			DebugUtils.d("NET", "in start finish");
			Intent intent = new Intent();
			intent.putExtra("isChanged", true);
			setResult(Activity.RESULT_OK, intent);
			DebugUtils.d("NET", "in end finish");
		}
	}

	// 不能正常使用,要监听BACK才可以
	@Override
	protected void onStop() {
		super.onStop();
		if (hasCollectFirst != hasCollecte) {
			DebugUtils.d("NET", "in start finish");
			Intent intent = new Intent();
			intent.putExtra("isChanged", true);
			setResult(Activity.RESULT_OK, intent);
			DebugUtils.d("NET", "in end finish");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult();
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}
}
