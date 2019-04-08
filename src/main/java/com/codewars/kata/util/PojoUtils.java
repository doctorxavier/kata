package com.codewars.kata.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;

public final class PojoUtils {

	private static final int RANDOM_LENGTH = 10;

	private PojoUtils() {

	}

	public static void initializePojo(final Object object) throws PojoUtilsException {
		initializePojo(object, 1);
	}

	public static void initializePojo(final Object object, final int depth) throws PojoUtilsException {

		if (depth > 0) {
			for (Method method : object.getClass().getMethods()) {
				if (Modifier.isPublic(method.getModifiers())) {
					if ("void".equals(method.getReturnType().getName()) && "set".equals(method.getName().substring(0, 3))
							&& method.getParameterTypes().length == 1) {

						final String name = method.getName().substring(3).toLowerCase(Locale.ENGLISH);

						final Class<?> parameterType = method.getParameterTypes()[0];

						try {
							if (parameterType.isAssignableFrom(Boolean.class) || "bool".equals(parameterType.getName())) {
								method.invoke(object, RandomUtils.nextBoolean());
							} else if (parameterType.isAssignableFrom(Short.class)) {
								method.invoke(object, (short) RandomUtils.nextInt(Short.MAX_VALUE));
							} else if (parameterType.isAssignableFrom(Integer.class) || "int".equals(parameterType.getName())) {
								method.invoke(object, RandomUtils.nextInt());
							} else if (parameterType.isAssignableFrom(Long.class) || "long".equals(parameterType.getName())) {
								method.invoke(object, RandomUtils.nextLong());
							} else if (parameterType.isAssignableFrom(Float.class) || "float".equals(parameterType.getName())) {
								method.invoke(object, RandomUtils.nextFloat());
							} else if (parameterType.isAssignableFrom(Double.class) || "double".equals(parameterType.getName())) {
								method.invoke(object, RandomUtils.nextDouble());
							} else if (parameterType.isAssignableFrom(Date.class)) {
								method.invoke(object, new Date());
							} else if (parameterType.isAssignableFrom(DateTime.class)) {
								method.invoke(object, new DateTime());
							} else if (parameterType.isAssignableFrom(String.class)) {
								method.invoke(object, name + RandomStringUtils.randomAscii(RANDOM_LENGTH));
							} else if (depth > 1 && !(parameterType.isAssignableFrom(Set.class)) && !(parameterType.isAssignableFrom(List.class))
									&& !(parameterType.isAssignableFrom(Map.class))) {
								final Object innerObject = parameterType.newInstance();
								initializePojo(innerObject, depth - 1);
								method.invoke(object, innerObject);
							}
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
							throw new PojoUtils.PojoUtilsException(e);
						}
					}
				}
			}
		}

	}

	public static final class PojoUtilsException extends Exception {

		private static final long serialVersionUID = 1L;

		public PojoUtilsException(final Exception e) {
			super(e);
		}

	}

}
