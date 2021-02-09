package com.cpcn.institution.login;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Login {

    public static void main(String[] args) {

    }


    public static String login(String loginID,String password,String verifyCode) {
        String url = "https://test.cpcn.com.cn/Boss/loginaction.do?op=login";
        /*final String verifyCode = VerifyCode.getVerifyCode();
        if (StringUtil.isEmpty(verifyCode) || verifyCode.length() != 4) {
            System.out.println("验证码错误: "+verifyCode);
            return;
        }*/
        List<NameValuePair> params = new LinkedList<NameValuePair>();
        params.add(new BasicNameValuePair("loginID",loginID));
        params.add(new BasicNameValuePair("password",password));
        params.add(new BasicNameValuePair("fusionUrlHeader","aHR0cHM6Ly90ZXN0LmNwY24uY29tLmNu"));
        params.add(new BasicNameValuePair("imageCode",verifyCode));

        final Cookie cookie = HttpClientHolder.getCookie();
        final String cookieName = cookie.getName();
        final String cookieValue = cookie.getValue();
        String cookies = cookieName+"="+cookieValue;
        System.out.println(cookies);
        HttpPost httpPost =   new HttpPost(url);
        String s = "";

        HttpClient httpClient = HttpClientHolder.getHttpsClient();
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36 Edg/88.0.705.63");
            httpPost.setHeader("Cookie",cookies);
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode==200){
                HttpEntity entity = response.getEntity();
                s = EntityUtils.toString(entity);
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
