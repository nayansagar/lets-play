package com.challenge.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class ResourceUtils {
    
    private static final Logger LOGGER = Logger.getLogger(ResourceUtils.class.getName());
    
    public static String getResourceAsString(String name, String defaultString){
        InputStream is = null;
        try {
            is = ResourceUtils.class.getClassLoader().getResourceAsStream(name);
            byte[] content = new byte[is.available()];
            is.read(content);
            return new String(content);
        } catch (IOException e) {
            return defaultString;
        } finally {
            if(is != null){
                try {
                    is.close();    
                }catch (IOException e){
                    LOGGER.severe("Could not close steam");   
                }
            }
        }
        
    }
}
