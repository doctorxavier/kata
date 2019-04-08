package com.santander.api.util.parser.gson;

import java.lang.reflect.Type;
import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public final class LocalDateConverter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

	private static final String				ISO_PATTERN	= "yyyy-MM-dd";
	private static final DateTimeFormatter	FORMATTER	= DateTimeFormat.forPattern(ISO_PATTERN);

	@Override
	public JsonElement serialize(final LocalDate src, final Type srcType, final JsonSerializationContext context) {
		return new JsonPrimitive(src.toString(FORMATTER));
	}

	@Override
	public LocalDate deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
		try {
			return new LocalDate(json.getAsString());
		} catch (final IllegalArgumentException e) {
			// May be it came in formatted as a java.util.Date, so try that
			final Date date = context.deserialize(json, Date.class);
			return new LocalDate(date);
		}
	}

}
