package yalong.site.http;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import yalong.site.cache.AppCache;
import yalong.site.frame.constant.GameConstant;
import yalong.site.exception.NoLcuApiException;
import yalong.site.services.sgp.RegionSgpApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求sgp工具
 *
 * @author WuYi
 */
@Data
@Slf4j
public class RequestSgpUtil {
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = HttpClient.newInstance();
    private Map<String, String> defaultHeaders;
    private String RsoPlatformId;
    private String defaultUrl;
    /**
     * 所有大区的sgp地址
     */
    public final List<String> allRegionUrl = new ArrayList<String>() {{
        for (String s : GameConstant.REGION) {
            add(buildUrl(s));
        }
    }};

    public RequestSgpUtil(String token, String rsoPlatformId) {
        defaultUrl = buildUrl(rsoPlatformId);
        RsoPlatformId = rsoPlatformId;
        buildSgpHeaders(token);
        addRequestLog();
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

    private void buildSgpHeaders(String token) {
        defaultHeaders = new HashMap<>(1);
        defaultHeaders.put("Authorization", "Bearer " + token);
        client = HttpClient.newInstance().newBuilder().addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder();
            this.defaultHeaders.forEach(builder::addHeader);
            Request build = builder.build();
            return chain.proceed(build);
        }).build();
    }

    public String callString(Request request) throws IOException {
        try (Response response = client.newCall(request).execute(); ResponseBody body = response.body()) {
            if (response.code() == 200) {
                return body.string();
            } else if (response.code() == 401) {
                String sgpAccessToken = AppCache.api.getSgpAccessToken();
                buildSgpHeaders(sgpAccessToken);
                String twiceCall = this.callString(request);
                buildNewToken(sgpAccessToken);
                return twiceCall;
            }
            return "";
        }
    }

    public byte[] callStream(Request request) throws IOException {
        try (Response response = client.newCall(request).execute(); ResponseBody body = response.body()) {
            if (response.code() == 200) {
                return body.bytes();
            } else if (response.code() == 401) {
                String sgpAccessToken = AppCache.api.getSgpAccessToken();
                buildSgpHeaders(sgpAccessToken);
                byte[] twiceCall = callStream(request);
                buildNewToken(sgpAccessToken);
                return twiceCall;
            }
            return null;
        }
    }

    public String doGet(String endpoint) throws IOException {
        Request request = new Request.Builder()
                .url(defaultUrl + endpoint)
                .get()
                .build();
        return this.callString(request);
    }

    public String doGet(String endpoint, String region) throws IOException {
        Request request = new Request.Builder()
                .url(buildUrl(region) + endpoint)
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

    public String doPost(String endpoint, String bodyStr, String region) throws IOException {
        RequestBody body = RequestBody.create(bodyStr, JSON);
        Request request = new Request.Builder()
                .url(buildUrl(region) + endpoint)
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
     * 构建各个地区的sgp请求地址
     * 艾欧尼亚和黑色玫瑰和峡谷之巅未未合区地址不一样
     *
     * @param region 大区
     */
    public String buildUrl(String region) {
        String s = region.toLowerCase();
        if (s.equals("hn1") || s.equals("hn10") || s.equals("bgp2")) {
            return "https://" + s + "-k8s-sgp.lol.qq.com:21019";
        }
        return "https://" + s + "-sgp.lol.qq.com:21019";

    }

    /**
     * 构建新的sgpapi到缓存
     */
    private void buildNewToken(String sgpAccessToken) {
        try {
            RequestSgpUtil requestUtil = new RequestSgpUtil(AppCache.api.getSgpAccessToken(), getRsoPlatformId());
            AppCache.sgpApi = new RegionSgpApi(requestUtil);
            buildSgpHeaders(sgpAccessToken);
        } catch (IOException e) {
            throw new NoLcuApiException();
        }
    }
}
