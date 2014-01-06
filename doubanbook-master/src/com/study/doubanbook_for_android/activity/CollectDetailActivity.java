package com.study.doubanbook_for_android.activity;

import com.study.doubanbook_for_android.utils.DebugUtils;

import android.os.Bundle;

public class CollectDetailActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DebugUtils.e("CLASS", getClass().getName());
	}
}
