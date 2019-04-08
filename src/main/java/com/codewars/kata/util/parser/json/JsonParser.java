package com.codewars.kata.util.parser.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.codewars.kata.util.parser.Parser;
import com.codewars.kata.util.parser.exception.ParserException;
import com.codewars.kata.util.parser.gson.DateTimeConverter;
import com.codewars.kata.util.parser.gson.LocalDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class JsonParser<E> implements Parser<E> {

	private static Gson	gson	= new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").registerTypeAdapter(DateTime.class,
			new DateTimeConverter()).registerTypeAdapter(LocalDate.class, new LocalDateConverter()).create();

	private Class<E>	clazz;

	private JsonParser() {

	}

	public JsonParser(final Class<E> clazz) {
		this();
		this.clazz = clazz;
	}

	public JsonParser(final Class<E> clazz, final boolean prettyPrinting) {
		this(clazz);
		if (prettyPrinting) {
			gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").registerTypeAdapter(DateTime.class,
					new DateTimeConverter()).registerTypeAdapter(LocalDate.class, new LocalDateConverter()).setPrettyPrinting().create();
		}
	}

	@Override
	public E unmarshall(final String inPath) throws ParserException {
		E response = null;
		InputStream in = null;
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(inPath);
			response = this.unmarshall(in);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new ParserException(e);
				}
			}
		}
		return response;
	}

	@Override
	public E unmarshall(final InputStream in) throws ParserException {
		E response = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			response = gson.fromJson(br, this.clazz);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new ParserException(e);
				}
			}
		}
		return response;
	}

	public E unmarshallFromString(final String marshall) {
		return gson.fromJson(marshall, this.clazz);
	}

	@Override
	public void marshall(final E marshall, final OutputStream out) throws ParserException {
		final String json = gson.toJson(marshall);
		try {
			out.write(json.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new ParserException(e);
		}
	}

	@Override
	public String toString(final E marshall) throws ParserException {
		return gson.toJson(marshall);
	}

	@Override
	public String toString(final E marshall, final int indent) throws ParserException {
		return this.toString(marshall);
	}

}
