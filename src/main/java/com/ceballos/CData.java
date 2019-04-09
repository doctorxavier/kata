package com.ceballos;

public class CData {

	private String	name;

	private int		score;

	public CData(final String name, final int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public final int getScore() {
		return score;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public final void setScore(final int score) {
		this.score = score;
	}

}
