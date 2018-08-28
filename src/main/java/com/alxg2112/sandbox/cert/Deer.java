package com.alxg2112.sandbox.cert;

/**
 * @author Alexander Gryshchenko
 */
public class Deer {

	public static void main(String[] args) {
		Deer deer = new Reindeer(5);
		System.out.println("," + deer.hasHorns());
	}

	public Deer() {
		System.out.print("Deer");
	}

	public Deer(int age) {
		System.out.print("DeerAge");
	}

	private boolean hasHorns() {
		return false;
	}
}

class Reindeer extends Deer {

	public Reindeer(int age) {
		System.out.print("Reindeer");
	}

	public boolean hasHorns() {
		return true;
	}
}
