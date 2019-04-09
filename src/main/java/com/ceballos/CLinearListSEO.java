package com.ceballos;

public class CLinearListSEO extends AbstractCLinearListSEO<CData> {

	public int compare(final CData obj1, final CData obj2) {
		final String str1 = new String(obj1.getName());
		final String str2 = new String(obj2.getName());

		return str1.compareTo(str2);
	}
	
}
