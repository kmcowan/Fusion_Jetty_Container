/*
 * A filter by which you can extend Processing in Jetty
 */
package com.lucidworks.fusion.jetty;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 

/**
 *
 * @author kevin.cowan@lucidworks.com
 */
public class APIRequestFilter implements Filter {

    FilterConfig filterConfig = null;
 

    public void init(FilterConfig filterConfig) throws ServletException {

        this.filterConfig = filterConfig;

    }

    public void destroy() {
        
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
       
         // print an empty response
        //   servletResponse.getOutputStream().print("{}");
        try {

            HttpServletRequest req = (HttpServletRequest) servletRequest;
            String[] val = {"true"};
            Map<String, String[]> map = new TreeMap<>();
            map.put("echo", val);
            map.put("echoParams", val);
            map.put("debug", val);
            boolean isProcessing = false; 

     
            ServletRequest nreq = new LWHttpServletRequestWrapper(req, map);

            HttpServletResponse res = (HttpServletResponse) servletResponse;

            if (req.getMethod().equalsIgnoreCase("POST") ) {
    
                String data = "  ";
                if (req.getRequestURL() != null) {
                    data += req.getRequestURL().toString();
                } else if (req.getPathTranslated() != null) {
                    data += req.getPathTranslated();
                }

                if (data.contains("collections/morganstanley/index") && 
                        !data.contains("echo=true") && 
                        !data.contains("_click_signals_")) {
                    APILogger.log("PROCESS REQUEST ************** " + data);
                    isProcessing = true;
                    //   res.sendRedirect(res.encodeRedirectURL(data + "?echo=true"));
                    //  res.sendRedirect(data + "?echo=true");
                    res.getOutputStream().write("{\"status\": 0}".getBytes());

                    //  return;
                } else {
                    if( !data.contains("_click_signals_")){
                     APILogger.log("NOT PROcessing: " + data);
                    }
                }
            }

            if (isProcessing) {
                APILogger.log("Set Response Headers...");
                res.addHeader("Powered-by-APIOVERRIDE", "Api Override by Kevin");
                res.setStatus(200);
                res.addHeader("Content-Length", "20");

                filterChain.doFilter(servletRequest,
                        new LWHttpServletResponseWrapper((HttpServletResponse) servletResponse) {
                    @Override
                    public void setHeader(String name, String value) {

                        super.setHeader(name, value);

                    }

                    @Override
                    public void setContentLength(int len) {
                        super.setContentLength(20+len);
                    }

                    @Override
                    public void setStatus(int stat) {
                        super.setStatus(200);
                    }
                    
                    

                    @Override
                    public ServletOutputStream getOutputStream() throws IOException {
                        super.getOutputStream().print("{\"status\": 0}");
                        return super.getOutputStream();  
                    }

                   

                    @Override
                    public void addHeader(String name, String value) {
                        super.addHeader("Custom-API-Powered-by", "Kev");
                      
                        super.addHeader(name, value);  
                    }

                    @Override
                    public ServletResponse getResponse() {
                        try{
                        super.getResponse().getOutputStream().print("{\"status\": 0}");
                        }catch(Exception e){
                            APILogger.log(e);
                        }
                        return super.getResponse();  
                    }

                });
                APILogger.log("Post Process Complete...");
            } else {

            filterChain.doFilter((ServletRequest) nreq, (ServletResponse) res);
            }

        } catch (Exception e) {
           APILogger.log(e);
            
        }

    }

}
