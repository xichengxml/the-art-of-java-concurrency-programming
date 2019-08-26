package com.xicheng.concurrent.chapter05.code.code516;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * description
 *
 * @author xichengxml
 * @date 2019-08-26 18:31:33
 */
public class Cache {

	static Map<String, Object> map = new HashMap<>();

	static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

	static Lock read = reentrantReadWriteLock.readLock();

	static Lock write = reentrantReadWriteLock.writeLock();

	/**
	 * 读取
	 * @param key
	 * @return
	 */
	public static final Object get(String key) {
		read.lock();
		try {
			return map.get(key);
		} finally {
			read.unlock();
		}
	}

	/**
	 * 写入
	 * @param key
	 * @param value
	 * @return
	 */
	public static final Object put(String key, Object value) {
		write.lock();
		try {
			return map.put(key, value);
		} finally {
			write.unlock();
		}
	}

	/**
	 * 清空
	 */
	public static final void clear() {
		write.lock();
		try {
			map.clear();
		} finally {
			write.unlock();
		}
	}

}
