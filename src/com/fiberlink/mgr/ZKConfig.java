package com.fiberlink.mgr;

import java.util.HashMap;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.stereotype.Component;

@Component("zkConfig")
public class ZKConfig {
    public static final String ZOO_KEEPER_CONNECTION_STRING = "10.104.26.238:3181,10.104.26.238:4181,10.104.26.238:5181";
    private static Map<String, String> propertyMap = new HashMap<>();
    
    static {
        propertyMap.put("prop1", "val1");
        propertyMap.put("prop2", "val2");
    }
    
    private static CuratorFramework client;
    
    public static CuratorFramework configureZKClient() {
        if(client == null || !client.isStarted()) {
            client = CuratorFrameworkFactory.newClient(ZOO_KEEPER_CONNECTION_STRING,
                                                            new ExponentialBackoffRetry(1000, 3));
            client.start();
        }
        
        return client;
    }
    
    public static String getProperty(String name) throws Exception {
        String path = "/" + name;
        
        byte[] propValInBytes = client.getData().forPath(path);
        return convertByteArrayToString(propValInBytes);
    }
    
    public static String convertByteArrayToString(byte[] byteArray) {
        StringBuilder str = new StringBuilder();
        
        for(byte b : byteArray)
            str.append((char) b);
        
        return str.toString();
    }
    
    public static void readProperties() throws Exception {
        for(Map.Entry<String, String> property : propertyMap.entrySet()) {
            System.out.println(property.getKey() + " ZK value: " + getProperty(property.getKey()));
        }
    }
    
    public static void loadProperties() throws Exception {
        for(Map.Entry<String, String> property : propertyMap.entrySet()) {
            if(client != null && client.checkExists() != null) {
                String propPath = "/" + property.getKey();
                byte[] propVal = property.getValue().getBytes();
                
                client.create().forPath(propPath, propVal);
            }
        }
    }
    
    public static void main(String args[]) throws Exception {
        configureZKClient();
        
        loadProperties();
        readProperties();
    }
}
