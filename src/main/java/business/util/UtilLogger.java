package business.util;

/**
 *
 * @author cbustamante
 */
import java.text.SimpleDateFormat;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Singleton;

/***
 * Logging producer for injectable log4j logger 
 */
@Singleton
@Startup
public class UtilLogger {

    private static final  Logger logger = Logger.getLogger("log");
    public static boolean configured = false;
    
    private UtilLogger() {
        init();
    }
    
    /***
     * @param injectionPoint
     * @return 
     */
    @Produces  
    public Logger produceLogger(InjectionPoint injectionPoint) {  
         if (!configured) {
            init();
        }
         if (null !=Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName()))
            return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());  
         else
          return logger;
    }  
    
    
    public static void init() {

        Properties log4jProperties = new Properties();
        log4jProperties.setProperty("log4j.rootLogger", "ERROR, jlogs");
        log4jProperties.setProperty("log4j.rootLogger", "INFO, jlogs");
        log4jProperties.setProperty("log4j.appender.jlogs", "org.apache.log4j.DailyRollingFileAppender");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        log4jProperties.setProperty("log4j.appender.jlogs.File", System.getProperty("user.home") + "/logs/PrimeDemo.log");
        log4jProperties.setProperty("log4j.appender.jlogs.DatePattern", ".yyyy-MM-dd");
        log4jProperties.setProperty("log4j.appender.jlogs.layout", "org.apache.log4j.PatternLayout");
        log4jProperties.setProperty("log4j.appender.jlogs.layout.ConversionPattern", "%d|%-5p|%m%n");
        log4jProperties.setProperty("log4j.appender.jlogs.rollingFile.File", "%d{yyyy-MM-dd}_logs.log.gz");
        log4jProperties.setProperty("log4j.appender.jlogs.rollingFile.MaxFileSize", "2MB");
        PropertyConfigurator.configure(log4jProperties);

        logger.info(" Inicio de actividades en log");
        configured = true;     
    }

//    public static void info(String log) {
//        log(log);
//    }
//
//
//
//    public static void log(String log) {
//        if (!configured) {
//            init();
//        }
//        logger.info("INF |" + log);
//        System.out.println("INF |" + log);
//
//    }
//
//    public static void error(String log) {
//        if (!configured) {
//            init();
//        }
//        logger.error("ERROR |" + log);
//        System.out.println("ERROR |" + log);
//
//    }
//
//    public static void error(String log, Exception ex) {
//        if (!configured) {
//            init();
//        }
//        String errorMsg = "";
//        if (ex instanceof NullPointerException) {
//            NullPointerException n = (NullPointerException) ex;
//            StackTraceElement stackTrace = n.getStackTrace()[0];
//            errorMsg = ex.getMessage() + "Exception at Class:" + stackTrace.getClassName() + " method:" + stackTrace.getMethodName() + " line:" + stackTrace.getLineNumber();
//        } else {
//            errorMsg = ex.toString();
//        }
//        logger.error("ERROR |" + log + " " + errorMsg);
//        System.out.println("ERROR |" + log + " " + ex.getMessage() + " causa:" + ex.getCause() + errorMsg);
//
//    }
}
