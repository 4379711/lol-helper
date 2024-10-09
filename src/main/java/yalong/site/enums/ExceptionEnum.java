package yalong.site.enums;

import yalong.site.constant.ResultCode;

/**
 * @author yalong
 */

public enum ExceptionEnum implements ResultCode {
	/*系统控制*/
	COMMON_ERROR(60000, "普通错误"),
	UNKNOWN_ERROR(60001, "未知错误"),
	INTERNAL_SERVER_ERROR(60002, "服务内部错误"),
	TOO_MANY_REQUESTS(60003, "请求超过频率限制"),

	/*用户控制*/
	TOKEN_ERROR(61000, "令牌错误"),
	NOT_PERMISSION_ERROR(61001, "无权限"),
	LOGIN_NONE(61002, "未登录/登录已过期"),
	NOT_USER(61003, "用户不存在"),
	USER_EXIST(61004, "用户已存在"),
	NOT_PASSWORD(61005, "账号/密码不正确"),
	USER_STATUS_DISABLE(61006, "用户被禁用"),
	USER_STATUS_LOCK(61007, "用户被锁定5分钟"),
	USER_STATUS_OFFLINE(61008, "被挤下线"),

	/*数据库控制*/
	NO_DATASOURCE_ERROR(62000, "未知数据源"),
	DATA_NOT_EXIST(62001, "记录不存在"),
	DATA_EXIST(62002, "记录已存在"),
	MAPPER_PARAM_ERROR(62003, "mapper参数错误"),
	UPDATE_FAILURE(62004, "更新失败"),
	ADD_FAILURE(62005, "添加失败"),
	DELETE_FAILURE(62006, "删除失败"),
	SELECT_FAILURE(62007, "查询失败"),

	/*业务控制*/
	PARAM_ERROR(63000, "请求参数错误"),
	METHOD_NOT_ALLOWED(63001, "请求方式错误"),
	MEDIA_TYPE_ERROR(63002, "媒体类型错误"),

	;
	private final Integer code;

	private final String message;

	ExceptionEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public Integer getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
