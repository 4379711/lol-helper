package helper.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

/**
 * @author @_@
 */
@Configuration
public class ValidatorConfig {

	/**
	 * validation默认会校验完所有字段，然后返回所有的验证失败信息。
	 * 可以通过一些简单的配置，开启Fail Fast模式，只要有一个验证失败就立即返回
	 */
	@Bean
	public Validator validator(AutowireCapableBeanFactory springFactory) {
		try (ValidatorFactory validatorFactory = Validation
				.byProvider(HibernateValidator.class)
				.configure()
				// 快速失败
				.failFast(true)
				// 解决 SpringBoot 依赖注入问题
				.constraintValidatorFactory(new SpringConstraintValidatorFactory(springFactory))
				.buildValidatorFactory()) {
			return validatorFactory.getValidator();
		}
	}
}
