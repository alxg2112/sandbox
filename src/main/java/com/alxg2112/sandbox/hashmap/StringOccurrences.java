package com.alxg2112.sandbox.hashmap;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author Alexander Gryshchenko
 */
public class StringOccurrences {

	private final Map<String, Integer> occurrencesByWord;

	public StringOccurrences() {
		this.occurrencesByWord = Maps.newHashMap();
	}

	public void addToStats(String word) {
		occurrencesByWord.putIfAbsent(word, 0);
		occurrencesByWord.computeIfPresent(word, (key, oldValue) -> ++oldValue);
	}

	public int getOccurrencesFor(String word) {
		return occurrencesByWord.getOrDefault(word, 0);
	}

	public static void main(String[] args) {
		StringOccurrences stringOccurrences = new StringOccurrences();
		stringOccurrences.addToStats("test");
		stringOccurrences.addToStats("test");
		stringOccurrences.addToStats("test");
		System.out.println(stringOccurrences.getOccurrencesFor("test"));
	}
}
