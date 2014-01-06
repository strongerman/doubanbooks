package com.study.doubanbook_for_android.activity;

import com.study.doubanbook_for_android.R;
import com.study.doubanbook_for_android.api.NetUtils;
import com.study.doubanbook_for_android.auth.Douban;
import com.study.doubanbook_for_android.auth.SimpleDoubanOAuthListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TextActivity1 extends Activity {

	private Button testbtn;
	private Button postTest;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading_page);
		postTest = (Button) findViewById(R.id.postTest);

		context = this;
		testbtn = (Button) findViewById(R.id.testbtn);
		testbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Douban douban = Douban.getInstance();
				douban.authorize(TextActivity1.this,
						new SimpleDoubanOAuthListener());
			}
		});

		// after auth
		postTest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread() {
					public void run() {
						NetUtils.getHttpEntity(
								"https://api.douban.com/v2/book/72719211/collection",
								NetUtils.POST, null, null, context);
					};
				}.start();

			}
		});
	}
}
