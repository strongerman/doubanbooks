package com.study.doubanbook_for_android.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.study.doubanbook_for_android.R;
import com.study.doubanbook_for_android.imagedownloader.ImageDownloader;
import com.study.doubanbook_for_android.utils.DebugUtils;
import com.study.doubanbook_for_android.utils.ToastUtils;

public class BaseActivity extends Activity {

	public final static int PAGE_COUNT = 10;
	protected ImageDownloader imageDownloader;
	protected Context context;
	// thread
	private BaseMessageHandler baseMsgHandle;

	class BaseMessageHandler extends Handler {
		public BaseMessageHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			baseSelfHandleMsg(msg);
		}
	}

	/**
	 * 利用MESSAGEHANDLER发送消息到UI线程
	 * 
	 * @param b
	 * @param status
	 */
	void baseSendMessage(Object b, int status) {
		Message message = Message.obtain();
		message.arg1 = status;
		message.obj = b;
		baseMsgHandle.sendMessage(message);
	}

	/**
	 * 怎样处理接受到的消息,自己覆盖方法判断使用
	 * 
	 * @param msg
	 */
	public void baseSelfHandleMsg(Message msg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bar_all);
		DebugUtils.d("TTT", "base Activity oncreate");
		context = this;
		imageDownloader = new ImageDownloader(this);
		// init baseMsgHandle
		Looper looper = Looper.myLooper();
		baseMsgHandle = new BaseMessageHandler(looper);

	}

	TextView title;
	Button back;

	void setNavagator(String searchContent) {
		title = (TextView) findViewById(R.id.title_tv);
		back = (Button) findViewById(R.id.back_btn);
		title.setText(searchContent);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResultThenFinish();
			}
		});
	}

	Button showComment_btn;

	void setRightButton() {
		showComment_btn = (Button) findViewById(R.id.showComment_btn);
//		showComment_btn.setVisibility(View.VISIBLE);
		showComment_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initRightListener();
			}
		});
	}

	void initRightListener() {

	}

	/**
	 * 若有必要时重写该方法添加其他内容
	 */
	void setResultThenFinish() {
		finish();
	}

	/**
	 * find the views in xml layout
	 */
	void findViews() {
		DebugUtils.d("TTT", "base Activity findview");
	}

	/**
	 * initial the data what you need use
	 */
	void initDatas() {
		DebugUtils.d("TTT", "base Activity initDatas");
	}

	/**
	 * initial the widget it should be when first show
	 */
	void initWidgets() {
		DebugUtils.d("TTT", "base Activity initwidgets");
	}

	/**
	 * initial the click listener of other listener
	 */
	void initListners() {
		DebugUtils.d("TTT", "base Activity initlisternser");
	}

	String getText(TextView tv) {
		return tv.getText().toString().trim();
	}

	/**
	 * 不为0
	 * 
	 * @param k
	 * @return
	 */
	boolean notZero(int k) {
		if (k != 0)
			return true;
		else
			return false;
	}

	/**
	 * 不为空并不为""空串
	 * 
	 * @param k
	 * @return
	 */
	boolean notNull(String k) {
		if (k != null && !k.equals(""))
			return true;
		else
			return false;
	}

	/**
	 * 显示简单文字提示
	 * 
	 * @param msg
	 */
	void toast(String msg) {
		ToastUtils.toast(context, msg);
	}
}
