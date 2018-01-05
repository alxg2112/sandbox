package com.alxg2112.sandbox.collect;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alexander Gryshchenko
 */
public class ConcurrentRingBuffer<E> {

	private final int bufferSize;
	private final E[] buffer;
	private final AtomicInteger elementCount = new AtomicInteger(0);
	private final AtomicInteger writePos = new AtomicInteger(0);
	private final AtomicInteger readPos = new AtomicInteger(0);

	private final Lock lock = new ReentrantLock();
	private final Condition notEmpty = lock.newCondition();
	private final Condition notFull = lock.newCondition();

	public ConcurrentRingBuffer(int bufferSize) {
		this.bufferSize = bufferSize;
		this.buffer = (E[]) new Object[bufferSize];
	}

	public void put(E element) throws InterruptedException {
		lock.lock();
		try {
			while (elementCount.get() == bufferSize) {
				notFull.await();
			}
			buffer[writePos.getAndUpdate(x -> ++x % bufferSize)] = element;
			elementCount.incrementAndGet();
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}


	public E take() throws InterruptedException {
		lock.lock();
		try {
			while (elementCount.get() == 0) {
				notEmpty.await();
			}
			int pos = readPos.getAndUpdate(x -> ++x % bufferSize);
			E element = buffer[pos];
			buffer[pos] = null;
			elementCount.decrementAndGet();
			notFull.signal();
			return element;
		} finally {
			lock.unlock();
		}
	}
}
