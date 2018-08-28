package com.alxg2112.sandbox.file;

import java.io.File;

/**
 * @author Alexander Gryshchenko
 */
public class FileTest {

	private static final String INSTANCE_STDERR_FILENAME_TEMPLATE =
			"instance-stderr"
					+ File.separator
					+ "${instanceId}-stderr.txt";

	public static void main(String[] args) {
//		System.out.println(new StringSubstitutor(Collections.singletonMap("instanceId", UUID.randomUUID().toString()))
//				.replace(INSTANCE_STDERR_FILENAME_TEMPLATE));

		int a = 0x00010;

		System.out.println(a);
	}
}
