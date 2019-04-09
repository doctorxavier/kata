package com.codewars.kata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.joda.time.DateTime;

import com.codewars.kata.util.Utilities;
import com.codewars.kata.util.parser.gson.DateTimeConverter;
import com.codewars.kata.util.parser.gson.LocalDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class WhichAreIn {

//#Example 1: a1 = ["arp", "live", "strong"]
//a2 = ["lively", "alive", "harp", "sharp", "armstrong"]
//returns ["arp", "live", "strong"]
//
//#Example 2: a1 = ["tarp", "mice", "bull"]
//a2 = ["lively", "alive", "harp", "sharp", "armstrong"]
//returns []

	private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").registerTypeAdapter(DateTime.class,
			new DateTimeConverter()).registerTypeAdapter(LocalDate.class, new LocalDateConverter()).create();

	private WhichAreIn() {

	}

	public static String[] inArray(final String[] arr1, final String[] arr2) {

		final List<String> words = new ArrayList<String>();
		for (final String word : arr1) {
			words.add(word);
		}
		final List<String> matches = new ArrayList<String>();

		for (int i = 0; i < words.size(); i++) {
			for (final String word : arr2) {
				if (word.contains(words.get(i))) {
					matches.add(words.get(i));
					words.remove(i);
					i = -1;
					break;
				}
			}
		}
		Collections.sort(matches);

		return matches.toArray(new String[matches.size()]);
	}

	public static void main(final String[] args) {
		final StopWatch watch = new StopWatch();
		watch.start();

		final String[] a1 = new String[]
			{"arp", "live", "strong"};
		final String[] a2 = new String[]
			{"lively", "alive", "harp", "sharp", "armstrong"};

		System.out.println(GSON.toJson(WhichAreIn.inArray(a1, a2)));

		watch.stop();
		System.out.println(Utilities.parseMilliseconds(watch.getTime()));
	}

}
