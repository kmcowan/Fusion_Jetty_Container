/*
 * A container to wrapper all calls to Fusion API

https://google.github.io/guice/api-docs/latest/javadoc/index.html?com/google/inject/servlet/GuiceFilter.html

https://www.eclipse.org/jetty/documentation/9.3.x/reference-section.html#jetty-xml-configure

https://github.com/eclipse/jetty.project/blob/jetty-9.3.x/jetty-server/src/main/config/etc/jetty-http.xml

http://www.eclipse.org/jetty/documentation/current/rewrite-handler.html

https://www.eclipse.org/jetty/documentation/9.3.x/configuring-webapps.html
 */
package com.lucidworks.fusion.jetty;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kevin.cowan@lucidworks.com
 */
public class APIGuiceFilter extends com.google.inject.servlet.GuiceFilter {
    
    public APIGuiceFilter(){
        super();
       // APILogger.log("Custom Guice Filter Engaged...");
    }
    
        @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
       APILogger.log("** BEGIN GuiceFilter execution...");
        HttpServletResponse resp = null;
       try{
        super.doFilter(servletRequest, servletResponse, filterChain);
         resp = (HttpServletResponse)servletResponse;
         if(resp.getStatus() == 204){
               resp.getOutputStream().write("{\"status\": 0}".getBytes());
               resp.setStatus(200);
         }
       }catch(Exception e){
           APILogger.log(e);
       }catch(Error err){
           APILogger.log(err);
       } finally {
           APILogger.log("Guice Filter Run Complete. Status: " + resp.getStatus());
           String[] val = {"Kev"};
           
           
       }
        
 
    }
}
