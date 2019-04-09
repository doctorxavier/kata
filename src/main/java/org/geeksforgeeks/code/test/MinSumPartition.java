package org.geeksforgeeks.code.test;

import java.time.LocalDate;

import org.joda.time.DateTime;

import com.codewars.kata.util.parser.gson.DateTimeConverter;
import com.codewars.kata.util.parser.gson.LocalDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MinSumPartition {

	private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").registerTypeAdapter(DateTime.class,
			new DateTimeConverter()).registerTypeAdapter(LocalDate.class, new LocalDateConverter()).create();

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

	public int searchMax(final int[] nums, final int lo, final int hi) {
		int middle = (hi + lo) / 2;

		int leftMax = 0;
		int rightMax = 0;
		int pointer = nums[middle];

		if (lo >= hi) {
			return pointer;
		}

		if (lo < hi) {
			leftMax = searchMax(nums, lo, middle - 1);
		}

		if (hi > lo) {
			rightMax = searchMax(nums, middle + 1, hi);
		}

		if (leftMax > rightMax) {
			return leftMax;
		} else {
			return rightMax;
		}
	}

	public int test(final int[][] matrix, final int dim) {
		final int[][] middle = new int[dim][matrix.length];
		final int[][] finalTable = new int[matrix.length][dim];

		for (int i = 0; i < matrix.length; i++) {
			quickSort(matrix[i], 0, matrix[i].length - 1, true);
		}

		System.out.println(GSON.toJson(matrix));

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				middle[j][i] = matrix[i][j];
			}
		}

		System.out.println(GSON.toJson(middle) + "\n");

		for (int i = 0; i < middle.length; i++) {
			if (i % 2 == 0) {
				quickSort(middle[i], 0, middle[i].length - 1, true);
			} else {
				quickSort(middle[i], 0, middle[i].length - 1, false);
			}
		}

		System.out.println(GSON.toJson(middle) + "\n");

		for (int i = 0; i < middle.length; i++) {
			for (int j = 0; j < middle[i].length; j++) {
				finalTable[j][i] = middle[i][j];
			}
		}

		System.out.println(GSON.toJson(finalTable) + "\n");
		return 0;
	}

	public static void main(final String[] args) {
		final int[][] arr = new int[][]
			{
					{1, 6, 5, 11, 100},
					{36, 7, 46, 40, 120},
					{5, 11, 12, 53, 62}};

		final MinSumPartition minSumPartition = new MinSumPartition();

		System.out.println(GSON.toJson(arr) + "\n");

		System.out.println(GSON.toJson(minSumPartition.test(arr, 5)));

		// minSumPartition.divideToTwoSets(5, arr);
	}

}
