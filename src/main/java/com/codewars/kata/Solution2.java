package com.codewars.kata;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.StopWatch;

import com.santander.api.util.Utilities;

public class Solution2 {

	public static void main(String[] args) {
		final StopWatch watch = new StopWatch();
		watch.start();

		/* for (int i = 100; i <= 125; i++) {
			System.out.println(String.format("n: %d, mod: %f, div: %f", i, i % 4.0d, i / 4.0d));
		} */
		
		Solution2.find();

		watch.stop();
		System.out.println(Utilities.parseMilliseconds(watch.getTime()));
	}
	
	public static void find() {
		int matches = 0;

		int totalDivTimes = 0;
		
		for (int i = 1; i < 4000; i += 1) {

			String factor = FactDecomp.factor(BigInteger.valueOf(i)).toString();

			StringBuilder sb = new StringBuilder();

			for (int x = factor.length() - 1; x >= 0; x--) {
				sb.append(factor.charAt(x));
			}

			String factor2 = sb.toString();

			Pattern pattern = Pattern.compile("[1-9]");
			Matcher matcher = pattern.matcher(factor2);

			if (matcher.find()) {
				int match = matcher.start();
				int mod = i - (i % 5);
				mod--;
				int ant = mod - (mod % 5);
				int divTimes = divisibleTimes(i - (i % 5), 5);
				
				int calculate = ant / 4;
				
				if(i % 5 == 0) {
					totalDivTimes += divTimes;
				}

				System.out.println(String.format("n: %d, matcher: %d, mod4: %f, mod5: %f, div4: %f, div5: %f, ant: %d, num: %d, divTimes: %d, totalDivTimes: %d", 
						i, matcher.start(), i % 4.0d, i % 5.0d, i / 4.0d, i / 5.0d, ant, calculate, divTimes, totalDivTimes));
			}
		}
		System.out.println(matches);
		// System.out.println(FactDecomp.factor(BigInteger.valueOf(200)));
	}
	
	public static int divisibleTimes(final int n, final int div) {
		int rest = n;
		int times = 0;
		while (rest != 0 && rest % div == 0) {
			times++;
			rest /= div;
		}
		return times;
	}

}
