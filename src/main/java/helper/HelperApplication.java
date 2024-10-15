package helper;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author @_@
 */
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableAsync
public class HelperApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(HelperApplication.class).headless(false).run(args);
	}

}
