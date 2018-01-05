package com.alxg2112.sandbox.concurrency;

import java.util.List;
import java.util.concurrent.RecursiveTask;

import com.google.common.collect.Lists;

/**
 * @author Alexander Gryshchenko
 */
public class SumComputationTask extends RecursiveTask<Long> {

	private static final long THRESHOLD = 10000L;

	private long startInclusive;
	private long endInclusive;

	public SumComputationTask(long startInclusive, long endInclusive) {
		this.startInclusive = startInclusive;
		this.endInclusive = endInclusive;
	}

	@Override
	protected Long compute() {
		if (endInclusive - startInclusive > THRESHOLD) {
			List<SumComputationTask> subtasks = createSubtasks();
			subtasks.forEach(SumComputationTask::fork);

			long result = 0;
			for (SumComputationTask subtask : subtasks) {
				result += subtask.join();
			}

			return result;
		} else {
			return sumOfRange(startInclusive, endInclusive);
		}
	}

	private long sumOfRange(long startInclusive, long endInclusive) {
		long result = 0L;
		for (long i = startInclusive; i <= endInclusive; i++) {
			result += i;
		}
		return result;
	}

	private List<SumComputationTask> createSubtasks() {
		return Lists.newArrayList(
				new SumComputationTask(startInclusive, (endInclusive + startInclusive) / 2L),
				new SumComputationTask(((endInclusive + startInclusive) / 2L + 1), endInclusive)
		);
	}
}
