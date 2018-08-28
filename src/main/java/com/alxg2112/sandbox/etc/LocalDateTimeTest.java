package com.alxg2112.sandbox.etc;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Alexander Gryshchenko
 */
public class LocalDateTimeTest {

	public static void main(String[] args) {
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println(localDateTime);
		System.out.println(localDateTime.atZone(ZoneId.of("UTC")).toInstant());
	}
}
