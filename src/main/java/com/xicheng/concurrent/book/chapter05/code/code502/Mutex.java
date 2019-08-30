package com.xicheng.concurrent.book.chapter05.code.code502;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * description
 *
 * @author xichengxml
 * @date 2019-08-26 16:59:47
 */
public class Mutex implements Lock {

	// 静态内部类，自定义同步器
	private static class Sync extends AbstractQueuedSynchronizer {
		// 是否处于占用状态
		@Override
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

		@Override
		protected boolean tryAcquire(int arg) {
			return super.tryAcquire(arg);
		}

		@Override
		protected boolean tryRelease(int arg) {
			return super.tryRelease(arg);
		}

		// 返回一个condition，每个condition都包含一个condition队列
		Condition newCondition() {
			return new ConditionObject();
		}
	}

	// 仅需要将操作代理到sync上即可
	private final Sync sync = new Sync();

	@Override
	public void lock() {
		sync.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {

	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}

	public boolean hasQueuedThreads() {
		return sync.hasQueuedThreads();
	}

}
