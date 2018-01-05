package com.alxg2112.sandbox.collect;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.LongStream;

/**
 * @author Alexander Gryshchenko
 */
public class IterationPerformanceTest {

	private static final long NUMBER_OF_ELEMENTS = 10_000_000L;

	public static void main(String[] args) {
		Set<Long> longSet = new HashSet<>();
		List<Long> longList = new ArrayList<>();

		long setAdditionStart = System.currentTimeMillis();
		LongStream.range(0, NUMBER_OF_ELEMENTS).forEach(longSet::add);
		long setAdditionEnd = System.currentTimeMillis();
		System.out.println("HashSet addition time: " + (setAdditionEnd - setAdditionStart));

		long listAdditionStart = System.currentTimeMillis();
		LongStream.range(0, NUMBER_OF_ELEMENTS).forEach(longList::add);
		long listAdditionEnd = System.currentTimeMillis();
		System.out.println("ArrayList addition time: " + (listAdditionEnd - listAdditionStart));

		Consumer<Object> doNothing = param -> {};

		long setIterationStart = System.currentTimeMillis();
		longSet.forEach(doNothing);
		long setIterationEnd = System.currentTimeMillis();
		System.out.println("HashSet iteration time: " + (setIterationEnd - setIterationStart));

		long listIterationStart = System.currentTimeMillis();
		longList.forEach(doNothing);
		long listIterationEnd = System.currentTimeMillis();
		System.out.println("ArrayList iteration time: " + (listIterationEnd - listIterationStart));
	}
}
