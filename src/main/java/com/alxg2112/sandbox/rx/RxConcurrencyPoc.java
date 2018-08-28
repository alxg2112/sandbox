package com.alxg2112.sandbox.rx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * @author Alexander Gryshchenko
 */
public class RxConcurrencyPoc {

	private static final int N_THREADS = 8;

	public static void main(String[] args) throws InterruptedException {

		PublishSubject<String> publishSubject = PublishSubject.create();

		ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

		IntStream.range(0, N_THREADS).forEach(num -> executorService.submit(
				() -> {
//					publishSubject.subscribe(str ->
//							System.out.printf("Thread: %s. Got: '%s'%n",
//									Thread.currentThread().getName(),
//									str));
					Observable.just("SomeValue")
							.doOnNext(s -> System.out.printf("Thread: %s. Got: '%s'%n",
									Thread.currentThread().getName(),
									s))
							.subscribe();
				}
				)
		);

		Thread.sleep(1000);
		publishSubject.onNext("Whatever");
	}
}
