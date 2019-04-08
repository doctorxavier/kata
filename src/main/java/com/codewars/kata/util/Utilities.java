package com.codewars.kata.util;

public final class Utilities {

	public static final int	MILLISECONDS	= 1000;
	public static final int	MINUTES			= 60;
	public static final int	SECONDS			= 60;
	public static final int	HOURS			= 24;
	public static final int	MONTHDAYS_V1	= 31;
	public static final int	MONTHDAYS_V2	= 32;
	public static final int	WEEKDAYS		= 7;

	private Utilities() {

	}

	public static String parseMilliseconds(final long elapsed) {
		long elapsedAux = elapsed;
		int ms = (int) (elapsedAux % MILLISECONDS);
		elapsedAux /= MILLISECONDS;
		int seconds = (int) (elapsedAux % SECONDS);
		elapsedAux /= SECONDS;
		int minutes = (int) (elapsedAux % MINUTES);
		elapsedAux /= MINUTES;
		int hours = (int) (elapsedAux % HOURS);
		return String.format("%02d:%02d:%02d:%04d", hours, minutes, seconds, ms);
	}

}
