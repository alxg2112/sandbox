package com.alxg2112.sandbox.collect;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author Alexander Gryshchenko
 */
public class IterationPerformanceTest {

	private static final long NUMBER_OF_ELEMENTS = 10_000_000L;

	public static void main(String[] args) {
		Set<Long> longSet = Sets.newCopyOnWriteArraySet(LongStream.range(0, NUMBER_OF_ELEMENTS).boxed().collect(Collectors.toList()));
		List<Long> longList = Lists.newCopyOnWriteArrayList(LongStream.range(0, NUMBER_OF_ELEMENTS).boxed().collect(Collectors.toList()));

		long setAdditionStart = System.currentTimeMillis();
//		LongStream.range(0, NUMBER_OF_ELEMENTS).forEach(longSet::add);
		long setAdditionEnd = System.currentTimeMillis();
		System.out.println("HashSet addition time: " + (setAdditionEnd - setAdditionStart));

		long listAdditionStart = System.currentTimeMillis();
//		LongStream.range(0, NUMBER_OF_ELEMENTS).forEach(longList::add);
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
