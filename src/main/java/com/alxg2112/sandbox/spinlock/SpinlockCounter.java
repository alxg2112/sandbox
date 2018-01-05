package com.alxg2112.sandbox.spinlock;

/**
 * @author Alexander Gryshchenko
 */
public class SpinlockCounter {

	private final Spinlock spinlock = new Spinlock();
	private int value = 0;

	public void increment() {
		spinlock.lock();
		value++;
		spinlock.unlock();
	}

	public void decrement() {
		spinlock.lock();
		value--;
		spinlock.unlock();
	}

	public void set(int newValue) {
		spinlock.lock();
		value = newValue;
		spinlock.unlock();
	}

	public int get() {
		return value;
	}
}
