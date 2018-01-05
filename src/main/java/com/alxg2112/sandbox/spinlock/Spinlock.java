package com.alxg2112.sandbox.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Simple spinlock implementation
 *
 * @author Alexander Gryshchenko
 */
public class Spinlock {

	private final AtomicReference<Thread> owner = new AtomicReference<>(null);

	public void lock() {
		while (!owner.compareAndSet(null, Thread.currentThread())) {

		}
	}

	public void unlock() {
		if (owner.get() == Thread.currentThread()) {
			owner.set(null);
		}
	}
}
