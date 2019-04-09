package org.geeksforgeeks.code.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.joda.time.DateTime;

import com.codewars.kata.util.Utilities;
import com.codewars.kata.util.parser.gson.DateTimeConverter;
import com.codewars.kata.util.parser.gson.LocalDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//CHECKSTYLE:OFF
//Input : arr[] = {2, 3, 5, 6, 8, 10}
//        sum = 10
//Output : 5 2 3
//         2 8
//         10
//
//Input : arr[] = {1, 2, 3, 4, 5}
//        sum = 10
//Output : 4 3 2 1
//         5 3 2
//         5 4 1
//CHECKSTYLE:ON
public final class PerfectSumProblem {

	private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").registerTypeAdapter(DateTime.class,
			new DateTimeConverter()).registerTypeAdapter(LocalDate.class, new LocalDateConverter()).create();

	private PerfectSumProblem() {

	}

	public void quickSort(final int[] arr, final int lo, final int hi, final boolean reversal) {
		if (lo == hi) {
			return;
		}
		int i = lo;
		int j = hi;
		int middle = (lo + hi) / 2;
		int n = arr[middle];

		while (i <= j) {
			if (reversal) {
				while (arr[i] > n) {
					i++;
				}
				while (arr[j] < n) {
					j--;
				}
			} else {
				while (arr[i] < n) {
					i++;
				}
				while (arr[j] > n) {
					j--;
				}
			}

			if (i <= j) {
				int swap = arr[j];
				arr[j] = arr[i];
				arr[i] = swap;
				i++;
				j--;
			}
		}

		if (lo < j) {
			quickSort(arr, lo, j, reversal);
		}
		if (hi > i) {
			quickSort(arr, i, hi, reversal);
		}

	}

	public void sum(final int[] arr, final int value) {
		quickSort(arr, 0, arr.length - 1, false);
		final List<Integer> nums = new ArrayList<Integer>(0);
		int sum = 0;

		/*
		 * int sub = 1;
		 * for (int i = 0; i < arr.length; i++) {
		 * sum += arr[i];
		 * nums.add(arr[i]);
		 * if (sum == value) {
		 * System.out.println(nums.toString());
		 * i = sub++;
		 * }
		 * if (sum > value) {
		 * i = sub++;
		 * }
		 * }
		 */

		int pointer = arr[0];
		int pivot = 1;
		int matrix[][] = new int[arr.length][arr.length];
		int index = 0;
		boolean match = false;

		for (int i = 0; i < arr.length && !match; i++) {
			sum += arr[i];
			matrix[i][0] = arr[i];
			for (int j = i + 1; j < arr.length; j++) {
				sum += arr[j];
				if (sum <= value) {
					matrix[i][j] = arr[j];
				} else {
					System.out.println(GSON.toJson(matrix));
					break;
				}
			}
		}

	}

	// CHECKSTYLE:OFF
	public static void main(final String[] args) {
		final StopWatch watch = new StopWatch();
		watch.start();

		final int[] arr =
			{2, 3, 5, 6, 8, 10};

		final PerfectSumProblem perfectSumProblem = new PerfectSumProblem();
		perfectSumProblem.sum(arr, 10);

		watch.stop();
		System.out.println(Utilities.parseMilliseconds(watch.getTime()));
	}
	// CHECKSTYLE:ON

}
