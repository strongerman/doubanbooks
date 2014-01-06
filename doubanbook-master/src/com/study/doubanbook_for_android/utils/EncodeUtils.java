package com.study.doubanbook_for_android.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class EncodeUtils {
	public static String encoder(String value) {
		// 转中文
		String enUft = "";
		String str = null;
		try {
			enUft = URLEncoder.encode(value, "UTF-8");
			str = URLDecoder.decode(enUft, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String getEncodedStr(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}