package helper.constant;

/**
 * 定义bean的优先级,使用注解 @Order(value),注意:value值越小,优先级越高 Interceptor -> interceptor ->
 * controllerAdvice -> aop
 *
 * @author @_@
 */

public interface BeanOrderConstants {
	int MYBATIS_ERROR_HANDLER = 100;
	int LIMITER_INTERCEPTOR = -1200;
	int SESSION_INTERCEPTOR = -1100;
	int AUTH_INTERCEPTOR = -1000;

}
