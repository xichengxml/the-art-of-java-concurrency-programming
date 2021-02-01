package com.xicheng.concurrent.tuling;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/2/1 下午 10:40
 */
public class C02_DCL {

    private static volatile C02_DCL INSTANCE;

    private C02_DCL() {

    }

    public static C02_DCL getInstance() {
        if (INSTANCE == null) {
            synchronized (C02_DCL.class) {
                if (INSTANCE == null) {
                    newInstance();
                }
            }
        }
        return INSTANCE;
    }

    private static void newInstance() {
        INSTANCE = new C02_DCL();
    }
}
