package com.alxg2112.sandbox.concurrency;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Alexander Gryshchenko
 */
public class StatefulObject {

	private static final int NUMBER_OF_THREADS = 10;

	private State state;

	public StatefulObject() {
		state = new State();
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public static class State {

		private AtomicInteger counter;

		public State() {
			counter = new AtomicInteger();
		}

		public AtomicInteger getCounter() {
			return counter;
		}

		public void setCounter(AtomicInteger counter) {
			this.counter = counter;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		StatefulObject object = new StatefulObject();

		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

		AtomicInteger oldCounter = new AtomicInteger();
		AtomicInteger newCounter = new AtomicInteger();

		object.getState().setCounter(oldCounter);

		List<Long> oldTimestamps = new CopyOnWriteArrayList<>();
		List<Long> newTimestamps = new CopyOnWriteArrayList<>();

		List<Future> futures = IntStream.range(0, NUMBER_OF_THREADS)
				.mapToObj(num -> executorService.submit(() -> {
					for (int i = 0; i < 1000; i++) {
						long timestamp = System.nanoTime();
						AtomicInteger counter = object.getState().getCounter();
						counter.incrementAndGet();
						if (counter.equals(oldCounter)) {
							oldTimestamps.add(timestamp);
						} else {
							newTimestamps.add(timestamp);
						}
					}
				})).collect(Collectors.toList());

		executorService.shutdown();

//		object.getState().setCounter(newCounter);
		State newState = new State();
		newState.setCounter(newCounter);
		object.setState(newState);

		futures.forEach(future -> {
			try {
				future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});

		System.out.printf("Counter: %s%n", object.getState().getCounter().get());
		Collections.sort(oldTimestamps);
		Collections.sort(newTimestamps);
		long lastSeenOld = oldTimestamps.get(oldTimestamps.size() - 1);
		long firstSeenNew = newTimestamps.get(0);
		System.out.printf("Last seen old counter: %s%n", lastSeenOld);
		System.out.printf("First seen new counter: %s%n", firstSeenNew);
		System.out.printf("Old was seen after the new: %s%n", lastSeenOld > firstSeenNew);
		System.out.printf("Old was seen %s nanoseconds after the new%n", lastSeenOld - firstSeenNew);
	}
}