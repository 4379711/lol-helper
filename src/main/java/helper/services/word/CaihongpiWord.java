package helper.services.word;

import com.alibaba.fastjson2.JSONObject;
import helper.http.HttpClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author @_@
 */
@Slf4j
public class CaihongpiWord {
	public static String requestCaiHongPiText() {
		log.info("开始请求彩虹屁接口");
		Request request = new Request.Builder()
				.url("https://api.shadiao.pro/chp")
				.get()
				.build();
		try {
			Response response = HttpClient.newInstance().newCall(request).execute();
			JSONObject jsonObject = JSONObject.parseObject(response.body().string());
			return jsonObject.getJSONObject("data").getString("text");
		} catch (Exception e) {
			log.error("彩虹屁接口错误", e);
		}
		return null;
	}

}
