package com.xicheng.concurrent.chapter04.code.code411;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * description
 *
 * @author liubin52
 * @date 2019-08-15 14:08:50
 */
public class MultiThread {

	private static final Logger LOGGER = LoggerFactory.getLogger(MultiThread.class);

	public static void main(String[] args) {
		// 获取java管理的mxbean
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		// 仅获取线程和线程堆栈信息
		ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
		// 遍历，仅打印id和名称
		for (ThreadInfo threadInfo : threadInfos) {
			LOGGER.info("id: {}，name: {}", threadInfo.getThreadId(), threadInfo.getThreadName());
		}
	}
}
