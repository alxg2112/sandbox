package com.alxg2112.sandbox.joiner;

import java.util.Collection;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * @author Alexander Gryshchenko
 */
public class JoinerPoC {

	public static void main(String[] args) {
		Collection<Long> numbers = Lists.newArrayList(10L, null, 11L);
		String numbersNewLineSeparated = Joiner.on('\n').join(numbers);
		System.out.println(numbersNewLineSeparated);
	}
}
