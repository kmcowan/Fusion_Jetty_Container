/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucidworks.fusion.jetty;

import java.util.Properties;

/**
 *
 * @author kevin
 */
public class APIProperties {
    private static Properties props = null;
    
    public static Properties getProperties(){
        if(props == null){
            props = new Properties();
        }
        
        return props;
    }
}
