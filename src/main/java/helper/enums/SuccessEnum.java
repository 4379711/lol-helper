package helper.enums;

import helper.constant.ResultCode;

/**
 * @author @_@
 */

public enum SuccessEnum implements ResultCode {
	SUCCESS(90000, "成功"),
	WARNING(90001, "警告"),

	;
	private final Integer code;

	private final String message;

	SuccessEnum(Integer code, String message) {
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
