package com.codewars.basics;

import java.time.LocalDate;

import org.joda.time.DateTime;

import com.codewars.kata.util.parser.gson.DateTimeConverter;
import com.codewars.kata.util.parser.gson.LocalDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class MatrixAddition {

	private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").registerTypeAdapter(DateTime.class,
			new DateTimeConverter()).registerTypeAdapter(LocalDate.class, new LocalDateConverter()).create();

	private MatrixAddition() {

	}

	public static int[][] matrixAddition(final int[][] a, final int[][] b) {
		int n = a.length;
		int m = a[0].length;
		int[][] x = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				x[i][j] = a[i][j] + b[i][j];
			}
		}
		return x;
	}

	public static void main(final String[] args) {
		final int[][] a =
			{
					{1, 2, 3},
					{3, 2, 1},
					{1, 1, 1}};
		final int[][] b =
			{
					{2, 2, 1},
					{3, 2, 3},
					{1, 1, 3}};

		System.out.println(GSON.toJson(matrixAddition(a, b)));

	}

}
