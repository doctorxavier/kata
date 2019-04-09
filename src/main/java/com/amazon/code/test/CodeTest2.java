package com.amazon.code.test;

import java.time.LocalDate;

import org.joda.time.DateTime;

import com.codewars.kata.util.parser.gson.DateTimeConverter;
import com.codewars.kata.util.parser.gson.LocalDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class CodeTest2 {

	private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").registerTypeAdapter(DateTime.class,
			new DateTimeConverter()).registerTypeAdapter(LocalDate.class, new LocalDateConverter()).create();

	private CodeTest2() {

	}

	// CHECKSTYLE:OFF

	// CHECKSTYLE:ON

	// CHECKSTYLE:OFF
	public static void main(final String[] args) {

	}
	// CHECKSTYLE:ON
}
