package com.study.doubanbook_for_android.utils;


public class DebugUtils {
	private static final boolean DEBUG = true;

	public static void d(String tag, String content) {
		if (DEBUG)
			android.util.Log.d(tag, content);
	}

	public static void e(String tag, String content) {
		if (DEBUG)
			android.util.Log.e(tag, content);
	}

	public static void w(String tag, String content) {
		if (DEBUG)
			android.util.Log.w(tag, content);
	}

	public static void v(String tag, String content) {
		if (DEBUG)
			android.util.Log.v(tag, content);
	}

	public static void i(String tag, String content) {
		if (DEBUG)
			android.util.Log.i(tag, content);
	}

	public static void out(String content) {
		if (DEBUG)
			System.out.println(content);
	}

}
