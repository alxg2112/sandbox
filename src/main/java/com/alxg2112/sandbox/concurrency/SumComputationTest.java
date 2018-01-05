package com.alxg2112.sandbox.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * @author Alexander Gryshchenko
 */
public class SumComputationTest {

	private static final int NUMBER_OF_THREADS = 8;
	private static final long LOWER_BOUND = 9992346L;
	private static final long UPPER_BOUND = 23468789863L;

	private static void findSumUsingRecursion() throws ExecutionException, InterruptedException {
		ForkJoinPool forkJoinPool = new ForkJoinPool(NUMBER_OF_THREADS);
		long start = System.currentTimeMillis();
		Future<Long> future = forkJoinPool.submit(new SumComputationTask(LOWER_BOUND, UPPER_BOUND));
		long result = future.get();
		long finish = System.currentTimeMillis();
		System.out.println("Sum calculated using recursion. Result: " + result + ". Time elapsed: " + (finish - start) + " millis.");
	}

	private static void findSumTrivially() {
		long start = System.currentTimeMillis();
		long result = 0;
		for (long i = LOWER_BOUND; i <= UPPER_BOUND; i++) {
			result += i;
		}
		long finish = System.currentTimeMillis();
		System.out.println("Sum calculated trivially. Result: " + result + ". Time elapsed: " + (finish - start) + " millis.");
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		findSumUsingRecursion();
		findSumTrivially();
	}
}
