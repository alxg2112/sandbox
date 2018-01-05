package com.alxg2112.sandbox.collect;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alexander Gryshchenko
 */
public class ConcurrentRingBuffer<E> implements Iterable<E> {

	private final E[] buffer;

	private final Lock lock = new ReentrantLock();
	private final Condition notEmpty = lock.newCondition();
	private final Condition notFull = lock.newCondition();

	private int elementCount;
	private int writePos;
	private int readPos;

	public ConcurrentRingBuffer(int bufferSize) {
		this.buffer = (E[]) new Object[bufferSize];
	}

	public void put(E element) throws InterruptedException {
		lock.lock();
		try {
			while (elementCount == buffer.length) {
				notFull.await();
			}
			buffer[writePos] = element;
			writePos = ++writePos % buffer.length;
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
			readPos = ++readPos % buffer.length;
			elementCount--;
			notFull.signal();
			return element;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			private final E[] buffer;
			private final int readPos;
			private final int writePos;
			private int pointer;

			{
				lock.lock();
				buffer = Arrays.copyOf(ConcurrentRingBuffer.this.buffer, ConcurrentRingBuffer.this.buffer.length);
				readPos = ConcurrentRingBuffer.this.readPos;
				writePos = ConcurrentRingBuffer.this.writePos;
				elementCount = ConcurrentRingBuffer.this.elementCount;
				pointer = readPos;
				lock.unlock();
			}

			@Override
			public boolean hasNext() {
				return pointer % this.buffer.length < this.writePos;
			}

			@Override
			public E next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				E item = this.buffer[pointer];
				pointer = ++pointer % this.buffer.length;
				return item;
			}
		};
	}
}
