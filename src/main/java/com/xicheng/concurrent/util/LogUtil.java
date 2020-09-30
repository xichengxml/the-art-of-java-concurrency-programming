package com.xicheng.concurrent.util;

import org.slf4j.Logger;

/**
 * description
 *
 * @author xichengxml
 * @date 2020-09-30 09:49
 */
public class LogUtil {

    public static void info(Logger log, String logFormat, Object... values) {
        StringBuilder stringBuilder = new StringBuilder();
        String className = log.getName().substring(log.getName().lastIndexOf(".") + 1);
        String methodName = "";
        stringBuilder.append(className).append(" ").append(methodName).append(" ").append(logFormat);
        log.info(stringBuilder.toString(), values);
    }
}
