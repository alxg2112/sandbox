package com.alxg2112.sandbox.concurrency;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alexander Gryshchenko
 */
public class CompletableFut {

	private final Lock reconfigurationLock = new ReentrantLock();
	private final Condition condition = reconfigurationLock.newCondition();

	public static void main(String[] args) {
		CompletableFut completableFut = new CompletableFut();

		try {
			completableFut.reconfigureCluster().get();
		} catch (InterruptedException e) {
			System.out.println(e);
		} catch (ExecutionException e) {
			System.out.println(e);
		}
//		Future fut2 = completableFut.reconfigureCluster();
//
//		System.out.println("calling fut1" + " " + Instant.now());
//		fut1.get();
//		System.out.println("calling fut2" + " " + Instant.now());
//		fut2.get();
//		System.out.println("called fut2");
	}

	public Future<Void> reconfigureCluster() throws ExecutionException, InterruptedException {
		CompletableFuture<Void> startFuture = processThatTakesTwoSeconds();
		CompletableFuture<Void> shutdownFuture = processThatTakesTwoSeconds();
		return CompletableFuture
				.runAsync(() -> {
					reconfigurationLock.lock();
					System.out.println("Entered lock." + Thread.currentThread().getName() + " " + Instant.now());
					try {
						System.out.println("Start. " + Thread.currentThread().getName() + " " + Instant.now());
						startFuture.get();
						System.out.println("Shutdown. " + Thread.currentThread().getName() + " " + Instant.now());
						shutdownFuture.get();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					} catch (ExecutionException e) {
						System.out.println(e);
					} finally {
						reconfigurationLock.unlock();
						System.out.println("Gave up lock." + Thread.currentThread().getName() + " " + Instant.now());
					}
				});
	}

	private CompletableFuture<Void> processThatTakesTwoSeconds() throws InterruptedException {
		CompletableFuture<Void> future = new CompletableFuture<>();
		Thread.sleep(2000);
		future.completeExceptionally(new IOException("IO"));
		return future;
	}
}
