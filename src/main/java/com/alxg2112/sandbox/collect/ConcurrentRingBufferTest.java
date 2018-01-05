package com.alxg2112.sandbox.collect;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;

/**
 * @author Alexander Gryshchenko
 */
public class ConcurrentRingBufferTest {

	private static final int NUMBER_OF_PRODUCERS = 4;
	private static final int NUMBER_OF_CONSUMERS = 4;
	private static final int ELEMENTS_PER_PRODUCER = 250_000;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		AtomicLong counter = new AtomicLong(0);
		AtomicInteger leftToConsume = new AtomicInteger(ELEMENTS_PER_PRODUCER * NUMBER_OF_PRODUCERS);
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_PRODUCERS + NUMBER_OF_CONSUMERS);
		ConcurrentRingBuffer<Long> buffer = new ConcurrentRingBuffer<>(BUFFER_SIZE);
		Runnable producer = () -> {
			for (int i = 0; i < ELEMENTS_PER_PRODUCER; i++) {
				long newElement = counter.getAndIncrement();
				try {
					buffer.put(newElement);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		};
		Runnable consumer = () -> {
			while (leftToConsume.decrementAndGet() > 0) {
				Long element;
				try {
					element = buffer.take();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				Preconditions.checkNotNull(element);
			}
		};
		long start = System.currentTimeMillis();
		List<Future<?>> producersFutures = IntStream.range(0, NUMBER_OF_PRODUCERS)
				.mapToObj(num -> executorService.submit(producer))
				.collect(Collectors.toList());
		List<Future<?>> consumersFutures = IntStream.range(0, NUMBER_OF_CONSUMERS)
				.mapToObj(num -> executorService.submit(consumer))
				.collect(Collectors.toList());
		List<Future<?>> allFutures = new ArrayList<>();
		allFutures.addAll(producersFutures);
		allFutures.addAll(consumersFutures);
		executorService.shutdown();
		for (Future<?> future : allFutures) {
			future.get();
		}
		System.out.println("Time elapsed to produce and consume 1,000,000 elements is " + (System.currentTimeMillis() - start) + " millis");
	}
}
