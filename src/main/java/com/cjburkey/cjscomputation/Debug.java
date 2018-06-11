package com.cjburkey.cjscomputation;

public class Debug {
    
    public static void log(Object msg) {
        CJsComputation.logger.info(sanitize(msg));
    }
    
    public static void warn(Object msg) {
        CJsComputation.logger.warn(sanitize(msg));
    }
    
    public static void err(Object msg) {
        CJsComputation.logger.error(sanitize(msg));
    }
    
    public static void log(Object msg, Object... data) {
        CJsComputation.logger.info(sanitize(msg), data);
    }
    
    public static void warn(Object msg, Object... data) {
        CJsComputation.logger.warn(sanitize(msg), data);
    }
    
    public static void err(Object msg, Object... data) {
        CJsComputation.logger.error(sanitize(msg), data);
    }
    
    private static String sanitize(Object msg) {
        String m = (msg == null) ? "null" : msg.toString();
        return (m == null) ? "null" : m;
    }
    
}