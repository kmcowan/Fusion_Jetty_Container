/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucidworks.fusion.jetty;

import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.Server;
/**
 *
 * @author kevin
 */
public class APIServerConnector extends ServerConnector {
    
    public APIServerConnector(Server server){
        super(server);
    }
    
}
