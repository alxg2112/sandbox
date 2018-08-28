package com.alxg2112.sandbox.concurrency;

import java.util.concurrent.*;

/**
 * @author Alexander Gryshchenko
 */
public class SafePublishingTest {

	private static Subject subject;

	public static class Subject {

		private int value;

		public Subject(int value) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ScheduledExecutorService setterExecutorService = Executors.newSingleThreadScheduledExecutor();
		ExecutorService checkerExecutorService = Executors.newSingleThreadExecutor();

		Runnable subjectSetter = () -> {
			subject = new Subject(10);
		};

		Runnable subjectChecker = () -> {
			while (true) {
				if (subject != null) {
					if (subject.getValue() == 0) {
						throw new AssertionError();
					} else {
						break;
					}
				}
			}
		};

		setterExecutorService.schedule(subjectSetter, 5, TimeUnit.SECONDS);
		checkerExecutorService.submit(subjectChecker).get();

		setterExecutorService.shutdown();
		checkerExecutorService.shutdown();
	}
}
