package helper.http;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import helper.bo.LeagueClientBO;
import helper.enums.WSSEventEnum;
import helper.frame.utils.FrameTipUtil;
import helper.services.wss.WSSEventTrigger;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求lcu工具
 *
 * @author @_@
 */
@Data
@Slf4j
public class RequestLcuUtil {
	private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private String gateway = "127.0.0.1";
	private String defaultUrl;
	private OkHttpClient client = HttpClient.newInstance();
	private Map<String, String> defaultHeaders;
	private LeagueClientBO bo;

	public RequestLcuUtil(LeagueClientBO bo) {
		this.bo = bo;
		defaultUrl = "https://" + this.gateway + ":" + bo.getPort();
		buildLcuHeader();
		addDefaultHeaders();
		addRequestLog();
	}

	public void wss() {
		for (WSSEventEnum eventEnum : WSSEventEnum.values()) {
			Request request = new Request.Builder()
					.url("wss://" + this.gateway + ":" + bo.getPort())
					.build();
			client.newWebSocket(request, new WebSocketListener() {
				@Override
				public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
					log.info("关闭链接");
				}

				@Override
				public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
					log.info("正在关闭链接");
				}

				@Override
				public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
					if(t instanceof EOFException){
						FrameTipUtil.errorOccur("游戏已退出");
						System.exit(0);
					}
					log.error("发生错误,{},{}", t, response);

				}

				@Override
				public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
					if (!text.isEmpty()) {
						JSONArray eventArr = com.alibaba.fastjson2.JSON.parseArray(text);
						String data = eventArr.getJSONObject(2).getString("data");
						log.info("收到：{}的String消息:{}", eventArr.get(1).toString(), data);
						WSSEventTrigger.eventRun(eventEnum, data);
					}
				}

				@Override
				public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
					log.info("收到bytes消息:{}", bytes);
				}

				@Override
				public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
					JSONArray jsonArray = new JSONArray();
					jsonArray.add(5);
					jsonArray.add(eventEnum.getUri());
					String jsonString = jsonArray.toJSONString();
					webSocket.send(jsonString);
					log.info("打开链接:{}", eventEnum.getUri());
				}
			});
		}
	}

	private void addRequestLog() {
		client = client.newBuilder().addInterceptor(chain -> {
			Request original = chain.request();
			String method = original.method();
			String uri = original.url().uri().toString();
			String body = JSONObject.toJSONString(original.body());
			if (body != null && !"null".equals(body) && !"".equals(body)) {
				log.info("{} - {} - {}", method, uri, body);
			} else {
				log.info("{} - {}", method, uri);
			}

			return chain.proceed(original);
		}).build();
	}

	private void addDefaultHeaders() {
		client = client.newBuilder().addInterceptor(chain -> {
			Request original = chain.request();
			Request.Builder builder = original.newBuilder();
			this.defaultHeaders.forEach(builder::addHeader);
			Request build = builder.build();
			return chain.proceed(build);
		}).build();
	}

	private void buildLcuHeader() {
		HashMap<String, String> headers = new HashMap<>(3);
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		headers.put("Authorization", Credentials.basic("riot", this.bo.getToken()));
		this.defaultHeaders = headers;
	}

	public String callString(Request request) throws IOException {
		try (Response response = client.newCall(request).execute(); ResponseBody body = response.body()) {
			return body.string();
		}
	}

	public byte[] callStream(Request request) throws IOException {
		try (Response response = client.newCall(request).execute(); ResponseBody body = response.body()) {
			return body.bytes();
		}
	}

	public String doGet(String endpoint) throws IOException {
		Request request = new Request.Builder()
				.url(defaultUrl + endpoint)
				.get()
				.build();
		return this.callString(request);
	}

	public String doPut(String endpoint, String bodyStr) throws IOException {
		RequestBody body = RequestBody.create(bodyStr, JSON);
		Request request = new Request.Builder()
				.url(defaultUrl + endpoint)
				.put(body)
				.build();
		return this.callString(request);
	}

	public String doPost(String endpoint, String bodyStr) throws IOException {
		RequestBody body = RequestBody.create(bodyStr, JSON);
		Request request = new Request.Builder()
				.url(defaultUrl + endpoint)
				.post(body)
				.build();
		return this.callString(request);
	}

	public String doPatch(String endpoint, String bodyStr) throws IOException {
		RequestBody body = RequestBody.create(bodyStr, JSON);
		Request request = new Request.Builder()
				.url(defaultUrl + endpoint)
				.patch(body)
				.build();
		return this.callString(request);
	}

	/**
	 * 下载图片资源
	 * 本地没有则按照地址请求后缓存到本地 有则直接去读本地
	 *
	 * @param endpoint 资源请求路径
	 * @return InputStream
	 */
	public byte[] download(String endpoint) throws IOException {
		Request request = new Request.Builder()
				.url(defaultUrl + endpoint)
				.get()
				.build();
		return this.callStream(request);
	}
}
