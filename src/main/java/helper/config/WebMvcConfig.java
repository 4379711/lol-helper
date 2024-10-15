package helper.config;

import helper.Interceptor.AssetsInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * @author @_@
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Resource
	private AssetsInterceptor assetsInterceptor;
	/**
	 * 跨域支持
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOriginPatterns("*")
				.allowCredentials(true)
				.allowedMethods("*")
				.maxAge(3600 * 24);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String resourcePath = "file:" + System.getProperty("user.dir") + "/lol-game-data/";
		System.out.println("Resource location: " + resourcePath); // Debugging line
		registry.addResourceHandler("/lol-game-data/**")
				.addResourceLocations(resourcePath)
				.setCachePeriod(3600) // Optional: Cache configuration
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(assetsInterceptor)
				.addPathPatterns("/lol-game-data/**"); // 拦截所有请求
	}

}
