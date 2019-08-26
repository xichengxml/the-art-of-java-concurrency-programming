package com.xicheng.concurrent.chapter05.code.code501;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * description
 *
 * @author liubin52
 * @date 2019-08-26 16:38:21
 */
public class LockUseCase {

	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		lock.lock();
		try {

		} finally {
			lock.unlock();
		}
	}
}
