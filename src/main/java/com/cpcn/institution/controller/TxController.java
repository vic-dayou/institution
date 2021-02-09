package com.cpcn.institution.controller;


import cpcn.institution.tools.security.SignatureFactory;
import cpcn.institution.tools.security.Signer;
import cpcn.institution.tools.util.Base64;
import cpcn.institution.tools.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import payment.api.system.TxMessenger;
import payment.api.util.GUIDGenerator;

import java.util.Arrays;

@Controller
@RequestMapping("/v1")
public class TxController {


    private static final Logger LOGGER = LoggerFactory.getLogger(TxController.class);


    @GetMapping("/index")
    public String index(Model model) throws Exception {
        final String guid = GUIDGenerator.genGUID();
        model.addAttribute("guid",guid);
        return "index";
    }

    @RequestMapping("/Tx")
    @ResponseBody
    public String tx2811(String plainText) throws Exception {
        LOGGER.info("request parameter: \n{}", plainText);
        final byte[] data = plainText.getBytes("UTF-8");
        String message = new String(Base64.encode(data));

        // 签名
        final Signer signer = SignatureFactory.getSigner();
        final byte[] sign = signer.sign(data);
        final String signature = StringUtil.bytes2hex(sign);

        TxMessenger txMessenger = new TxMessenger();
        String[] response = txMessenger.send(message, signature);
        final String result = Arrays.toString(response);
        String responseText = new String(Base64.decode(response[0]));
        LOGGER.info("response text: \n{}", responseText);
        return responseText;
    }


}
