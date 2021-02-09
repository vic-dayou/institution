package com.cpcn.institution.entity;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;




@Data
@Component
@ConfigurationProperties(prefix = "config.path")
public class PaymentSystemEnvironment {


    private String systemPath;

    private String paymentPath;


}



