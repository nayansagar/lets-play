package com.challenge.common.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GOGLogger {

    private static Logger logger;

    private static GOGLogger instance = new GOGLogger();

    static {
        InputStream inputStream = GOGLogger.class.getClassLoader().getResourceAsStream("gog_logging.properties");
        if (null != inputStream) {
            try {
                LogManager.getLogManager().readConfiguration(inputStream);
            } catch (IOException e) {
                Logger.getGlobal().log(Level.SEVERE, "init logging system", e);
            }
            logger = Logger.getLogger(GOGLogger.class.getCanonicalName());
        }
    }

    //Deviation from the singleton pattern - required to be public for LogManager
    public GOGLogger(){}

    public static GOGLogger getInstance(){
        if(instance == null){
            synchronized (instance){
                if(instance == null){
                    instance = new GOGLogger();
                }
            }
        }
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }
}
