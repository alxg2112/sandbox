package com.alxg2112.sandbox.regex;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import com.google.common.collect.Sets;

/**
 * @author Alexander Gryshchenko
 */
public class RegexTest {

	public static void main(String[] args) {

		System.out.println(Instant.now());
		System.out.println(Timestamp.from(Instant.now()));

		String message = "nginxMsg";
		AtomicReference<String> ref = new AtomicReference<>(message);
		System.out.println(ref.compareAndSet("nginxMsg", "sss"));

		Set<String> strings = Sets.newHashSet(null, null, "ssss", "ss");
		System.out.println(strings);

		String messageWithoutTopic = message.replaceFirst("(nginx|tomcat)", "");

		System.out.println(messageWithoutTopic);
	}
}
