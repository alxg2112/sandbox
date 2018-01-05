package com.alxg2112.sandbox.collect;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alexander Gryshchenko
 */
public class ConcurrentRingBuffer<E> {

	private final int bufferSize;
	private final E[] buffer;

	private final Lock lock = new ReentrantLock();
	private final Condition notEmpty = lock.newCondition();
	private final Condition notFull = lock.newCondition();

	private int elementCount;
	private int writePos;
	private int readPos;

	public ConcurrentRingBuffer(int bufferSize) {
		this.bufferSize = bufferSize;
		this.buffer = (E[]) new Object[bufferSize];
	}

	public void put(E element) throws InterruptedException {
		lock.lock();
		try {
			while (elementCount == bufferSize) {
				notFull.await();
			}
			buffer[writePos] = element;
			writePos = ++writePos % bufferSize;
			elementCount++;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}


	public E take() throws InterruptedException {
		lock.lock();
		try {
			while (elementCount == 0) {
				notEmpty.await();
			}
			E element = buffer[readPos];
			buffer[readPos] = null;
			readPos = ++readPos % bufferSize;
			elementCount--;
			notFull.signal();
			return element;
		} finally {
			lock.unlock();
		}
	}
}
