package com.study.doubanbook_for_android.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
import com.study.doubanbook_for_android.utils.DebugUtils;

public class CopyOfNetUtils {
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

		HttpRequestBase generalRequest = null;
		HttpResponse httpResponse = null;
		
		StringBuffer result = new StringBuffer();
		switch (method) {
		case GET:
			HttpGet httpGet = new HttpGet(getUrlStr(urls, values, keys));
			generalRequest = new HttpGet(getUrlStr(urls, values, keys));
			

			// add access_token if need,just send context
			if (context != null) {
				AccessToken accessToken = KeepToken.readAccessToken(context);
				httpGet.addHeader("access_token", accessToken.getToken());
			}
			try {
				httpResponse = getNewHttpClient().execute(httpGet);
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
			} else {
				// get the wrong msg will return upstairs will charge
				// log out the status code and describe
				try {
					// log out the result,in business will be received by
					// wrongMsg model
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
			break;
		case POST:
			HttpPost httpPost = new HttpPost(urls);
			try {
				// 设置httpPost请求参数
				DebugUtils.d("NET", "POST " + urls);
				// add access_token if need,just send context to this is ok
				if (context != null) {
					AccessToken accessToken1 = KeepToken
							.readAccessToken(context);
					httpPost.addHeader("Authorization", "Bearer "
							+ accessToken1.getToken());
					httpPost.addHeader("access_token", accessToken1.getToken());
				}
				httpPost.setEntity(new UrlEncodedFormEntity(getNameValuePair(
						keys, values), HTTP.UTF_8));
				// here remember user New HttpClient with SSL protocol
				httpResponse = getNewHttpClient().execute(httpPost);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					result = new StringBuffer(EntityUtils.toString(httpResponse
							.getEntity()));
				} else {
					// get the wrong msg will return upstairs will charge
					// log out the status code and describe
					try {
						// log out the result,in business will be received by
						// wrongMsg model
						result = new StringBuffer(
								EntityUtils.toString(httpResponse.getEntity()));
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
			} catch (ClientProtocolException e) {
				DebugUtils.d("NET", e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				DebugUtils.d("NET", e.getMessage());
				e.printStackTrace();
			}
			break;

		case DELETE:
			HttpPost httpDelete = new HttpPost(urls);
			try {
				// 设置httpPost请求参数
				DebugUtils.d("NET", "DELETE " + urls);
				// add access_token if need,just send context to this is ok
				if (context != null) {
					AccessToken accessToken1 = KeepToken
							.readAccessToken(context);
					httpDelete.addHeader("access_token",
							accessToken1.getToken());
				}
				httpDelete.setEntity(new UrlEncodedFormEntity(getNameValuePair(
						keys, values), HTTP.UTF_8));
				// here remember user New HttpClient with SSL protocol
				httpResponse = getNewHttpClient().execute(httpDelete);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					result = new StringBuffer(EntityUtils.toString(httpResponse
							.getEntity()));
				} else {
					// get the wrong msg will return upstairs will charge
					// log out the status code and describe
					try {
						// log out the result,in business will be received by
						// wrongMsg model
						result = new StringBuffer(
								EntityUtils.toString(httpResponse.getEntity()));
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
			} catch (ClientProtocolException e) {
				DebugUtils.d("NET", e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				DebugUtils.d("NET", e.getMessage());
				e.printStackTrace();
			}
			break;

		/**
		 * 这个不用使用SSL协议的客服端认证,以前的使用方便现在放弃 updatatime: 2013-12-13
		 */
		// TODO add delete and put http... then the NetUtils is done
		case 3 /* old get */:
			try {
				URL url = new URL(getUrlStr(urls, values, keys));
				URLConnection connection = url.openConnection();
				String line;
				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream(), "UTF-8"));
				while ((line = in.readLine()) != null) {
					result.append(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
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
		DebugUtils.d("NET", "url:" + urlBuffer.toString());
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
		if (keys != null)
			for (int i = 0; i < keys.size(); i++) {
				params.add(new BasicNameValuePair(keys.get(i), values.get(i)));
				DebugUtils.d("NET", "parms" + (i + 1) + ":" + keys.get(i) + "="
						+ values.get(i));
			}
		return params;
	}
}
