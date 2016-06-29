package com.fiberlink.mgr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("sampleService")
public class SampleService {
    @Value("${prop1}")
    private String property1;
    @Value("${prop2}")
    private String property2;
    
    @Value("${ce-batch-app/dev/jdbc.url}")
    private String jdbcUrl;
    
    @Value("${ce-batch-app/dev/jdbc.password}")
    private String jdbcPasswd;
    
    public String getProperty1() {
        return property1;
    }
    
    public String getProperty2() {
        return property2;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getJdbcPasswd() {
        return jdbcPasswd;
    }
    
}
