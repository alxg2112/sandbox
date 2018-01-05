package com.alxg2112.sandbox.spinlock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Alexander Gryshchenko
 */
public class ReentrantSpinlock {

	private final AtomicReference<Thread> owner = new AtomicReference<>(null);
	private final AtomicInteger counter = new AtomicInteger(0);

	public void lock() {
		if (owner.get() == Thread.currentThread()) {
			counter.incrementAndGet();
			return;
		}

		while (!owner.compareAndSet(null, Thread.currentThread())) {

		}
	}

	public void unlock() {
		if (owner.get() == Thread.currentThread() && counter.decrementAndGet() == 0) {
			owner.set(null);
		}
	}
}
