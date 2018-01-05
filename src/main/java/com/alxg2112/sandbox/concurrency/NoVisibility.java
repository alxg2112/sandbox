package com.alxg2112.sandbox.concurrency;

import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

/**
 * @author Alexander Gryshchenko
 */
public class NoVisibility {

	private static boolean ready;
	private static int number;

	private static class ReaderThread extends Thread {

		public void run() {
			while (!ready)
				Thread.yield();
			System.out.println(number);
		}
	}

	public static void main(String[] args) {
//		new ReaderThread().start();
//		number = 42;
//		ready = true;
//		String headersString = "asdgasdg=asdgasdg; asdgasdgasdg=sadgasdgasdg;aasdga";
//		Map<String, String> headers = Splitter.on(";")
//				.trimResults()
//				.omitEmptyStrings()
//				.withKeyValueSeparator("=")
//				.split(headersString);
//		System.out.println(headers);
		Map<String, Integer> salary = Maps.newHashMap();
		salary.put("John", 1000);
		salary.put("Jane", 1500);

		String salaries = Joiner.on("; ")
				.withKeyValueSeparator("=")
				.join(salary);
		System.out.println(salaries);
	}
}
