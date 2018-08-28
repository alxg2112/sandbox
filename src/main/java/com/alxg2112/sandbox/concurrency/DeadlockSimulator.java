package com.alxg2112.sandbox.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Alexander Gryshchenko
 */
public class DeadlockSimulator implements AutoCloseable {

	private final Object lock1 = new Object();
	private final Object lock2 = new Object();

	private final ExecutorService executorService = Executors.newFixedThreadPool(2);

	public static void main(String[] args) throws Exception {
		try (DeadlockSimulator deadlockSimulator = new DeadlockSimulator()) {
			deadlockSimulator.simulateDeadlock();
		}
	}

	public void simulateDeadlock() {
		executorService.submit(this::bar);
		executorService.submit(this::foo);
	}

	private void foo() {
		System.out.println(Thread.currentThread().getName() + " is waiting for lock1...");
		synchronized (lock1) {
			doSomeWork();
			System.out.println(Thread.currentThread().getName() + " is waiting for lock2...");
			synchronized (lock2) {
				System.out.println(Thread.currentThread().getName() + " is in critical section");
			}
		}
	}

	private void bar() {
		System.out.println(Thread.currentThread().getName() + " is waiting for lock2...");
		synchronized (lock2) {
			doSomeWork();
			System.out.println(Thread.currentThread().getName() + " is waiting for lock1...");
			synchronized (lock1) {
				System.out.println(Thread.currentThread().getName() + " is in critical section");
			}
		}
	}

	private void doSomeWork() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws Exception {
		executorService.shutdown();
	}
}
