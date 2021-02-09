package com.cpcn.institution.login;

import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpsClientRequestFactory extends SimpleClientHttpRequestFactory {


    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (!(connection instanceof HttpsURLConnection)) {
            // http
            super.prepareConnection(connection,httpMethod);
        }
        try {
        if (connection instanceof  HttpsURLConnection) {
            // https

                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());

                TrustStrategy anyTrustStrategy = new TrustStrategy() {
                    @Override
                    public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        return true;
                    }
                };
            SSLContext ctx = SSLContexts.custom().loadTrustMaterial(trustStore, anyTrustStrategy).build();
            ((HttpsURLConnection) connection).setSSLSocketFactory(ctx.getSocketFactory());
            HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
            super.prepareConnection(httpsConnection, httpMethod);
        }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
