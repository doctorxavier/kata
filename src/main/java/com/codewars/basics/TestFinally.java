package com.codewars.basics;

public final class TestFinally {

	private TestFinally() {

	}

	public void testFinally() {
		try {
			return;
		} finally {
			System.out.println("finally!");
		}
	}

	public static void main(final String[] args) {
		final TestFinally testFinally = new TestFinally();
		testFinally.testFinally();
	}

}
