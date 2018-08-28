package com.alxg2112.sandbox.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Alexander Gryshchenko
 */
public class Interruption {

	private static volatile Thread thr;

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		Future<?> future = executorService.submit(() -> {
//			try {
			thr = Thread.currentThread();
			while (true) {
				Thread.sleep(1000);
				System.out.println("Did some work...");
			}
//			} catch (InterruptedException e) {
//				Thread.currentThread().interrupt();
//				System.out.println("Thread is interrupted: " + Thread.currentThread().getName() + " : " + Thread.currentThread().isInterrupted());
//			}
		});

		try {
			Thread.sleep(3000);
			thr.interrupt();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		future.cancel(true);
		future.cancel(true);
		future.get();

		executorService.submit(() -> {
			System.out.println("Info: " + Thread.currentThread().isInterrupted());

		});
	}
}
