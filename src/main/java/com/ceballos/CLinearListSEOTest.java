package com.ceballos;

public final class CLinearListSEOTest {

	private CLinearListSEOTest() {

	}

	public static void showList(final CLinearListSEO lse) {
		CData obj = lse.getFirst();
		int i = 1;
		while (obj != null) {
			System.out.println(i++ + ".-" + obj.getName() + " " + obj.getScore());
			obj = lse.getNext();
		}
	}

	public static void main(final String[] args) {
		final CLinearListSEO lse = new CLinearListSEO();

		CData obj;
		String name = "";
		double score;
		int i = 0;

		System.out.println("Introduce data. Ctrl+Z to end.");
		System.out.println("Name: ");

		/*
		 * while ((name = Read.data()) != null) {
		 * System.out.println("score: ");
		 * score = Read.doubleData();
		 * lse.add(new CData(name, score));
		 * System.out.println("Name Student to be deleted: ");
		 * }
		 */

		System.out.println("\n");

		System.out.println("Name Student to be deleted: ");
		// name = Read.data();
		obj = lse.delete(new CData(name, 0));
		if (obj == null) {
			System.out.println("Error: student not deleted");
		}

		obj = lse.getNext();
		if (obj != null) {
			System.out.println("Name: " + obj.getName() + ", Score: " + obj.getScore());
		}

		System.out.println("");
		showList(lse);
	}
}
