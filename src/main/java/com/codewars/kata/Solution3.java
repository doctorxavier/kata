package com.codewars.kata;

import org.apache.commons.lang3.time.StopWatch;

import com.codewars.kata.util.Utilities;

public final class Solution3 {

	private Solution3() {

	}

	public static void main(final String[] args) {
		final StopWatch watch = new StopWatch();
		watch.start();

		for (int i = 1; i < Integer.valueOf("300000"); i += 1) {
			Solution3.zeros(i);
			// System.out.println(String.format("i: %d, n: %d", i, Solution3.zeros(i)));
		}

		watch.stop();
		System.out.println(Utilities.parseMilliseconds(watch.getTime()));
	}

	public static int zeros(final int m) {
		int n = m;
		int totalDivTimes = 0;
		int pow1 = 0;
		int pow2 = 0;
		int puts = 0;
		n = n - (n % 5);

		for (int i = 2; pow1 <= n; i++) {
			pow1 = (int) Math.pow((int) 5, (int) i);
			for (int j = 2; pow2 <= n && pow1 <= n; j++) {
				pow2 = (int) Math.pow((int) 5, (int) i) * j;
				if (pow2 > n) {
					pow2 = 0;
					break;
				}
				if (j % 5 != 0) {
					totalDivTimes += i;
					puts++;
				}
			}
			if (pow1 > n) {
				continue;
			}
			totalDivTimes += i;
			puts++;
		}

		for (int i = n; i >= 5; i -= 5) {
			totalDivTimes++;
		}

		return totalDivTimes - puts;
	}

}
