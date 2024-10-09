package helper.constant;

import helper.enums.ExceptionEnum;
import helper.enums.SuccessEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author @_@
 */
@Data
public class R<T> implements Serializable {

	private int code;

	private String msg;

	private T data;

	public static <T> R<T> judge(boolean status) {
		if (status) {
			return ok();
		} else {
			return fail(ExceptionEnum.UPDATE_FAILURE);
		}
	}

	public static <T> R<T> ok() {
		return ok(SuccessEnum.SUCCESS);
	}

	public static <T> R<T> ok(T data) {
		return innerWarp(data, SuccessEnum.SUCCESS.getCode(), SuccessEnum.SUCCESS.getMessage());
	}

	public static <T> R<T> ok(T data, String msg) {
		return innerWarp(data, SuccessEnum.SUCCESS.getCode(), msg);
	}

	public static <T> R<T> ok(ResultCode result) {
		return innerWarp(null, result.getCode(), result.getMessage());
	}

	public static <T> R<T> fail() {
		return fail(ExceptionEnum.COMMON_ERROR);
	}

	public static <T> R<T> fail(T data) {
		return innerWarp(data, ExceptionEnum.COMMON_ERROR.getCode(), ExceptionEnum.COMMON_ERROR.getMessage());
	}

	public static <T> R<T> fail(String msg) {
		return innerWarp(null, ExceptionEnum.COMMON_ERROR.getCode(), msg);
	}

	public static <T> R<T> fail(ResultCode result) {
		return innerWarp(null, result.getCode(), result.getMessage());
	}

	public static <T> R<T> fail(ResultCode result, String msg) {
		return innerWarp(null, result.getCode(), msg);
	}

	private static <T> R<T> innerWarp(T data, int code, String msg) {
		R<T> apiResult = new R<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		return apiResult;
	}
}
