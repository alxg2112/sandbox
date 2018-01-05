package com.alxg2112.sandbox.hashmap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import com.google.common.collect.Maps;

/**
 * @author Alexander Gryshchenko
 */
public class HashMapPoC {

	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
		Map<Integer, Integer> map = Maps.newHashMap();
		IntStream.range(0, 1000).forEach(number -> map.put(ThreadLocalRandom.current().nextInt(10000), number));
		Field tableField = map.getClass().getDeclaredField("table");
		tableField.setAccessible(true);
		Object[] table = (Object[]) tableField.get(map);
		int numberOfEntries = map.size();
		int numberOfBuckets = table.length;
		long numberOfNonNullBuckets = Arrays.stream(table).filter(Objects::nonNull).count();
		System.out.println("Total number of buckets: " + numberOfBuckets);
		System.out.println("Number of key-value pairs: " + numberOfEntries);
		System.out.println("Number of non-null buckets: " + numberOfNonNullBuckets);
	}
}
