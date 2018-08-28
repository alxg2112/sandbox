package com.alxg2112.sandbox.tryfinally;

import java.util.Map;

/**
 * @author Alexander Gryshchenko
 */
public class TryFunallyPoC {

	private static Class getClassForName(String clazz) {
		try {
			return Class.forName(clazz);
		} finally {
			return Map.class;
		}
	}

	public static void main(String[] args) {
		System.out.println(getClassForName("java.lang.String"));
	}
}
