package org.geeksforgeeks.code.test;

// This code is contributed by Arnav Kr. Mandal.
public final class GFGMinDifferencesSubset {

	private GFGMinDifferencesSubset() {

	}

	// Function to find the minimum sum
	public static int findMinRec(final int[] arr, final int i, final int sumCalculated, final int sumTotal, final int dir) {
		// If we have reached last element.
		// Sum of one subset is sumCalculated,
		// sum of other subset is sumTotal-
		// sumCalculated. Return absolute
		// difference of two sums.
		if (i == 0) {
			int result = Math.abs((sumTotal - sumCalculated) - sumCalculated);
			if (dir == 1) {
				System.out.println(String.format("result: %d, i: %d, sumCalculated: %d, sumTotal: %d, dir: %d", result, i, sumCalculated, sumTotal, dir));
			}
			return result;
		}

		// For every item arr[i], we have two choices
		// (1) We do not include it first set
		// (2) We include it in first set
		// We return minimum of two choices
		return Math.min(findMinRec(arr, i - 1, sumCalculated + arr[i - 1], sumTotal, 1), findMinRec(arr, i - 1, sumCalculated, sumTotal, 0));
	}

	// Returns minimum possible difference between
	// sums of two subsets
	public static int findMin(final int[] arr, final int n) {
		// Compute total sum of elements
		int sumTotal = 0;
		for (int i = 0; i < n; i++) {
			sumTotal += arr[i];
		}

		// Compute result using recursive function
		return findMinRec(arr, n, 0, sumTotal, -1);
	}

	/* Driver program to test above function */
	public static void main(final String[] args) {
		final int[] arr =
			{3, 1, 4, 2, 2, 1};

		int n = arr.length;

		System.out.print("The minimum difference" + " between two sets is " + findMin(arr, n));
	}
}
