/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucidworks.fusion.jetty;

import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;

/**
 *
 * @author kevin
 */
public class APIServer extends Server {

    public APIServer() {
        super();
        APILogger.log("******************************************* LUCIDWORKS API SERVER STARTING ********************");
        init();
    }
    
    private void init(){
        if(this.getConnectors() != null){
            
        }
    }

    @Override
    public void handle(HttpChannel channel) {
        try {
           HttpChannel pchannel = modifyRequest(channel);
             

            super.handle(pchannel);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.print(e);
             APILogger.log(e.getLocalizedMessage());
        }
    }

    @Override
    public void handleAsync(HttpChannel channel) {
        try {
            HttpChannel pchannel = modifyRequest(channel);
            super.handleAsync(pchannel);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.print(e);
               APILogger.log(e.getLocalizedMessage());
        }
    }

    @Override
    protected void handleOptions(Request req, Response res) {
        try {
           // super.handleOptions(req, res);
           modifyRequestWithOptions( req,  res);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.print(e);
               APILogger.log(e.getLocalizedMessage());
        }
    }
    
    private HttpChannel modifyRequest(HttpChannel channel){
        String[] val = {"true"};
        channel.getRequest().getParameterMap().put("echo", val);
        channel.getResponse().addHeader("Powered-BY-CUSTOM", APIServer.class.getSimpleName());
        return channel;
    }
    
    protected void modifyRequestWithOptions(Request req, Response res){
        String[] val = {"true"};
      //  channel.getRequest().getParameterMap().put("echo", val);
     //   channel.getResponse().addHeader("Powered-BY-CUSTOM", APIServer.class.getSimpleName());
       // return channel;
       
        try {
            req.getParameterMap().put("echo", val);
            res.addHeader("Powered-by-custom", APIServer.class.getSimpleName());
            super.handleOptions(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.print(e);
               APILogger.log(e.getLocalizedMessage());
        }
    }
}
