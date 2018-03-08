
Use Case:  A client needs to control their Jetty requests and responses at a deeper level than currently available.    

Open Source Project:
https://github.com/kmcowan/Fusion_Jetty_Container



In the web.xml, we defined a custom filter, similar to the one that was there initially.  We added custom logging to verify the process.  What we could see is that though our filter was called and values set, ultimately, these values were scrubbed in the final response.  

The primary filter declared in the web.xml is a default filter from the Google Guice framework.  After some testing, we extended this class with our own custom class. This acted as a passthrough to the Google Guice framework, but afterwards, we were able to modify the response and the modifications were honored.  

Thus the proposed solution is to extend the Google Guice filter with a custom class, example attached. 

GuiceFilter javadoc:
https://google.github.io/guice/api-docs/latest/javadoc/index.html?com/google/inject/servlet/GuiceFilter.html

 





Sample Extended Filter Class


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
        APILogger.log("Custom Guice Filter Engaged...");
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




The implementation is as follows:

Path to web.xml:   FUSION_ROOT/apps/jetty/api/webapps/api/WEB-INF/web.xml

In the web.xml change: 

From: 
<filter>
<filter-name>guiceFilter</filter-name>
<filter-class>com.google.inject.servlet.GuiceFilter</filter-class>
</filter>

To: 

<filter>
<filter-name>guiceFilter</filter-name>
<filter-class>name.of.my.package.APIGuiceFilter</filter-class>
</filter>



References:
https://google.github.io/guice/api-docs/latest/javadoc/index.html?com/google/inject/servlet/GuiceFilter.html

https://www.eclipse.org/jetty/documentation/9.3.x/reference-section.html#jetty-xml-configure

https://github.com/eclipse/jetty.project/blob/jetty-9.3.x/jetty-server/src/main/config/etc/jetty-http.xml

http://www.eclipse.org/jetty/documentation/current/rewrite-handler.html

https://www.eclipse.org/jetty/documentation/9.3.x/configuring-webapps.html
