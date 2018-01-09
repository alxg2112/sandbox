package com.alxg2112.sandbox.collect;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * @author Alexander Gryshchenko
 */
public class ConcurrentRingBuffer<E> {

	private final AtomicReferenceArray<E> buffer;

	private final AtomicInteger writePos = new AtomicInteger(0);
	private final AtomicInteger readPos = new AtomicInteger(0);

	public ConcurrentRingBuffer(int bufferSize) {
		this.buffer = new AtomicReferenceArray<>(bufferSize);
	}

	public void put(E element) throws InterruptedException {
		while (!buffer.compareAndSet(
				writePos.getAndUpdate(x -> ++x % buffer.length()),
				null,
				element)) {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
		}
	}

	public E take() throws InterruptedException {
		E element;
		do {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			element = buffer.getAndSet(readPos.getAndUpdate(x -> ++x % buffer.length()), null);
		} while (element == null);
		return element;
	}
}
