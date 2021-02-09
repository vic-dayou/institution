package com.cpcn.institution.controller;


import com.cpcn.institution.crawler.InstitutionID;
import com.cpcn.institution.login.Login;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RequestMapping("/v1")
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        final Cookie[] cookies = request.getCookies();
        String uuid = UUID.randomUUID().toString();
        if (cookies == null) {
            Cookie cookie = new Cookie("M_SESSION", uuid);
            response.addCookie(cookie);
        }

        return "login";
    }



    @PostMapping("/login")
    @ResponseBody
    public List<String> doLogin(String loginID, String password, String imageCode) {
        System.out.println(loginID);
        System.out.println(password);
        System.out.println(imageCode);
        Login.login(loginID,password,imageCode);
        return InstitutionID.getInstitution("312");
    }
}
