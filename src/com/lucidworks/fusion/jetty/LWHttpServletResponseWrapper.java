/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucidworks.fusion.jetty;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 *
 * @author kevin
 */
public class LWHttpServletResponseWrapper extends HttpServletResponseWrapper{
    
    public LWHttpServletResponseWrapper(HttpServletResponse res){
        super(res);
        
    }
}
