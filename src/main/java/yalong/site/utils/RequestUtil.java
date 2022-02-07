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
    private final String dataUrl = "https://127.0.0.1:2999/liveclientdata/allgamedata";
    private String baseUrl;
    private OkHttpClient client = myHttpClient();
    private Headers defaultHeaders;

    public RequestUtil(LeagueClientBO bo) {
        baseUrl = "https://127.0.0.1:" + bo.getPort();
        String basic = Credentials.basic("riot", bo.getToken());
        defaultHeaders = new Headers.Builder()
                .add("Content-Type", "'application/json")
                .add("Accept", "application/json")
                .add("Authorization", basic)
                .build();
    }

    public String doGet(String endpoint) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .get()
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
                .build();
    }

    private static SSLSocketFactory getSslSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
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
