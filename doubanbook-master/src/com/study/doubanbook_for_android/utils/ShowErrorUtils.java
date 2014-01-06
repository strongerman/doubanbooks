package com.study.doubanbook_for_android.utils;

import com.study.doubanbook_for_android.api.WrongMsg;

import android.content.Context;
import android.os.Message;

/**
 * 
 * 管理豆瓣的所有错误信息 错误码 错误信息 含义 status code 999 unknow_v2_error 未知错误 400 1000
 * need_permission 需要权限 403 1001 uri_not_found 资源不存在 404 1002 missing_args 参数不全
 * 400 1003 image_too_large 上传的图片太大 400 1004 has_ban_word 输入有违禁词 400 1005
 * input_too_short 输入为空，或者输入字数不够 400 1006 target_not_fount
 * 相关的对象不存在，比如回复帖子时，发现小组被删掉了 400 1007 need_captcha 需要验证码，验证码有误 403 1008
 * image_unknow 不支持的图片格式 400 1009 image_wrong_format
 * 照片格式有误(仅支持JPG,JPEG,GIF,PNG或BMP) 400 1010 image_wrong_ck 访问私有图片ck验证错误 403 1011
 * image_ck_expired 访问私有图片ck过期 403 1012 title_missing 题目为空 400 1013 desc_missing
 * 描述为空 400
 *  6000	book_not_found				图书不存在	404
	6002	unauthorized_error			没有修改权限	403
	6004	review_content_short(should more than 150)	书评内容过短（需多于150字）	400
	6006	review_not_found			书评不存在	404
	6007	not_book_request			不是豆瓣读书相关请求	403
	6008	people_not_found			用户不存在	404
	6009	function_error				服务器调用异常	400
	6010	comment_too_long(should less than 350)					短评字数过长（需少于350字）	400
	6011	collection_exist(try PUT if you want to update)		该图书已被收藏（如需更新请用PUT方法而不是POST）	409
	6012	invalid_page_number(should be digit less than 1000000)	非法页码（页码需要是小于1000000的数字）	400
	6013	chapter_too_long(should less than 100)				章节名过长（需小于100字）	400
 * @author tezuka-pc
 * 
 */
public class ShowErrorUtils {
	public static void showWrongMsg(Context context, Message message) {
		WrongMsg wrongMsg = (WrongMsg) message.obj;
		String wrongMsgStr = null;
		int code = wrongMsg.getCode();
		switch (code) {
		case 999:
			wrongMsgStr = "未知错误";
			break;
		case 1000:
			wrongMsgStr = "需要权限";
			break;
		case 1001:
			wrongMsgStr = "资源不存在";
			break;
		case 1002:
			wrongMsgStr = "参数不全";
			break;
		case 1003:
			wrongMsgStr = "上传的图片太大";
			break;
		case 1004:
			wrongMsgStr = "输入有违禁词";
			break;
		case 1005:
			wrongMsgStr = "输入为空，或者输入字数不够";
			break;
		case 1006:
			wrongMsgStr = "相关的对象不存在，比如回复帖子时，发现小组被删掉了";
			break;
		case 1007:
			wrongMsgStr = "需要验证码，验证码有误";
			break;
		case 1008:
			wrongMsgStr = "不支持的图片格式";
			break;
		case 1009:
			wrongMsgStr = "照片格式有误(仅支持JPG,JPEG,GIF,PNG或BMP)";
			break;
		case 1010:
			wrongMsgStr = "访问私有图片ck验证错误";
			break;
		case 1011:
			wrongMsgStr = "访问私有图片ck过期";
			break;
		case 1012:
			wrongMsgStr = "题目为空";
			break;
		case 1013:
			wrongMsgStr = "描述为空";
			break;
		default:
			wrongMsgStr = wrongMsg.getMsg();
			break;
		}

		ToastUtils.toast(context, wrongMsgStr);
	}
}
