package yalong.site.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yalong
 */
@Configuration
public class SwaggerConfig {

	@Value("${springdoc.description:${spring.application.name}}")
	private String description;
	@Value("${springdoc.title:${spring.application.name}}")
	private String title;
	@Value("${springdoc.version:v1}")
	private String version;

	@Bean
	public GlobalOpenApiCustomizer globalOpenApiCustomizer() {
		//把security设置到每个path里
		return openApi -> openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream()).forEach(operation -> operation.security(openApi.getSecurity()));
	}

	private Info info() {
		return new Info()
				.description(description)
				.title(title)
				.version(version);
	}

	@Bean
	public OpenAPI customConfig() {
		return new OpenAPI()
				.info(info());
	}
}
