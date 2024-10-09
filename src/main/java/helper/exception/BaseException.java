package helper.exception;

import helper.constant.ResultCode;
import helper.enums.ExceptionEnum;

/**
 * 基础异常
 *
 * @author @_@
 */
public class BaseException extends RuntimeException implements ResultCode {

	/**
	 * 错误码
	 */
	private Integer code;

	/**
	 * 错误消息
	 */
	private String message;

	public BaseException(String msg) {
		this.code = ExceptionEnum.UNKNOWN_ERROR.getCode();
		this.message = msg;
	}

	public BaseException(ExceptionEnum exceptionEnum) {
		this.code = exceptionEnum.getCode();
		this.message = exceptionEnum.getMessage();
	}

	@Override
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
