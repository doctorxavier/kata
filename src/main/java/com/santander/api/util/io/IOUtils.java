package com.santander.api.util.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class IOUtils {

	private IOUtils() {

	}

	public static byte[] toByteArrayFromStaticPath(final String path) throws IOException {
		InputStream inputStream = null;
		byte[] byteArray = null;
		try {
			inputStream = new FileInputStream(path);
			byteArray = org.apache.commons.io.IOUtils.toByteArray(new InputStreamReader(inputStream, StandardCharsets.UTF_8), StandardCharsets.UTF_8);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				throw new IOException(path);
			}
		}
		return byteArray;
	}

	public static byte[] toByteArray(final String path) throws IOException {
		InputStream inputStream = null;
		byte[] byteArray = null;
		try {
			inputStream = new FileInputStream(path);
			byteArray = org.apache.commons.io.IOUtils.toByteArray(new InputStreamReader(inputStream, StandardCharsets.UTF_8), StandardCharsets.UTF_8);
		} catch (FileNotFoundException e) {
			byteArray = toByteArrayFromClasspath(path);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				throw new IOException(path);
			}
		}
		return byteArray;
	}

	public static byte[] toByteArrayFromClasspath(final String path) throws IOException {
		InputStream inputStream = null;
		byte[] byteArray = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			if (inputStream == null) {
				throw new FileNotFoundException(path);
			}
			byteArray = org.apache.commons.io.IOUtils.toByteArray(new InputStreamReader(inputStream, StandardCharsets.UTF_8), StandardCharsets.UTF_8);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				throw new IOException(path);
			}
		}
		return byteArray;
	}

}
