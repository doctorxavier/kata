package com.codewars.basics;

import java.time.LocalDate;

import org.joda.time.DateTime;

import com.codewars.kata.util.parser.gson.DateTimeConverter;
import com.codewars.kata.util.parser.gson.LocalDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class QuickSortExample {

	private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
			.registerTypeAdapter(DateTime.class, new DateTimeConverter())
			.registerTypeAdapter(LocalDate.class, new LocalDateConverter()).create();

	private QuickSortExample() {

	}

	// CHECKSTYLE:OFF
	public static void main(final String[] args) {
		// This is unsorted array
		Integer[] array = new Integer[]
			{9, 12, 24, 10, 3, 6, 90, 70};

		System.out.println(GSON.toJson(array) + "\n");

		// Let's sort using quick sort
		quickSort(array, 0, array.length - 1);

		// Verify sorted array
		System.out.println("\n" + GSON.toJson(array));
	}
	// CHECKSTYLE:ON

	public static void quickSort(final Integer[] arr, final int low, final int high) {
		// check for empty or null array
		if (arr == null || arr.length == 0) {
			return;
		}

		if (low >= high) {
			return;
		}

		// Get the pivot element from the middle of the list
		int middle = low + (high - low) / 2;
		int pivot = arr[middle];

		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			// Check until all values on left side array are lower than pivot
			while (arr[i] < pivot) {
				i++;
			}
			// Check until all values on left side array are greater than pivot
			while (arr[j] > pivot) {
				j--;
			}
			// Now compare values from both side of lists to see if they need
			// swapping
			// After swapping move the iterator on both lists
			if (i <= j) {
				swap(arr, i, j);
				System.out.println(GSON.toJson(arr));
				i++;
				j--;
			}
		}
		// Do same operation as above recursively to sort two sub arrays
		if (low < j) {
			quickSort(arr, low, j);
		}
		if (high > i) {
			quickSort(arr, i, high);
		}
	}

	public static void swap(final Integer[] array, final int x, final int y) {
		int temp = array[x];
		array[x] = array[y];
		array[y] = temp;
	}

}
