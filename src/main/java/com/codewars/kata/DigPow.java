package com.codewars.kata;

import org.apache.commons.lang3.time.StopWatch;

import com.codewars.kata.util.Utilities;

public final class DigPow {

	private DigPow() {

	}

	public static long digPow(final int n, final int p) {
		final char[] digits = String.format("%d", n).toCharArray();
		long result = 0;

		for (int i = 0; i < digits.length; i++) {
			result += Math.pow(digits[i] - '0', p + i);
		}

		int mod = (int) (result % n);
		if (mod == 0) {
			result /= n;
		} else {
			return -1;
		}

		return result;
	}

	public static void main(final String[] args) {
		final StopWatch watch = new StopWatch();
		watch.start();

		// digPow(46288, 3) should return 51 since 4³ + 6⁴+ 2⁵ + 8⁶ + 8⁷ = 2360688 =
		// 46288 * 51
		// DigPow
		System.out.println(String.format("%d", DigPow.digPow(Integer.valueOf("92"), 1)));

		watch.stop();
		System.out.println(Utilities.parseMilliseconds(watch.getTime()));
	}

}
