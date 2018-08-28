package com.alxg2112.sandbox.concurrency;

/**
 * @author Alexander Gryshchenko
 */
public class Visibility {

	private volatile SomeData data;

	public static class SomeData {

		private int number;

		public SomeData(int number) {
			this.number = number;
		}

		public int getNumber() {
			return number;
		}
	}

	public void initialize() {
		data = new SomeData(42);
	}

	public SomeData getData() {
		return data;
	}
}

		/*
		If this field were final, any thread that sees 'data' field
		is not not null is also guaranteed to see 'number'
		as '42'.

		Do we have the same guarantees for non final field?
		In other words, is it possible for some thread to observe non null
		'data' reference, but 'number' field as 0?
		*/

