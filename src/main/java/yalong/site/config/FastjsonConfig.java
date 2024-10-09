package yalong.site.config;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class FastjsonConfig implements WebMvcConfigurer {

	private FastJsonConfig fastJsonConfig() {
		FastJsonConfig config = new FastJsonConfig();

		config.setDateFormat("yyyy-MM-dd HH:mm:ss");
		config.setCharset(StandardCharsets.UTF_8);
		config.setWriterFeatures(
				JSONWriter.Feature.WriteNullListAsEmpty,
				//json格式化
				JSONWriter.Feature.PrettyFormat,
				//输出map中value为null的数据
				JSONWriter.Feature.WriteMapNullValue,
				//输出boolean 为 false
				JSONWriter.Feature.WriteNullBooleanAsFalse,
				//输出list 为 []
				JSONWriter.Feature.WriteNullListAsEmpty,
				//输出number 为 0
				JSONWriter.Feature.WriteNullNumberAsZero,
				//输出字符串 为 ""
				JSONWriter.Feature.WriteNullStringAsEmpty,
				// 解决big decimal转为科学计数法的问题
				JSONWriter.Feature.WriteBigDecimalAsPlain
		);
		return config;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		converter.setFastJsonConfig(fastJsonConfig());
		converters.add(0, converter);
	}
}
