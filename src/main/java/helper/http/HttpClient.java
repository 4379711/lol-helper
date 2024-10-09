package helper.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;

import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author @_@
 */
@Slf4j
public class HttpClient {

	private static volatile OkHttpClient instance;

	private HttpClient() {
	}

	public static OkHttpClient newInstance() {
		if (instance == null) {
			synchronized (HttpClient.class) {
				if (instance == null) {
					instance = myHttpClient();
				}
			}
		}
		return instance;

	}

	private static OkHttpClient myHttpClient() {
		return new OkHttpClient.Builder()
				.sslSocketFactory(getSslSocketFactory(), getX509TrustManager())
				.hostnameVerifier(getHostnameVerifier())
				.retryOnConnectionFailure(Boolean.TRUE)
				.connectTimeout(30, TimeUnit.SECONDS)
				.connectionPool(new ConnectionPool(10, 10, TimeUnit.SECONDS))
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
			assert trustManagers.length == 1;
			assert trustManagers[0] instanceof X509TrustManager;
			trustManager = (X509TrustManager) trustManagers[0];
		} catch (Exception e) {
			log.error("X509TrustManager配置错误", e);
		}
		return trustManager;
	}

}
