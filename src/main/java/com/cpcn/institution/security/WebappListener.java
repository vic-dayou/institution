/**
 * <pre>
 * Copyright Notice:
 *    Copyright (c) 2005-2009 China Financial Certification Authority(CFCA)
 *    A-1 You An Men Nei Xin An Nan Li, Xuanwu District, Beijing ,100054, China
 *    All rights reserved.
 * 
 *    This software is the confidential and proprietary information of
 *    China Financial Certification Authority (&quot;Confidential Information&quot;).
 *    You shall not disclose such Confidential Information and shall use
 *    it only in accordance with the terms of the license agreement you
 *    entered into with CFCA.
 * </pre>
 */

package com.cpcn.institution.security;

import com.cpcn.institution.entity.PaymentSystemEnvironment;
import cpcn.institution.tools.util.AppVersionUtil;
import cpcn.institution.tools.util.StringUtil;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import payment.api.system.PaymentEnvironment;

import javax.servlet.ServletContextEvent;

import java.io.File;

@Component
public class WebappListener implements ApplicationRunner {

    @Autowired
    private PaymentSystemEnvironment environment;


	private static Logger logger;

	public static String VERSION = null;

	public WebappListener() {

    }

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

	public void contextInitialized() {
		loadVersionInfo();
        head();
        try {
            String systemConfigPath = "/CPCN/Payment/InstitutionSimulator/config/system";
            String paymentConfigPath = "/CPCN/Payment/InstitutionSimulator/config/payment";

            // Log4j
            String log4jConfigFile = systemConfigPath + File.separatorChar + "log4j.xml";
            System.out.println(log4jConfigFile);
            //DOMConfigurator.configure(log4jConfigFile);

            // 初始化支付环境
            PaymentEnvironment.initialize(paymentConfigPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private static void loadVersionInfo() {
		try {
			// 读取version.properties
			String version = AppVersionUtil.getAppVersion(WebappListener.class);
			if (StringUtil.isNotEmpty(version)) {
				VERSION = version;
			} else {
				//logger.fatal("version property initialize faild.");
			}
		} catch (Exception e) {
			//logger.fatal("version property initialize faild.", e);
		}

	}

    private void head() {
        System.out.println("==========================================");
        System.out.println("China Payment & Clearing Network Co., Ltd.");
        System.out.println("Payment and Settlement System");
		System.out.println("Institution Simulator " + VERSION);
        System.out.println("==========================================");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadVersionInfo();
        head();
        try {

            String systemConfigPath = environment.getSystemPath();
            String paymentConfigPath = environment.getPaymentPath();

            // Log4j
            String log4jConfigFile = systemConfigPath + File.separatorChar + "log4j.xml";
            System.out.println(log4jConfigFile);
            //DOMConfigurator.configure(log4jConfigFile);

            // 初始化支付环境
            PaymentEnvironment.initialize(paymentConfigPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
