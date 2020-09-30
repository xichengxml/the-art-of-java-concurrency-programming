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
        stringBuilder.append(log.getName()).append(" main ").append(logFormat);
        log.info(stringBuilder.toString(), values);
    }
}
