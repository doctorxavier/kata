package com.codewars.kata;

import java.math.BigInteger;

import org.apache.commons.lang3.time.StopWatch;

import com.codewars.kata.util.Utilities;

public final class FactDecomp {

	private FactDecomp() {

	}

	public static void main(final String[] args) {
		final StopWatch watch = new StopWatch();
		watch.start();

		System.out.println(FactDecomp.decomp2(Integer.valueOf("4000")));

		watch.stop();
		System.out.println(Utilities.parseMilliseconds(watch.getTime()));
	}

	public static String decomp2(final int m) {
		int n = m;
		int[] exponentsOfPrimes = new int[n + 1];
		while (n > 1) {
			int x = n--;
			for (int i = 2; i <= Math.sqrt(x); i++) {
				if (x % i == 0) {
					x /= i;
					exponentsOfPrimes[i]++;
					i = 1;
				}
			}
			exponentsOfPrimes[x]++;
		}
		StringBuilder result = new StringBuilder();
		for (int i = 2; i < exponentsOfPrimes.length; i++) {
			if (exponentsOfPrimes[i] == 0) {
				continue;
			}
			if (exponentsOfPrimes[i] == 1) {
				result.append(i + " * ");
			}
			if (exponentsOfPrimes[i] > 1) {
				result.append(i + "^" + exponentsOfPrimes[i] + " * ");
			}
		}
		return result.substring(0, result.length() - 3);
	}

	public static String decomp(final int n) {
		final StringBuffer stringBuffer = new StringBuffer();
		final BigInteger factor = factor(BigInteger.valueOf(n));
		boolean divisible = true;

		for (int i = 2; i <= n; i++) {
			if (n == 2 || isPrime(i)) {
				stringBuffer.append(String.format("%d", i));
				int d = divisibleTimes2(n, factor, i);
				if (d > 1 && divisible) {
					stringBuffer.append(String.format("^%d * ", d));
				} else {
					stringBuffer.append(" * ");
					divisible = false;
				}
			}
		}
		String decomp = stringBuffer.toString();
		if (decomp.length() > 3) {
			decomp = decomp.substring(0, decomp.length() - 3);
		}
		return decomp;
	}

	public static int divisibleTimes(final BigInteger factor, final int n) {

		final BigInteger bigN = BigInteger.valueOf(n);

		if (factor.compareTo(BigInteger.ZERO) == 0 || factor.mod(bigN).compareTo(BigInteger.ZERO) != 0) {
			return 0;
		}
		int times = 1;

		BigInteger res = factor.divide(bigN);

		while (res.mod(bigN).compareTo(BigInteger.ZERO) == 0) {
			times++;
			res = res.divide(bigN);
		}
		return times;
	}

	public static int divisibleTimes2(final int n, final BigInteger factor, final int p) {
		int exp = n / (p - 1);

		BigInteger subFactor = BigInteger.valueOf(p).pow(exp);

		while (factor.mod(subFactor).compareTo(BigInteger.ZERO) != 0) {
			subFactor = BigInteger.valueOf(p).pow(--exp);
		}

		return exp;
	}

	public static BigInteger factor(final BigInteger n) {

		if (n.compareTo(BigInteger.ZERO) == 0) {
			return BigInteger.ZERO;
		}
		BigInteger factor = n;
		for (int i = n.intValue() - 1; i >= 1; i--) {
			factor = factor.multiply(BigInteger.valueOf(i));
		}
		return factor;
	}

	public static boolean isPrime(final int n) {

		if (n <= 1) {
			return false;
		}
		for (int i = 2; i < n; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

}
