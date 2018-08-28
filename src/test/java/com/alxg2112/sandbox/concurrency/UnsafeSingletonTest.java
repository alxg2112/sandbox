package com.alxg2112.sandbox.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Alexander Gryshchenko
 */
@RunWith(Parameterized.class)
public class UnsafeSingletonTest {

	private static final int N_THEARDS = 8;

	@Parameterized.Parameters
	public static Object[][] data() {
		return new Object[10000][0];
	}

	@Test
	public void test() {
		ExecutorService executorService = Executors.newFixedThreadPool(N_THEARDS);

		for (int i = 0; i < N_THEARDS; i++) {
			executorService.submit(() -> {
				Assert.assertTrue(UnsafeSingleton.getInstance().value == 42);
			});
		}

		executorService.shutdown();
	}
}
