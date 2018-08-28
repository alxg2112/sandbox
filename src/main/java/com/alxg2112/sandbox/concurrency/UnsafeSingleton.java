package com.alxg2112.sandbox.concurrency;

/**
 * @author Alexander Gryshchenko
 */
public class UnsafeSingleton {

	private static UnsafeSingleton instance;

	int value;

	public static UnsafeSingleton getInstance() {
		UnsafeSingleton result = instance;
		if (result == null) {
			synchronized (UnsafeSingleton.class) {
				if (result == null) {
					result = instance = new UnsafeSingleton();
				}
			}
		}
		return result;
	}

	public UnsafeSingleton() {
		value = 42;
	}
}
