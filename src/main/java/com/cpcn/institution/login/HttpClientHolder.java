package com.cpcn.institution.login;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.SecureRandom;

public class HttpClientHolder {


    private static CloseableHttpClient client;
    private static CookieStore cookieStore;

    public static HttpClient getHttpsClient()  {
        if (null != client) {
            return client;
        }

        try {
            SSLContext sslContext = SSLContexts.custom().build();
            sslContext.init(null,new TrustManager[]{new HttpsTrustManager()},new SecureRandom());
            SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            cookieStore = new BasicCookieStore();
            client = HttpClients.custom().setDefaultCookieStore(cookieStore).setSSLSocketFactory(factory).build();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return client;

    }
    public static Cookie getCookie() {
        if (cookieStore == null) {
            return null;
        }
        return cookieStore.getCookies().get(0);
    }
}
