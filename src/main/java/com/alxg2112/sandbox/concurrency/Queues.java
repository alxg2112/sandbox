package com.alxg2112.sandbox.concurrency;

import java.util.PriorityQueue;

/**
 * @author Alexander Gryshchenko
 */
public class Queues {

	public static void main(String[] args) {
//		BlockingDeque<Object> blockingDeque = new LinkedBlockingDeque<>();
//		System.out.println(blockingDeque.remainingCapacity());
//
//		Queue<String> queue = new ConcurrentLinkedQueue<>();
//		queue.add("1");
//		queue.add("2");
//		queue.add("3");
//		System.out.println(queue);

		PriorityQueue<Integer> queue = new PriorityQueue<>();

		queue.add(5);
		queue.add(3);
		queue.add(3);
		queue.add(7);

		while (true) {
			Integer element = queue.poll();
			System.out.println(element);
			if (element == null) {
				return;
			}
		}

//		System.out.println(queue);
	}
}
