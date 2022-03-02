package yalong.site.utils;

import lombok.Data;
import okhttp3.*;
import yalong.site.bo.LeagueClientBO;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 网络请求工具
 *
 * @author yaLong
 */
@Data
public class RequestUtil {
    private String defaultHost;
    private OkHttpClient client = myHttpClient();
    private Headers defaultHeaders;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public RequestUtil(LeagueClientBO bo) {
        defaultHost = "https://127.0.0.1:" + bo.getPort();
        String basic = Credentials.basic("riot", bo.getToken());
        defaultHeaders = new Headers.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .add("Authorization", basic)
                .build();
    }

    public String doGet(String host, String endpoint) throws IOException {
        Request request = new Request.Builder()
                .url(host + endpoint)
                .get()
                .headers(defaultHeaders)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String doGet(String endpoint) throws IOException {
        return this.doGet(defaultHost, endpoint);
    }

    public String doPost(String endpoint, String bodyStr) throws IOException {
        return this.doPost(defaultHost, endpoint, bodyStr);
    }

    public String doPut(String endpoint, String bodyStr) throws IOException {
        return this.doPut(defaultHost, endpoint, bodyStr);
    }

    public String doPut(String host, String endpoint, String bodyStr) throws IOException {
        RequestBody body = RequestBody.create(bodyStr, JSON);
        Request request = new Request.Builder()
                .url(host + endpoint)
                .put(body)
                .headers(defaultHeaders)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String doPost(String host, String endpoint, String bodyStr) throws IOException {
        RequestBody body = RequestBody.create(bodyStr, JSON);
        Request request = new Request.Builder()
                .url(host + endpoint)
                .post(body)
                .headers(defaultHeaders)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private OkHttpClient myHttpClient() {
        return new OkHttpClient.Builder()
                .sslSocketFactory(getSslSocketFactory(), getX509TrustManager())
                .hostnameVerifier(getHostnameVerifier())
                .retryOnConnectionFailure(Boolean.TRUE)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .protocols(Arrays.asList(Protocol.HTTP_1_1, Protocol.HTTP_2))
                .build();
    }

    private static SSLSocketFactory getSslSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TrustManager[] getTrustManager() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
    }

    public static HostnameVerifier getHostnameVerifier() {
        return (s, sslSession) -> true;
    }

    public static X509TrustManager getX509TrustManager() {
        X509TrustManager trustManager = null;
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            trustManager = (X509TrustManager) trustManagers[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trustManager;
    }

}
