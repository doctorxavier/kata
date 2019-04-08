package com.santander.api.util.parser.exception;

public class ParserException extends RuntimeException {

	private static final long serialVersionUID = -1L;

	public ParserException(final Exception e) {
		super(e);
	}

}
