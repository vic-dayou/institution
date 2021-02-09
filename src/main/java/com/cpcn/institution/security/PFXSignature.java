package com.cpcn.institution.security;

import cpcn.institution.tools.security.CertificateVerifier;
import cpcn.institution.tools.security.PfxSigner;
import cpcn.institution.tools.security.SignatureFactory;
import cpcn.institution.tools.security.Signer;
import cpcn.institution.tools.util.Base64;
import cpcn.institution.tools.util.Base64Encoder;
import payment.api.system.TxMessenger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


public class PFXSignature {

    private static final String PFX_PASSWORD = "cfca1234";
    private static final String ALIAS = "le-d95503d8-c227-41a9-9261-d96552128e5a";
    private static final String PUBLIC_KEY_PATH = "D:\\CPCN\\Payment\\InstitutionSimulator\\config\\payment\\paytest.cer";
    private static final String PRIVATE_KEY_PATH = "D:\\CPCN\\Payment\\InstitutionSimulator\\config\\payment\\test.pfx";
    private static final String algorithm = "SHA1withRSA";
    public static void main(String[] args) {
        try {

            /*final byte[] data = Base64.decode(message);
            String signature = "0EAD80C8F4B8B06FF9C89DBA0450FA07B3B5A8236B34EE9B6415559152B487C56ED823B9960BAFF839863DD7B994A062D630B75C120C39878C4BBC7424E9482CAD8E1EAAD87735E75676E6F5BD5A981EAE8A475521C077837A4300D23D804AB4F6F79D11FF8A6419D7D879A5CD3876305E03E929CAB679EDF6B855B57AA94C47";
            final byte[] sig = hex2bytes(signature);
            Signature sign = Signature.getInstance(algorithm);
            PublicKey publicKey = getPublicKey();
            sign.initVerify(publicKey);
            sign.update(data);
            final boolean verify = sign.verify(sig);
            System.out.println(verify);*/

            String message = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+CjxSZXF1ZXN0Pgo8SGVhZD4KPEluc3RpdHV0aW9uSUQ+MDA2MDExPC9JbnN0aXR1dGlvbklEPgo8VHhDb2RlPjI1MzE8L1R4Q29kZT4KPC9IZWFkPgo8Qm9keT4KPFR4U05CaW5kaW5nPjIwMjEwMjA1MTQ0OTMyMDczMTAxMjE5NDIwMjwvVHhTTkJpbmRpbmc+CjxCYW5rSUQ+MzA4PC9CYW5rSUQ+CjxBY2NvdW50TmFtZT7lvKDkuInllYrllYo8L0FjY291bnROYW1lPgo8QWNjb3VudE51bWJlcj42MjE0ODM3ODMyODMxNTY4PC9BY2NvdW50TnVtYmVyPgo8SWRlbnRpZmljYXRpb25UeXBlPjA8L0lkZW50aWZpY2F0aW9uVHlwZT4KPElkZW50aWZpY2F0aW9uTnVtYmVyPjQ0MDgyNTE5ODkwMTE1MTE2NDwvSWRlbnRpZmljYXRpb25OdW1iZXI+CjxQaG9uZU51bWJlcj4xMzMzMzMzMzMzMzwvUGhvbmVOdW1iZXI+CjxDYXJkVHlwZT4xMDwvQ2FyZFR5cGU+CjwvQm9keT4KPC9SZXF1ZXN0Pg==";
            String signature = "8C3A16EFD3027AA116FABE037D10B7B897A50A603049C8A72CD7C441D79D542EF697255947139234AA98F58E5CA200033C089D33B255257DF8EF245ECAAB290F41D3746870DDBB689AFEDB5443C33B57E718F0BE6607681D22DB963D7C8C41CEDCD923487261F8E39DEAC15A48B0790CAC0A4CE5588ED3C89C86069CFF815DE5";


            TxMessenger messenger = new TxMessenger();
            final String[] send = messenger.send(message, signature, "https://test.cpcn.com.cn/Gateway/InterfaceII");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public static  PublicKey getPublicKey() throws Exception {
        final InputStream inputStream = new FileInputStream(PUBLIC_KEY_PATH);
        //获取实现指定证书类型的CertificateFactory对象
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        //生成一个证书对象，并从执行的输入流中读取数据对它进行初始化
        X509Certificate certificate = (X509Certificate) cf.generateCertificate(inputStream);
        return certificate.getPublicKey();
    }

    public static byte[] hex2bytes(String hexString) {
        hexString = hexString.toUpperCase();
        char[] chars = hexString.toCharArray();
        byte[] bytes = new byte[chars.length / 2];
        int index = 0;

        for(int i = 0; i < chars.length; i += 2) {
            byte newByte = 0;
            newByte = (byte)(newByte | char2byte(chars[i]));
            newByte = (byte)(newByte << 4);
            newByte |= char2byte(chars[i + 1]);
            bytes[index] = newByte;
            ++index;
        }

        return bytes;
    }


    public static byte char2byte(char ch) {
        switch(ch) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            default:
                return 0;
            case 'A':
                return 10;
            case 'B':
                return 11;
            case 'C':
                return 12;
            case 'D':
                return 13;
            case 'E':
                return 14;
            case 'F':
                return 15;
        }
    }
}
