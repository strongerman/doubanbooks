package com.study.doubanbook_for_android.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ParseException;

import com.study.doubanbook_for_android.auth.AccessToken;
import com.study.doubanbook_for_android.auth.KeepToken;
import com.study.doubanbook_for_android.model.DeleteSuccess;
import com.study.doubanbook_for_android.utils.DebugUtils;
import com.study.doubanbook_for_android.utils.JsonUtil;

public class NetUtils {
	public static final int GET = 0;
	public static final int POST = 1;
	public static final int DELETE = 2;
	public static final int PUT = 3;

	/**
	 * 如果不是返回服务端,或者客服端的错误则在日志里面查看具体错误,用的是NET作为TAG
	 * 
	 * @param urls
	 *            suburl basic is known to us
	 * @param method
	 *            get or post
	 * @param keys
	 *            想要传递给服务器的键值列表
	 * @param values
	 *            想要传递给服务器的参数列表
	 * @return
	 */
	// TODO 2013-12-24 AFTER GET ACCESSTOKEN CHARGE NULL,IF NULL RETURN AND
	// REQUEST
	// USER TO
	// AUTH,ELSE RIGTH FLOW RELATIVED TO SEARCHINPUTACTIVITY.JAVA
	public static String getHttpEntity(String urls, int method,
			List<String> keys, List<String> values, Context context) {

		// super class of HttpGet ,HttpPost,HttpDelete ,HttpPut
		HttpRequestBase generalRequest = null;
		// response result
		HttpResponse httpResponse = null;
		// result StringBuffer
		StringBuffer result = new StringBuffer();

		//log out request urls
		DebugUtils.d("NET",urls);
		// initial the method
		switch (method) {
		case GET:
			generalRequest = new HttpGet(getUrlStr(urls, values, keys));
			break;
		case POST:
			generalRequest = new HttpPost(urls);
			try {
				((HttpPost) generalRequest).setEntity(new UrlEncodedFormEntity(
						getNameValuePair(keys, values), HTTP.UTF_8));
			} catch (UnsupportedEncodingException e1) {
				DebugUtils.d("NET", e1.getMessage());
				e1.printStackTrace();
			}
			break;
		case DELETE:
			generalRequest = new HttpDelete(urls);
			break;
		case PUT:
			generalRequest = new HttpPut(urls);
			try {
				((HttpPut) generalRequest).setEntity(new UrlEncodedFormEntity(
						getNameValuePair(keys, values), HTTP.UTF_8));
			} catch (UnsupportedEncodingException e1) {
				DebugUtils.d("NET", e1.getMessage());
				e1.printStackTrace();
			}
			break;
		default:
			break;
		}
		// add access token
		if (context != null) {
			AccessToken accessToken = KeepToken.readAccessToken(context);
			generalRequest.addHeader("Authorization",
					"Bearer " + accessToken.getToken());
			generalRequest.addHeader("access_token", accessToken.getToken());
		}
		// execute the request
		try {
			httpResponse = getNewHttpClient().execute(generalRequest);
		} catch (ClientProtocolException e) {
			DebugUtils.d("NET", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			DebugUtils.d("NET", e.getMessage());
			e.printStackTrace();
		}

		int statusCode = httpResponse.getStatusLine().getStatusCode();

		if (statusCode == HttpStatus.SC_OK) {
			try {
				result = new StringBuffer(EntityUtils.toString(httpResponse
						.getEntity()));
			} catch (ParseException e) {
				// log out the exception
				DebugUtils.d("NET", e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				DebugUtils.d("NET", e.getMessage());
				e.printStackTrace();
			}
		} else if (statusCode == 204) {// status = 204 无返回信息 如删除收藏图书 删除笔记
			// TODO add a delete success model and revert it to json
			DeleteSuccess del = new DeleteSuccess();
			del.setDelCode(200);
			del.setDelMessage("delete success");
			result = new StringBuffer(JsonUtil.toJsonObject(del));
		} else {
			// get the wrong message will return upstairs will charge in
			// business level
			// log out the status code and describe
			try {
				// log out the result,in business will be received by
				// WrongMsg model
				result = new StringBuffer(EntityUtils.toString(httpResponse
						.getEntity()));
			} catch (org.apache.http.ParseException e) {
				DebugUtils.d("NET", e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				DebugUtils.d("NET", e.getMessage());
				e.printStackTrace();
			}
			DebugUtils.d("NET", "status code:  "
					+ httpResponse.getStatusLine().getStatusCode());
			DebugUtils.d("NET", "status describe:  "
					+ httpResponse.getStatusLine().getReasonPhrase());
		}
		DebugUtils.d("NET", result.toString());
		return result.toString();
	}

	// ------other basic method
	/**
	 * attach params to url
	 * 
	 * @param urls
	 * @param values
	 * @param keys
	 * @return
	 */
	private static String getUrlStr(String urls, List<String> values,
			List<String> keys) {
		StringBuffer urlBuffer = new StringBuffer(urls);
		if (keys.size() == 0 || keys == null)
			return urlBuffer.toString();
		if (keys.size() >= 1) {
			// params start
			urlBuffer.append("?");
			urlBuffer.append(keys.get(0)).append("=").append(values.get(0));
			DebugUtils.d("NET", "parms" + (1) + ":" + keys.get(0) + "="
					+ values.get(0));
			for (int i = 1; i < keys.size(); i++) {
				// another key value pair and show the params
				urlBuffer.append("&");
				urlBuffer.append(keys.get(i)).append("=").append(values.get(i));
				DebugUtils.d("NET", "parms" + (i + 1) + ":" + keys.get(i) + "="
						+ values.get(i));
			}
		}
		return urlBuffer.toString();

	}

	/**
	 * for https or http connection defined a httpclient
	 * HTTPS:超文本安全传输协议，和HTTP相比，多了一个SSL/TSL的认证过程，端口为443
	 * 1.peer终端发送一个request，https服务端把支持的加密算法等以证书的形式返回一个身份信息（包含ca颁发机构和加密公钥等）。
	 * 2.获取证书之后，验证证书合法性。 3.随机产生一个密钥，并以证书当中的公钥加密。 4.request
	 * https服务端，把用公钥加密过的密钥传送给https服务端。 5.https服务端用自己的密钥解密，获取随机值。
	 * 6.之后双方传送数据都用此密钥加密后通信。
	 * 
	 * @return httpclient
	 */
	public static HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	private static List<NameValuePair> getNameValuePair(List<String> keys,
			List<String> values) {
		// 设置HTTP POST请求参数必须用NameValuePair对象
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (keys != null) {
			for (int i = 0; i < keys.size(); i++) {
				params.add(new BasicNameValuePair(keys.get(i), values.get(i)));
				DebugUtils.d("NET", "parms" + (i + 1) + ":" + keys.get(i) + "="
						+ values.get(i));
			}
		}
		return params;
	}
}
