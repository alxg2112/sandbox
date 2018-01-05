package com.alxg2112.sandbox.spinlock;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

/**
 * @author Alexander Gryshchenko
 */
public class Test {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpinlockCounter spinlockCounter = new SpinlockCounter();
		ExecutorService executorService = Executors.newFixedThreadPool(8);
		List<Future<?>> futures = Lists.newArrayList();
		futures.addAll(IntStream.range(0, 4)
				.mapToObj(number ->
						executorService.submit(
								() -> IntStream.range(0, 10000).forEach(num -> spinlockCounter.increment())
						)
				).collect(Collectors.toList()));
		futures.addAll(IntStream.range(0, 4)
				.mapToObj(number ->
						executorService.submit(
								() -> IntStream.range(0, 10000).forEach(num -> spinlockCounter.decrement())
						)
				).collect(Collectors.toList()));
		executorService.shutdown();
		for (Future<?> future : futures) {
			future.get();
		}
		System.out.println(spinlockCounter.get());
	}
}
