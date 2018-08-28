package com.alxg2112.sandbox.concurrency;

import java.util.Date;

/**
 * @author Alexander Gryshchenko
 */
public class ValueClass {

	private final String name;
	private final String surname;
	private final Date date;

	public ValueClass(String name, String surname, Date date) {
		this.name = name;
		this.surname = surname;
		this.date = date;


	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public Date getDate() {
		return new Date(date.getTime());
	}
}
