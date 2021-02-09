package com.cpcn.institution.crawler;

import com.cpcn.institution.login.HttpClientHolder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class InstitutionID {

    public static void main(String[] args) {
        getInstitution("312");
    }


    public static List<String> getInstitution(String pageNo) {
        if (pageNo == null || "".equals(pageNo)) {
            pageNo = "0";
        }
        String url = "https://test.cpcn.com.cn/Management/InstitutionInfoNew.do";
        final HttpClient httpsClient = HttpClientHolder.getHttpsClient();
        List<NameValuePair> params = new LinkedList<NameValuePair>();
        List<String> institutionIDs = new LinkedList<String>();
        // 设置参数
        params.add(new BasicNameValuePair("op", "getListForm"));
        params.add(new BasicNameValuePair("pageNo", pageNo));
        // 参数t 随机数 可有可无
        params.add(new BasicNameValuePair("t","0.8702144893057147"));
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36 Edg/88.0.705.63");
            //httpPost.setHeader("Cookie",HttpClientHolder.getCookie().getValue());
            httpPost.setHeader("Cookie", "M_SESSION=2e336be8-4a37-466b-939d-6879bb76c8fb");
            HttpResponse response = httpsClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                String respContent = EntityUtils.toString(entity);
                System.out.println(respContent);
                final Document document = Jsoup.parse(respContent);
                final Elements elements = document.select("tr[id$=TrID]");
                for (Element e : elements) {
                    final String institutionID = e.attr("id").trim().substring(0,6);
                    System.out.println(institutionID);
                    institutionIDs.add(institutionID);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return institutionIDs;
    }
}
