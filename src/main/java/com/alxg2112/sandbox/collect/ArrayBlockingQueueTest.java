package com.alxg2112.sandbox.collect;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Alexander Gryshchenko
 */
public class ArrayBlockingQueueTest {

	public static void mpmcPerformanceTest(int numberOfProducers,
										   int numberOfConsumers,
										   int elementsPerProducer,
										   int containerSize) throws ExecutionException, InterruptedException {
		System.out.printf("=====================[Testing ArrayBlockingQueue]=====================%n" +
						"Consumers: %s%n" +
						"Producers: %s%n" +
						"Elements per producer: %s%n" +
						"Array size: %s%n%n",
				numberOfConsumers, numberOfProducers, elementsPerProducer, containerSize);
		AtomicLong counter = new AtomicLong(0);
		AtomicInteger leftToConsume = new AtomicInteger(elementsPerProducer * numberOfProducers);
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfProducers + numberOfConsumers);
		BlockingQueue<Long> queue = new ArrayBlockingQueue<>(containerSize);
		Runnable producer = () -> {
			for (int i = 0; i < elementsPerProducer; i++) {
				long newElement = counter.getAndIncrement();
				try {
					queue.put(newElement);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		};
		Runnable consumer = () -> {
			while (leftToConsume.decrementAndGet() >= 0) {
				try {
					queue.take();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		};
		System.out.printf("Starting...%n%n");
		long start = System.currentTimeMillis();
		List<Future<?>> producersFutures = IntStream.range(0, numberOfProducers)
				.mapToObj(num -> executorService.submit(producer))
				.collect(Collectors.toList());
		List<Future<?>> consumersFutures = IntStream.range(0, numberOfConsumers)
				.mapToObj(num -> executorService.submit(consumer))
				.collect(Collectors.toList());
		List<Future<?>> allFutures = new ArrayList<>();
		allFutures.addAll(producersFutures);
		allFutures.addAll(consumersFutures);
		executorService.shutdown();
		for (Future<?> future : allFutures) {
			future.get();
		}
		System.out.printf("Time elapsed to produce and consume %s elements is %s millis%n" +
						"======================================================================%n%n",
				elementsPerProducer * numberOfProducers,
				(System.currentTimeMillis() - start));
	}
}
