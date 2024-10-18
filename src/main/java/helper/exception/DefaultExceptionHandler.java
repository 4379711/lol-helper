package helper.exception;

import helper.constant.R;
import helper.enums.ExceptionEnum;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author @_@
 */
@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

	/**
	 * 基础异常
	 */
	@ExceptionHandler(BaseException.class)
	public R<?> baseException(BaseException e) {
		log.error(e.getMessage(), e);
		return R.fail(e);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public R<?> requestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
		log.error(e.getMessage(), e);
		return R.fail(ExceptionEnum.METHOD_NOT_ALLOWED, e.toString());
	}

	@ExceptionHandler({
			HttpMessageConversionException.class,
			MissingServletRequestParameterException.class,
			HttpMessageNotReadableException.class,
			MethodArgumentNotValidException.class,
			BindException.class,
			IllegalStateException.class,
			IllegalArgumentException.class,
			TypeMismatchException.class,
			ConversionNotSupportedException.class,
			ConstraintViolationException.class
	})
	public R<?> argument(Exception e) {
		log.error(e.getMessage(), e);
		return R.fail(ExceptionEnum.PARAM_ERROR, e.toString());
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public R<?> mediaType(Exception e) {
		log.error(e.getMessage(), e);
		return R.fail(ExceptionEnum.MEDIA_TYPE_ERROR, e.toString());
	}

	@ExceptionHandler(Exception.class)
	public R<?> handleException(Exception e) {
		log.error(e.getMessage(), e);
		return R.fail(ExceptionEnum.INTERNAL_SERVER_ERROR, e.toString());
	}

}
