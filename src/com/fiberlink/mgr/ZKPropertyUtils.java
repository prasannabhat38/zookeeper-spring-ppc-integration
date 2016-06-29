package com.fiberlink.mgr;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.stereotype.Component;

@Component("zkPropertyUtils")
public class ZKPropertyUtils {
    private static Set<String> propertyNames;
    private static CuratorFramework client;
    
    static {
        propertyNames = new HashSet<>();
        
        propertyNames.add("prop1");
        propertyNames.add("prop2");
        propertyNames.add("ce-batch-app/dev/jdbc.url");
        propertyNames.add("ce-batch-app/dev/jdbc.password");
        
        client = ZKConfig.configureZKClient();
    }
    
    public String getProperty(String name) throws Exception {
        String path = "/" + name;
        
        byte[] propValInBytes = client.getData().forPath(path);
        return convertByteArrayToString(propValInBytes);
    }
    
    public String convertByteArrayToString(byte[] byteArray) {
        StringBuilder str = new StringBuilder();
        
        for(byte b : byteArray)
            str.append((char) b);
        
        return str.toString();
    }
    
    public Properties loadProperties() throws Exception {
        Properties zkProperties = new Properties();
        
        for(String propertyName : propertyNames) {
            String propertyVal = getProperty(propertyName);
            
            zkProperties.put(propertyName, propertyVal);
        }
        
        return zkProperties;
    }
    
}
