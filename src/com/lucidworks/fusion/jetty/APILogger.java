/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucidworks.fusion.jetty;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author kevin
 */
public class APILogger {

    private static final File logfile = new File("/projects/Jetty_API_Override/logfile.log");
    private static BufferedWriter bw = null;

    public static void log(String message) {

        appendToLog(message);
    }

    public static void log(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        appendToLog(sw.toString());
    }
    
     public static void log(Error e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        appendToLog(sw.toString());
    }

    private static void appendToLog(String data) {
       /* if (bw == null) {
            try {
                // logfile.createNewFile();
                bw = new BufferedWriter(new FileWriter(logfile, true));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {

            bw.write(data);
            bw.newLine();
            bw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
         */
       System.out.println(data);
    }
}
