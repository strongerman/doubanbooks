/*
 * DoubanRequest.java
 * classes : org.kevin.douban.auth.DoubanRequest 
 * Created at : 2013-5-14 上午10:55:12
 */
package com.study.doubanbook_for_android.auth;

import com.study.doubanbook_for_android.api.NetUtils;
import com.study.doubanbook_for_android.utils.JsonUtil;

/**
 * 
 * the request for login or auth
 */
public class DoubanRequest {
	/**
	 * 获取Access Token
	 * 豆瓣的Content-Type是Text/Html，直接使用GsonHttpMessageConverter会导致异常
	 * 
	 * @param params
	 * @return
	 */
	public static Token requestAccessToken(DoubanParameters params) {
		String s = NetUtils.getHttpEntity(Douban.URL_OAUTH2_ACCESS_TOKEN,
				NetUtils.POST, params.getmKeys(), params.getmValues(), null);
		return JsonUtil.fromJsonObject(s, Token.class);
	}

	/**
	 * TODO think
	 * may i should use the spring frame to simplify the net access?
	 * @param params
	 * @return
	 */
	public static Token requestAccessToken1(DoubanParameters params) {
		//int statusCode = 0;
		// try {
		// RestTemplate restTemplate = new RestTemplate();
		// restTemplate.getMessageConverters().add(
		// new StringHttpMessageConverter());
		// HttpHeaders httpHeaders = new HttpHeaders();
		// httpHeaders.setAccept(Collections.singletonList(new MediaType(
		// "application", "json", Charset.forName("utf-8"))));
		// HttpEntity<?> requestEntity = new HttpEntity<Object>(httpHeaders);
		// ResponseEntity<String> respone = restTemplate.exchange(
		// Douban.URL_OAUTH2_ACCESS_TOKEN.concat("?").concat(
		// UriUtils.encodeParameters(params)),
		// HttpMethod.POST, requestEntity, String.class);
		// statusCode = respone.getStatusCode().value();
		// if (statusCode == 200) {
		// return JsonUtil.fromJsonObject(respone.getBody(), Token.class);
		// } else {
		// return null;
		// }
		// } catch (RestClientException e) {
		// Log.d("douban-request", "status code :" + statusCode
		// + " exception msg:" + e.getMessage());
		// }
		return null;
	}
}
