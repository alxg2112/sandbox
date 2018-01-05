package com.alxg2112.sandbox.oom;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * @author Alexander Gryshchenko
 */
public class OOMSimulator {

	private static final int TWO_MEGABYTES = 2 * 1024 * 1024;

	public static void main(String[] args) {
		List<byte[]> listOfBigArrays = new CopyOnWriteArrayList<>();
		Runnable heapEater = () -> {
			while (true) {
				byte[] newArray = new byte[TWO_MEGABYTES];
				listOfBigArrays.add(newArray);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Available memory: " + Runtime.getRuntime().freeMemory() / (1024 * 1024D) + " megabytes");
			}
		};
		IntStream.range(0, 4).forEach(num -> {
			Thread thread = new Thread(heapEater);
			thread.setDaemon(true);
			thread.start();
		});
		throw new RuntimeException();
	}
}
