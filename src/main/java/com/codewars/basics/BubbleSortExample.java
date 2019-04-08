package com.codewars.basics;

import java.time.LocalDate;

import org.joda.time.DateTime;

import com.codewars.kata.util.parser.gson.DateTimeConverter;
import com.codewars.kata.util.parser.gson.LocalDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class BubbleSortExample {
	
	private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
			.registerTypeAdapter(DateTime.class, new DateTimeConverter())
			.registerTypeAdapter(LocalDate.class, new LocalDateConverter()).create();
	
	private BubbleSortExample() {

	}

	// CHECKSTYLE:OFF
	public static void main(final String[] args) {
		// This is unsorted array
		Integer[] array = new Integer[]
			{12, 13, 24, 10, 3, 6, 90, 70};

		System.out.println(GSON.toJson(array) + "\n");

		// Let's sort using bubble sort
		bubbleSort(array, 0, array.length);

		// Verify sorted array
		System.out.println("\n" + GSON.toJson(array));
	}
	// CHECKSTYLE:ON

	@SuppressWarnings(
		{"rawtypes", "unchecked"})
	public static void bubbleSort(final Object[] array, final int fromIndex, final int toIndex) {
		Object d;
		for (int i = toIndex - 1; i > fromIndex; i--) {
			boolean isSorted = true;
			for (int j = fromIndex; j < i; j++) {
				// If elements in wrong order then swap them
				if (((Comparable) array[j]).compareTo(array[j + 1]) > 0) {
					isSorted = false;
					d = array[j + 1];
					array[j + 1] = array[j];
					array[j] = d;
					System.out.println(GSON.toJson(array));
				}
			}
			// If no swapping then array is already sorted
			if (isSorted) {
				break;
			}
		}
	}
}
