package com.codewars.kata;

import org.apache.commons.lang3.time.StopWatch;

import com.codewars.kata.util.Utilities;

public final class Solution {

	private Solution() {

	}

	public static void main(final String[] args) {
		final StopWatch watch = new StopWatch();
		watch.start();

		for (int i = 1; i < Integer.valueOf("3000"); i += 1) {
			Solution.zeros(i);
			// System.out.println(String.format("i: %d, n: %d", i, Solution.zeros(i)));
		}

		watch.stop();
		System.out.println(Utilities.parseMilliseconds(watch.getTime()));
	}

	public static int zeros(final int n) {
		int totalDivTimes = 0;

		for (int i = 1; i <= n; i += 1) {

			int mod = i % 5;

			if (mod == 0) {
				int divTimes = divisibleTimes(i - mod, 5);
				totalDivTimes += divTimes;
			}
		}
		return totalDivTimes;
	}

	public static int divisibleTimes(final int n, final int div) {
		long rest = n;
		int times = 0;
		while (rest != 0 && rest % div == 0) {
			times++;
			rest /= div;
		}
		return times;
	}

}
