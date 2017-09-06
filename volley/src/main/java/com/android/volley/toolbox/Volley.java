/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.volley.toolbox;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class Volley {

    private static final String load = "-----BEGIN CERTIFICATE-----\n" +
            "MIIGMDCCBRigAwIBAgIQW005Kkr+XM5g9jGh/wdUpTANBgkqhkiG9w0BAQsFADB+\n" +
            "MQswCQYDVQQGEwJVUzEdMBsGA1UEChMUU3ltYW50ZWMgQ29ycG9yYXRpb24xHzAd\n" +
            "BgNVBAsTFlN5bWFudGVjIFRydXN0IE5ldHdvcmsxLzAtBgNVBAMTJlN5bWFudGVj\n" +
            "IENsYXNzIDMgU2VjdXJlIFNlcnZlciBDQSAtIEc0MB4XDTE3MDUyNTAwMDAwMFoX\n" +
            "DTE4MDUyNTIzNTk1OVowgagxCzAJBgNVBAYTAkNOMRIwEAYDVQQIDAnkuIrmtbfl\n" +
            "uIIxEjAQBgNVBAcMCeS4iua1t+W4gjEqMCgGA1UECgwh6YeR5YWD5a6d572R57uc\n" +
            "56eR5oqA5pyJ6ZmQ5YWs5Y+4MSowKAYDVQQLDCHph5HlhYPlrp3nvZHnu5znp5Hm\n" +
            "ioDmnInpmZDlhazlj7gxGTAXBgNVBAMMECouYmFpYmFvYmlrZS5jb20wggEiMA0G\n" +
            "CSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCpN1lAW6Xip+tgDleWVJ+gqQwpmvxI\n" +
            "NeNddBYKyM23rMSq6KR3ZgwesZBIbjSpHbL11C7BCY8nYhR3P6hdc4rIWqPPLyKX\n" +
            "cBfoKEF1yLBFoMvSNK8y1/BAF67VhyyzeCLp8HopNoSyTnaVO8bE9RqH+G9HyHww\n" +
            "JMNa5TIvbw3VgFlXdqbh8berX/BXCPHeELL3GOtyhBxLctGN8/9JoPVcxV5+nfeb\n" +
            "vZgu5U2B3qF7cZQEITrQBdLfL5MZafllJQWIM6TymkVg4dsTLW/EMH2JChqyMk8I\n" +
            "UfFqJ1lH9okiKIXr8hzgVtjMuKfVSHFkBYUoMrBiqbJ3WdSJHxwMx5ztAgMBAAGj\n" +
            "ggJ9MIICeTArBgNVHREEJDAighAqLmJhaWJhb2Jpa2UuY29tgg5iYWliYW9iaWtl\n" +
            "LmNvbTAJBgNVHRMEAjAAMA4GA1UdDwEB/wQEAwIFoDArBgNVHR8EJDAiMCCgHqAc\n" +
            "hhpodHRwOi8vc3Muc3ltY2IuY29tL3NzLmNybDBhBgNVHSAEWjBYMFYGBmeBDAEC\n" +
            "AjBMMCMGCCsGAQUFBwIBFhdodHRwczovL2Quc3ltY2IuY29tL2NwczAlBggrBgEF\n" +
            "BQcCAjAZDBdodHRwczovL2Quc3ltY2IuY29tL3JwYTAdBgNVHSUEFjAUBggrBgEF\n" +
            "BQcDAQYIKwYBBQUHAwIwHwYDVR0jBBgwFoAUX2DPYZBV34RDFIpgKrL1evRDGO8w\n" +
            "VwYIKwYBBQUHAQEESzBJMB8GCCsGAQUFBzABhhNodHRwOi8vc3Muc3ltY2QuY29t\n" +
            "MCYGCCsGAQUFBzAChhpodHRwOi8vc3Muc3ltY2IuY29tL3NzLmNydDCCAQQGCisG\n" +
            "AQQB1nkCBAIEgfUEgfIA8AB2AN3rHSt6DU+mIIuBrYFocH4ujp0B1VyIjT0RxM22\n" +
            "7L7MAAABXD2HYRUAAAQDAEcwRQIhAPYpbnTPYpwd0fbemZzvzlsUbkPuhv3dA8MP\n" +
            "AyBX3m72AiB6haiP83zVIub3zq+olowvLGvbsnGH05TPStOMcRnGtgB2AKS5CZC0\n" +
            "GFgUh7sTosxncAo8NZgE+RvfuON3zQ7IDdwQAAABXD2HYVQAAAQDAEcwRQIgcmD2\n" +
            "mCXj33XZklUDbQDwPGXu1nZDmvr9IFomZAAbh7MCIQCwKLvRO9yTnW5u3J4ZH0Xd\n" +
            "IpAcJnI3ZdLu+xcqNxA8jDANBgkqhkiG9w0BAQsFAAOCAQEAmU1zSciN+XBB+O+i\n" +
            "IMReN415SmV6WkKHoh5k82nDW4SmrGai3ikMhoCawmlt7GrkAxJd7M9uEOf9FF0l\n" +
            "Pdx/iL0UoJGwbAQ4A5LiZu6EL9jcilguoctbzcuaDY+MuhkQKj0MY3RMWxMvREI0\n" +
            "lXhWEXqZKOrPtYN7p5jP1jHWPf/A0+HM8YzACOBJiKE8vDW5NU9MwNOGjogQ1Kr7\n" +
            "4xQnjKVVazG/ykxl1bnfgkmcgDlSq832fHsH7qP+PLypMX3Uw6RK0tFB2+z6XbNJ\n" +
            "wIz9huZ+sM2OOoalMLgoQsrokPDUKM85ZUy2a3DyihKi2t3W8hh1Y2b9w7tOO0G5\n" +
            "/MN50A==\n" +
            "-----END CERTIFICATE-----";

    /**
     * Default on-disk cache directory.
     */
    private static final String DEFAULT_CACHE_DIR = "volley";
    private static BasicNetwork network;
    private static RequestQueue queue;

    private Context mContext;

    /**
     * Creates a default instance of the worker pool and calls
     * {@link RequestQueue#start()} on it.
     *
     * @param context A {@link Context} to use for creating the cache dir.
     * @param stack   An {@link HttpStack} to use for the network, or null for
     *                default.
     * @return A started {@link RequestQueue} instance.
     */
    public static RequestQueue newRequestQueue(Context context,
                                               HttpStack stack, boolean selfSignedCertificate) {
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);

        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (NameNotFoundException e) {
        }

        if (stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                if (selfSignedCertificate) {
                    stack = new HurlStack(null, buildSSLSocketFactory(context));
                } else {
                    stack = new HurlStack();
                }
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See:
                // http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                if (selfSignedCertificate)
                    stack = new HttpClientStack(getHttpClient(context));
                else {
                    stack = new HttpClientStack(
                            AndroidHttpClient.newInstance(userAgent));
                }
            }
        }

        //此处是坑啊，network和queue 是static的，不用一直new
        if (network == null) {
            network = new BasicNetwork(stack);
        }
        if (queue == null) {
            queue = new RequestQueue(new DiskBasedCache(cacheDir), network);
        }
        queue.start();

        return queue;
    }

    /**
     * Creates a default instance of the worker pool and calls
     * {@link RequestQueue#start()} on it.
     *
     * @param context A {@link Context} to use for creating the cache dir.
     * @return A started {@link RequestQueue} instance.
     */
    public static RequestQueue newRequestQueue(Context context, boolean isHttps) {
        // 如果你目前还没有证书,那么先用下面的这行代码,http可以照常使用.
        if (!isHttps) {
            return newRequestQueue(context, null, false);
        } else {
            // 此处R.raw.certificateName 表示你的证书文件,替换为自己证书文件名字就好
            return newRequestQueue(context, null, true);
        }

    }

    private static SSLSocketFactory buildSSLSocketFactory(Context context) {
        KeyStore keyStore = null;
        try {
            keyStore = buildKeyStore(context);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();

        TrustManagerFactory tmf = null;
        try {
            tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, tmf.getTrustManagers(), null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslContext.getSocketFactory();

    }

    private static HttpClient getHttpClient(Context context) {
        KeyStore keyStore = null;
        try {
            keyStore = buildKeyStore(context);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        org.apache.http.conn.ssl.SSLSocketFactory sslSocketFactory = null;
        if (keyStore != null) {
            try {
                sslSocketFactory = new org.apache.http.conn.ssl.SSLSocketFactory(
                        keyStore);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            }
        }


        HttpParams params = new BasicHttpParams();

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));

        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
                params, schemeRegistry);

        return new DefaultHttpClient(cm, params);
    }

    private static KeyStore buildKeyStore(Context context)
            throws KeyStoreException, CertificateException,
            NoSuchAlgorithmException, IOException {
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);

        Certificate cert = readCert(context);
        keyStore.setCertificateEntry("ca", cert);

        return keyStore;
    }

    private static Certificate readCert(Context context) {
        InputStream caInput = null;
        Certificate ca = null;
        try {
            //1.生成证书:Certificate
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            caInput = new ByteArrayInputStream(load.getBytes());
            ca = cf.generateCertificate(caInput);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return ca;
    }
}
