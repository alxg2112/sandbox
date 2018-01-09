package com.alxg2112.sandbox.collect;

import java.util.concurrent.ExecutionException;

/**
 * @author Alexander Gryshchenko
 */
public class PerformanceComparison {

	private static final int NUMBER_OF_PRODUCERS = 4;
	private static final int NUMBER_OF_CONSUMERS = 4;
	private static final int ELEMENTS_PER_PRODUCER = 1_000_000;
	private static final int CONTAINER_SIZE = 2056;

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ArrayBlockingQueueTest.mpmcPerformanceTest(NUMBER_OF_PRODUCERS, NUMBER_OF_CONSUMERS, ELEMENTS_PER_PRODUCER, CONTAINER_SIZE);
		ConcurrentRingBufferTest.mpmcPerformanceTest(NUMBER_OF_PRODUCERS, NUMBER_OF_CONSUMERS, ELEMENTS_PER_PRODUCER, CONTAINER_SIZE);
	}
}
