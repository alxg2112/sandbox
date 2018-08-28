package com.alxg2112.sandbox.properties;

import com.google.common.base.Joiner;

/**
 * @author Alexander Gryshchenko
 */
public class SystemProps {

	public static void main(String[] args) {
		System.out.println(Joiner.on('\n').withKeyValueSeparator('=').join(System.getProperties()));
	}
}
