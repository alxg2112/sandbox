package com.alxg2112.sandbox;

/**
 * @author Alexander Gryshchenko
 */
public class DeadlockSimulation {

	public static void main(String[] args) throws InterruptedException {
		Thread.currentThread().join();
	}
}
