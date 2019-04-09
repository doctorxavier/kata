package com.amazon.code.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.codewars.kata.util.parser.gson.DateTimeConverter;
import com.codewars.kata.util.parser.gson.LocalDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CodeTest1 {

	private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").registerTypeAdapter(DateTime.class,
			new DateTimeConverter()).registerTypeAdapter(LocalDate.class, new LocalDateConverter()).create();

	// CHECKSTYLE:OFF
	public List<Integer> cellCompete(int[] states, int days) {
		int[] result = new int[]
			{0, 1, 1, 1, 1, 1, 1, 1, 1, 0};

		int[] statesCopy = new int[]
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		for (int i = 1; i <= 8; i++) {
			statesCopy[i] = states[i - 1];
		}
		System.out.println(GSON.toJson(statesCopy));

		for (int i = 0; i < days; i++) {
			for (int j = 1; j <= 8; j++) {
				if (statesCopy[j - 1] == statesCopy[j + 1]) {
					result[j] = 0;
				}
			}
			System.out.println(GSON.toJson(result));
			copyStates(result, statesCopy);
			result = new int[]
				{0, 1, 1, 1, 1, 1, 1, 1, 1, 0};
		}

		final List<Integer> finalResult = new ArrayList<Integer>(8);
		for (int i = 1; i <= 8; i++) {
			finalResult.add(statesCopy[i]);
		}

		return finalResult;
	}
	// CHECKSTYLE:ON

	public void copyStates(final int[] orig, final int[] dest) {
		for (int i = 1; i < orig.length - 1; i++) {
			dest[i] = orig[i];
		}
	}

	public int gcd(final int a, final int b) {
		if (b == 0) {
			System.out.println(a);
			return a;
		} else {
			System.out.println("a: " + a + ", b: " + b + ", a mod b: " + a % b);
			return gcd(b, a % b);
		}
	}

	public int mgcd(final int[] nums, final int lo, final int hi) {
		if (lo == hi) {
			return nums[lo];
		}
		int mid = lo + (hi - lo) / 2;
		return gcd(mgcd(nums, lo, mid), mgcd(nums, mid + 1, hi));
	}

	public int generalizedGCD(final int num, final int[] arr) {
		int gcd = arr[0];

		if (num <= 0) {
			throw new RuntimeException("Empty list.");
		}

		if (num > arr.length) {
			throw new RuntimeException("Too much numbers.");
		}

		if (num == 1) {
			return Math.abs(arr[0]);
		}

		gcd = mgcd(arr, 0, num - 1);
		return gcd;
	}

	public int generalizedGCD2(final int num, final int[] arr) {
		int gcd = arr[0];

		if (num <= 0) {
			throw new RuntimeException("Empty list.");
		}

		if (num > arr.length) {
			throw new RuntimeException("Too much numbers.");
		}

		if (num == 1) {
			return Math.abs(arr[0]);
		}

		for (int i = 0; i < num; i++) {
			if (arr[i] > 0) {
				if (Math.abs(arr[i]) < gcd) {
					gcd = arr[i];
				}
			}
		}

		for (int i = 0; i < num; i++) {
			if (arr[i] > 0 && arr[i] % gcd != 0) {
				return 1;
			}
		}
		return gcd;
	}

	// CHECKSTYLE:OFF
	public static void main(final String[] args) {
		int[] array = new int[]
			{120, 12, 4, 24, 120};

		final CodeTest1 codeTest = new CodeTest1();

		System.out.println(GSON.toJson(array) + "\n");

		// System.out.println(GSON.toJson(codeTest.generalizedGCD(5, array)));

		System.out.println("\n" + GSON.toJson(codeTest.gcd(24, 120)));
	}
	// CHECKSTYLE:ON
}
