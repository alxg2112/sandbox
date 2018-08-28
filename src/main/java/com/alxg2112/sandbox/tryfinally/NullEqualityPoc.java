package com.alxg2112.sandbox.tryfinally;

import java.util.HashMap;

/**
 * @author Alexander Gryshchenko
 */
public class NullEqualityPoc {

	public static void main(String[] args) {
		System.out.println(null == new HashMap<>().get(1));
	}
}
