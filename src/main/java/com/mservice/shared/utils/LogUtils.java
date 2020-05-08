package com.mservice.shared.utils;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;



/***
 * @author uyen.tran
 */
public class LogUtils {
     static Logger logger;

    public static void init(){
        logger = Logger.getLogger(LogUtils.class);
        BasicConfigurator.configure();
    }

    public static void info(String serviceCode, Object object){
        logger.info(new StringBuilder().append("[").append(serviceCode).append("]: ").append(object));
    }
    public static void info(Object object){
        logger.info(object);
    }

    public static void debug(Object object){
        logger.debug(object);
    }

    public static void error(Object object){
        logger.error(object);
    }

//    public static void error(Object object) {
//        logger.error(object);
//    }

    public static void warn(Object object){
        logger.warn(object);
    }
}
