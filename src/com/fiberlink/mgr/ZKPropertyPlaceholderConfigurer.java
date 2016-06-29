package com.fiberlink.mgr;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZKPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private ZKPropertyUtils zkPropertyUtils;
    
    public ZKPropertyPlaceholderConfigurer() {
        super();
        zkPropertyUtils = new ZKPropertyUtils();
    }
    
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException
    {
        
        Properties zkProperties;
        try {
            zkProperties = zkPropertyUtils.loadProperties();
        } catch (Exception e) {
            throw new RuntimeException("Unable to get Zookeeper Properties", e);
        }
        
        super.processProperties(beanFactoryToProcess, zkProperties);
    }
    
}
