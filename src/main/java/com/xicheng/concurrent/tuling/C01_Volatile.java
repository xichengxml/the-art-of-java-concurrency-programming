package com.xicheng.concurrent.tuling;

/**
 * description 证明可见性
 *
 * @author xichengxml
 * @date 2021/2/1 下午 09:34
 */
public class C01_Volatile {

    private static boolean found = false;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("等基友送笔来...");
            while (!found) {

            }
            System.out.println("笔送来了，开始写情书");
        }).start();

        new Thread(() -> {
            System.out.println("基友正在找笔");
            change();
            System.out.println("笔找到了");
        }).start();
    }

    private static void change() {
        found = true;
    }
}
