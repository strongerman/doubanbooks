/*
 * UriUtils.java
 * classes : org.kevin.douban.util.UriUtils 
 * Created at : 2013-5-9 下午5:51:39
 */
package com.study.doubanbook_for_android.utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.study.doubanbook_for_android.auth.DoubanParameters;

import android.os.Bundle;
import android.util.Log;

/**
 * @author thinkfeed#gmail.com
 * 
 */
public class UriUtils {
	public static String encodeUrl(DoubanParameters parameters) {
		if (parameters == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (int loc = 0; loc < parameters.size(); loc++) {
			if (first) {
				first = false;
			} else {
				sb.append("&");
			}
			String _key = parameters.getKey(loc);
			String _value = parameters.getValue(_key);
			if (_value == null) {
				Log.i("encodeUrl", "key:" + _key + " 's value is null");
			} else {
				try {
					sb.append(URLEncoder.encode(parameters.getKey(loc), "utf-8")
							+ "="
							+ URLEncoder.encode(parameters.getValue(loc),
									"utf-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return sb.toString();
	}

	public static String encodeParameters(DoubanParameters httpParams) {
		if (null == httpParams || isBundleEmpty(httpParams)) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		int j = 0;
		for (int loc = 0; loc < httpParams.size(); loc++) {
			String key = httpParams.getKey(loc);
			if (j != 0) {
				buf.append("&");
			}
			try {
				buf.append(URLEncoder.encode(key, "UTF-8"))
						.append("=")
						.append(URLEncoder.encode(httpParams.getValue(key),
								"UTF-8"));
			} catch (java.io.UnsupportedEncodingException neverHappen) {
			}
			j++;
		}
		return buf.toString();

	}

	private static boolean isBundleEmpty(DoubanParameters bundle) {
		if (bundle == null || bundle.size() == 0) {
			return true;
		}
		return false;
	}

	public static Bundle parseUrl(String url) {
		try {
			URL u = new URL(url);
			Bundle b = decodeUrl(u.getQuery());
			b.putAll(decodeUrl(u.getRef()));
			return b;
		} catch (MalformedURLException e) {
			return new Bundle();
		}
	}

	public static Bundle decodeUrl(String s) {
		Bundle params = new Bundle();
		if (s != null) {
			String array[] = s.split("&");
			for (String parameter : array) {
				String v[] = parameter.split("=");
				try {
					params.putString(URLDecoder.decode(v[0], "utf-8"),
							URLDecoder.decode(v[1], "utf-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return params;
	}

}
