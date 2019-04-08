package com.santander.api.util.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesLoader {

	private PropertiesLoader() {

	}

	public static Properties loadFromClasspath(final String path) throws IOException {
		Properties properties = null;
		InputStream inputStream = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			if (inputStream == null) {
				throw new FileNotFoundException(path);
			}
			properties = new Properties();
			properties.load(inputStream);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				throw new IOException(path);
			}
		}
		return properties;
	}

}
