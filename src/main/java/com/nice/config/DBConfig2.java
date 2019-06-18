package com.nice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ningh
 */
@ConfigurationProperties(prefix = "custom.datasource.ds2")
@Component
@Data
public class DBConfig2 {

    private String url;
    private String username;
    private String password;

}
