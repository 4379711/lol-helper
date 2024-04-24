package yalong.site.http;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import yalong.site.bo.LeagueClientBO;

import java.io.IOException;

/**
 * 请求lcu工具
 *
 * @author yaLong
 */
@Data
@Slf4j
public class RequestLcuUtil {
	private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private String defaultHost;
	private OkHttpClient client = HttpClient.newInstance();
	private Headers defaultHeaders;

	public RequestLcuUtil(LeagueClientBO bo) {
		defaultHost = "https://127.0.0.1:" + bo.getPort();
		Headers defaultHeaders = buildLcuHeader(bo.getToken());
		addDefaultHeaders(defaultHeaders);
		addRequestLog();
	}

	private void addRequestLog() {
		client.interceptors().add(new Interceptor() {
			@NotNull
			@Override
			public Response intercept(@NotNull Chain chain) throws IOException {
				Request original = chain.request();
				String method = original.method();
				String uri = original.url().uri().toString();
				String body = JSONObject.toJSONString(original.body());
				log.info("{} - {} - {}", method, uri, body);
				return chain.proceed(original);
			}
		});
	}

	private void addDefaultHeaders(Headers defaultHeaders) {
		client.interceptors().add(new Interceptor() {
			@NotNull
			@Override
			public Response intercept(@NotNull Chain chain) throws IOException {
				Request original = chain.request();
				Request build = original.newBuilder()
						.headers(defaultHeaders)
						.build();
				return chain.proceed(build);
			}
		});
	}

	private Headers buildLcuHeader(String token) {
		String basic = Credentials.basic("riot", token);
		return new Headers.Builder()
				.add("Content-Type", "application/json")
				.add("Accept", "application/json")
				.add("Authorization", basic)
				.build();
	}

	public String doGet(String endpoint) throws IOException {
		Request request = new Request.Builder()
				.url(defaultHost + endpoint)
				.get()
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public String doPut(String endpoint, String bodyStr) throws IOException {
		RequestBody body = RequestBody.create(bodyStr, JSON);
		Request request = new Request.Builder()
				.url(defaultHost + endpoint)
				.put(body)
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public String doPost(String endpoint, String bodyStr) throws IOException {
		RequestBody body = RequestBody.create(bodyStr, JSON);
		Request request = new Request.Builder()
				.url(defaultHost + endpoint)
				.post(body)
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public String doPatch(String endpoint, String bodyStr) throws IOException {
		RequestBody body = RequestBody.create(bodyStr, JSON);
		Request request = new Request.Builder()
				.url(defaultHost + endpoint)
				.patch(body)
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

}
