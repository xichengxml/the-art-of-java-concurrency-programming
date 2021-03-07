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

    public static void main(String[] args) {
        int i = 0;
        i++;
        ++i;

        int a = 1;
        int b = 1;

        int c = a + b;
        int d = a / b;

        int e = a * 2;
        int f = a << 1;
    }
}
