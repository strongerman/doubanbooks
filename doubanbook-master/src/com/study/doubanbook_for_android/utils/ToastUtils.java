package com.study.doubanbook_for_android.utils;

import android.content.Context;

public class ToastUtils {
	public static final int LONG = android.widget.Toast.LENGTH_LONG;
	public static final int SHORT = android.widget.Toast.LENGTH_SHORT;

	public static void toast(Context context, String message) {
		android.widget.Toast.makeText(context, message, SHORT).show();
	}

}
