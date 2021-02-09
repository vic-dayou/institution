package com.cpcn.institution.login;

import cpcn.institution.tools.util.StringUtil;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class VerifyCode {


    private static final String imgRootPath = "D:\\project\\img\\";
    private static final Logger logger = LoggerFactory.getLogger(VerifyCode.class);


    public static void main(String[] args) throws Exception {
        final String verifyCode = getVerifyCode();
        if (StringUtil.isEmpty(verifyCode) || verifyCode.length() > 4) {
            System.out.println("验证码识别失败");
        }else{
            System.out.println(verifyCode);
        }


    }


    public static String getVerifyCode() {
        String filename = null;
        try {
            filename = download();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String imgPath = imgRootPath+filename;
        File imageFile = new File(imgPath);
        System.out.println(imgPath);
        Tesseract tessreact = new Tesseract();

        tessreact.setDatapath("D:\\project\\tessdata");
        String verifyCode = "";
        try {
            verifyCode = tessreact.doOCR(imageFile);
            logger.info("识别库验证结果： {}",verifyCode);
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        for (char c : verifyCode.toCharArray()) {
            if (Character.isLetter(c) || Character.isDigit(c)) {
                sb.append(c);
            }
        }
        logger.info("去除非字母数字：{}",sb.toString());
        return sb.toString();
    }




    private static String  download()  {

        HttpClient httpClient = HttpClientHolder.getHttpsClient();

        HttpGet httpGet = new HttpGet("https://test.cpcn.com.cn/Boss/view/common/Image.jsp");

        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            logger.error("http execute exception...");
            e.printStackTrace();
        }

        final long timeMillis = System.currentTimeMillis();
        assert response != null;
        final int statusCode = response.getStatusLine().getStatusCode();
        String filename = "img-"+timeMillis+".jpeg";
        FileOutputStream fos = null;
        if (statusCode == 200) {
            final byte[] bytes;
            try {
                bytes = EntityUtils.toByteArray(response.getEntity());
                fos = new FileOutputStream(imgRootPath+filename);
                fos.write(bytes);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("error");
        }
        return filename;

    }


}
